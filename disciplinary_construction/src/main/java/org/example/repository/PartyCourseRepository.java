package org.example.repository;

import org.example.model.PartyCourseModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyCourseRepository extends MongoRepository<PartyCourseModel, String> {
    List<PartyCourseModel> findByStudentId(String studentId);
    List<PartyCourseModel> findByAuditStatus(String auditStatus);
    List<PartyCourseModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);
    List<PartyCourseModel> findByCourseType(String courseType);
    List<PartyCourseModel> findByStatus(String status);
    List<PartyCourseModel> findByInstructor(String instructor);
    List<PartyCourseModel> findByTitleContaining(String title);
    List<PartyCourseModel> findByStudentNameContaining(String studentName);
} 