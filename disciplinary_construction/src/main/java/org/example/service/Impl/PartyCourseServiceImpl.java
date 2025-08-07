package org.example.service.Impl;

import org.example.model.PartyCourseModel;
import org.example.repository.PartyCourseRepository;
import org.example.service.PartyCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartyCourseServiceImpl implements PartyCourseService {

    @Autowired
    private PartyCourseRepository partyCourseRepository;

    @Override
    public PartyCourseModel addPartyCourse(PartyCourseModel partyCourse) {
        partyCourse.setCreateTime(LocalDateTime.now());
        partyCourse.setUpdateTime(LocalDateTime.now());
        partyCourse.setAuditStatus("待审核");
        return partyCourseRepository.save(partyCourse);
    }

    @Override
    public PartyCourseModel updatePartyCourse(PartyCourseModel partyCourse) {
        partyCourse.setUpdateTime(LocalDateTime.now());
        return partyCourseRepository.save(partyCourse);
    }

    @Override
    public void deletePartyCourse(String id) {
        partyCourseRepository.deleteById(id);
    }

    @Override
    public List<PartyCourseModel> getStudentPartyCourses(String studentId) {
        return partyCourseRepository.findByStudentId(studentId);
    }

    @Override
    public PartyCourseModel getPartyCourseById(String id) {
        return partyCourseRepository.findById(id).orElse(null);
    }

    @Override
    public List<PartyCourseModel> getPendingPartyCourses() {
        return partyCourseRepository.findByAuditStatus("待审核");
    }

    @Override
    public PartyCourseModel auditPartyCourse(String id, String auditStatus, String auditComment, String auditorId) {
        PartyCourseModel partyCourse = partyCourseRepository.findById(id).orElse(null);
        if (partyCourse != null) {
            partyCourse.setAuditStatus(auditStatus);
            partyCourse.setAuditComment(auditComment);
            partyCourse.setAuditorId(auditorId);
            partyCourse.setAuditTime(LocalDateTime.now());
            return partyCourseRepository.save(partyCourse);
        }
        return null;
    }

    @Override
    public List<PartyCourseModel> getAllPartyCourses() {
        return partyCourseRepository.findAll();
    }

    @Override
    public List<PartyCourseModel> searchPartyCourses(String keyword) {
        List<PartyCourseModel> byTitle = partyCourseRepository.findByTitleContaining(keyword);
        List<PartyCourseModel> byStudentName = partyCourseRepository.findByStudentNameContaining(keyword);
        byTitle.addAll(byStudentName);
        return byTitle;
    }

    @Override
    public List<PartyCourseModel> getPartyCoursesByType(String courseType) {
        return partyCourseRepository.findByCourseType(courseType);
    }

    @Override
    public List<PartyCourseModel> getPartyCoursesByStatus(String status) {
        return partyCourseRepository.findByStatus(status);
    }

    @Override
    public List<PartyCourseModel> getPartyCoursesByInstructor(String instructor) {
        return partyCourseRepository.findByInstructor(instructor);
    }
} 