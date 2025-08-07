package org.example.service;

import org.example.model.ThoughtReportModel;
import java.util.List;

public interface ThoughtReportService {
    // 学生操作
    ThoughtReportModel addThoughtReport(ThoughtReportModel thoughtReport);
    ThoughtReportModel updateThoughtReport(ThoughtReportModel thoughtReport);
    void deleteThoughtReport(String id);
    List<ThoughtReportModel> getStudentThoughtReports(String studentId);
    ThoughtReportModel getThoughtReportById(String id);

    // 管理员操作
    List<ThoughtReportModel> getPendingThoughtReports();
    ThoughtReportModel auditThoughtReport(String id, String auditStatus, String auditComment, String auditorId);
    List<ThoughtReportModel> getAllThoughtReports();
    List<ThoughtReportModel> searchThoughtReports(String keyword);
    List<ThoughtReportModel> getThoughtReportsByType(String reportType);
    List<ThoughtReportModel> getThoughtReportsByStatus(String status);
    List<ThoughtReportModel> getThoughtReportsByReviewer(String reviewerId);
} 