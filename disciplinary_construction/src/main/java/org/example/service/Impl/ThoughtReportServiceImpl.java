package org.example.service.Impl;

import org.example.model.ThoughtReportModel;
import org.example.repository.ThoughtReportRepository;
import org.example.service.ThoughtReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThoughtReportServiceImpl implements ThoughtReportService {

    @Autowired
    private ThoughtReportRepository thoughtReportRepository;

    @Override
    public ThoughtReportModel addThoughtReport(ThoughtReportModel thoughtReport) {
        thoughtReport.setCreateTime(LocalDateTime.now());
        thoughtReport.setUpdateTime(LocalDateTime.now());
        thoughtReport.setAuditStatus("待审核");
        return thoughtReportRepository.save(thoughtReport);
    }

    @Override
    public ThoughtReportModel updateThoughtReport(ThoughtReportModel thoughtReport) {
        thoughtReport.setUpdateTime(LocalDateTime.now());
        return thoughtReportRepository.save(thoughtReport);
    }

    @Override
    public void deleteThoughtReport(String id) {
        thoughtReportRepository.deleteById(id);
    }

    @Override
    public List<ThoughtReportModel> getStudentThoughtReports(String studentId) {
        return thoughtReportRepository.findByStudentId(studentId);
    }

    @Override
    public ThoughtReportModel getThoughtReportById(String id) {
        return thoughtReportRepository.findById(id).orElse(null);
    }

    @Override
    public List<ThoughtReportModel> getPendingThoughtReports() {
        return thoughtReportRepository.findByAuditStatus("待审核");
    }

    @Override
    public ThoughtReportModel auditThoughtReport(String id, String auditStatus, String auditComment, String auditorId) {
        ThoughtReportModel thoughtReport = thoughtReportRepository.findById(id).orElse(null);
        if (thoughtReport != null) {
            thoughtReport.setAuditStatus(auditStatus);
            thoughtReport.setAuditComment(auditComment);
            thoughtReport.setAuditorId(auditorId);
            thoughtReport.setAuditTime(LocalDateTime.now());
            return thoughtReportRepository.save(thoughtReport);
        }
        return null;
    }

    @Override
    public List<ThoughtReportModel> getAllThoughtReports() {
        return thoughtReportRepository.findAll();
    }

    @Override
    public List<ThoughtReportModel> searchThoughtReports(String keyword) {
        List<ThoughtReportModel> byTitle = thoughtReportRepository.findByTitleContaining(keyword);
        List<ThoughtReportModel> byStudentName = thoughtReportRepository.findByStudentNameContaining(keyword);
        byTitle.addAll(byStudentName);
        return byTitle;
    }

    @Override
    public List<ThoughtReportModel> getThoughtReportsByType(String reportType) {
        return thoughtReportRepository.findByReportType(reportType);
    }

    @Override
    public List<ThoughtReportModel> getThoughtReportsByStatus(String status) {
        return thoughtReportRepository.findByStatus(status);
    }

    @Override
    public List<ThoughtReportModel> getThoughtReportsByReviewer(String reviewerId) {
        return thoughtReportRepository.findByReviewerId(reviewerId);
    }
} 