package org.example.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.model.AlumniModel;
import org.example.model.StudentModel;
import org.example.repository.AlumniRepository;
import org.example.repository.StudentRepository;
import org.example.response.ResponseCode;
import org.example.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class AlumniServiceImpl implements AlumniService {
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
        alumniRepository.save(alumniModel);
    }

    @Override
    public AlumniModel findAlumniByStudentId(String studentId) {
        return null;
    }

    @Override
    public List<AlumniModel> findAlumniByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public List<AlumniModel> findAllAlumni() {
        return alumniRepository.findAll();
    }
}
