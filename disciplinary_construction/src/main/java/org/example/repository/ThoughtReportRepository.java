package org.example.repository;

import org.example.model.ThoughtReportModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThoughtReportRepository extends MongoRepository<ThoughtReportModel, String> {
    List<ThoughtReportModel> findByStudentId(String studentId);
    List<ThoughtReportModel> findByAuditStatus(String auditStatus);
    List<ThoughtReportModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);
    List<ThoughtReportModel> findByReportType(String reportType);
    List<ThoughtReportModel> findByStatus(String status);
    List<ThoughtReportModel> findByTitleContaining(String title);
    List<ThoughtReportModel> findByStudentNameContaining(String studentName);
    List<ThoughtReportModel> findByReviewerId(String reviewerId);
} 