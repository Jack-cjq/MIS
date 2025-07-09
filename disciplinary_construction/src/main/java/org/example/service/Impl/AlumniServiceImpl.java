package org.example.service.Impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.example.exception.BusinessException;
import org.example.model.AlumniModel;
import org.example.model.StudentModel;
import org.example.repository.AlumniRepository;
import org.example.repository.StudentRepository;
import org.example.response.ResponseCode;
import org.example.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AlumniServiceImpl implements AlumniService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private AlumniRepository alumniRepository;
    @Autowired
    private StudentRepository studentRepository;

    public AlumniServiceImpl() {
        log.info("AlumniServiceImpl已创建");
    }

    @Override
    public void insertAlumni(AlumniModel alumniModel) {
        System.out.println(alumniModel);
        //1.登录账号是否是此学生


        //2.查询学生是否存在
        StudentModel studentModel = studentRepository.findByStudentId(alumniModel.getStudentId());
        if (studentModel == null) {
            throw new BusinessException(ResponseCode.STUDENT_NOT_FOUND);
        }
        //3.是否已经存在该学生信息
        AlumniModel data = alumniRepository.findAlumniByStudentId(alumniModel.getStudentId());
        if (data != null) {
            throw new BusinessException(ResponseCode.ALUMNI_EXISTS_ERROR);
        }
        //4.进入数据库
        alumniModel.setId(null);
        alumniModel.setStudent(null);
        alumniRepository.insert(alumniModel);

    }

    @Override
    public void updateAlumni(AlumniModel alumniModel) {
        System.out.println(alumniModel);
        //1.登录账号是否是此学生

        //2.查询学生是否存在
        StudentModel studentModel = studentRepository.findByStudentId(alumniModel.getStudentId());
        if (studentModel == null) {
            throw new BusinessException(ResponseCode.STUDENT_NOT_FOUND);
        }
        //3.是否已经存在该学生信息
        AlumniModel data = alumniRepository.findAlumniByStudentId(alumniModel.getStudentId());
        if (data == null) {
            throw new BusinessException(ResponseCode.ALUMNI_NOT_FOUND_ERROR);
        }
        //4.进入数据库
        alumniModel.setId(data.getId());
        alumniModel.setStudent(null);
        alumniRepository.save(alumniModel);
    }

    @Override
    public AlumniModel findAlumniByStudentId(String studentId) {
        return alumniRepository.findAlumniByStudentId(studentId);
    }

    @Override
    public List<AlumniModel> searchAlumniList(String searchValue, Date searchYear, Integer currentPage, Integer pageSize) {
        LookupOperation lookup = LookupOperation.newLookup()
                .from("students")
                .localField("studentId")
                .foreignField("studentId")
                .as("student");

        UnwindOperation unwind = Aggregation.unwind("student", true);

        Criteria criteria = new Criteria().orOperator(
                Criteria.where("studentId").regex(searchValue, "i"),
                Criteria.where("student.name").regex(searchValue, "i"),
                Criteria.where("workPlace").regex(searchValue, "i")
        );

        MatchOperation match = null;

        if (searchYear == null) {
            match = Aggregation.match(criteria);
        } else {
            Criteria yearCriteria = new Criteria().andOperator(
                    criteria,
                    Criteria.where("graduationDate").gte(searchYear).lt(DateUtil.offset(searchYear, DateField.YEAR, 1))
            );
            match = Aggregation.match(yearCriteria);
        }

        SkipOperation skipOp = Aggregation.skip((currentPage - 1) * pageSize);
        LimitOperation limitOp = Aggregation.limit(pageSize);
        Aggregation aggregation = Aggregation.newAggregation(lookup, unwind, match, skipOp, limitOp);

        // 执行聚合查询
        return mongoTemplate.aggregate(aggregation, "alumni", AlumniModel.class).getMappedResults();
    }

    @Override
    public Integer getTotalCount(String searchValue, Date searchYear) {
        LookupOperation lookup = LookupOperation.newLookup()
                .from("students")
                .localField("studentId")
                .foreignField("studentId")
                .as("student");

        UnwindOperation unwind = Aggregation.unwind("student", true);

        Criteria criteria = new Criteria().orOperator(
                Criteria.where("studentId").regex(searchValue, "i"),
                Criteria.where("student.name").regex(searchValue, "i"),
                Criteria.where("workPlace").regex(searchValue, "i")
        );

        MatchOperation match = null;

        if (searchYear == null) {
            match = Aggregation.match(criteria);
        } else {
            Criteria yearCriteria = new Criteria().andOperator(
                    criteria,
                    Criteria.where("graduationDate").gte(searchYear).lt(DateUtil.offset(searchYear, DateField.YEAR, 1))
            );
            match = Aggregation.match(yearCriteria);
        }

        CountOperation count = Aggregation.count().as("total");
        Aggregation aggregation = Aggregation.newAggregation(lookup, unwind, match, count);

        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, "alumni", Document.class);
        Document countDoc = result.getUniqueMappedResult();
        return countDoc != null ? countDoc.getInteger("total", 0) : 0;
    }
}
