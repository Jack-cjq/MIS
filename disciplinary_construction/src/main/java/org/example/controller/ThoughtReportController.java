package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.CurrentUser;
import org.example.model.ThoughtReportModel;
import org.example.response.ResponseResult;
import org.example.service.ThoughtReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/thought-report")
@CrossOrigin
@Tag(name = "思想汇报管理", description = "思想汇报相关接口")
public class ThoughtReportController {

    @Autowired
    private ThoughtReportService thoughtReportService;

    @Operation(summary = "添加思想汇报")
    @PostMapping("/add")
    public ResponseResult<ThoughtReportModel> addThoughtReport(@RequestBody ThoughtReportModel thoughtReport, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        thoughtReport.setStudentId(userId);
        ThoughtReportModel result = thoughtReportService.addThoughtReport(thoughtReport);
        return ResponseResult.success(result);
    }

    @Operation(summary = "更新思想汇报")
    @PutMapping("/update")
    public ResponseResult<ThoughtReportModel> updateThoughtReport(@RequestBody ThoughtReportModel thoughtReport, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        thoughtReport.setStudentId(userId);
        ThoughtReportModel result = thoughtReportService.updateThoughtReport(thoughtReport);
        return ResponseResult.success(result);
    }

    @Operation(summary = "删除思想汇报")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<String> deleteThoughtReport(@PathVariable String id) {
        thoughtReportService.deleteThoughtReport(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取学生思想汇报")
    @GetMapping("/student/{studentId}")
    public ResponseResult<List<ThoughtReportModel>> getStudentThoughtReports(@PathVariable String studentId) {
        List<ThoughtReportModel> result = thoughtReportService.getStudentThoughtReports(studentId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据ID获取思想汇报")
    @GetMapping("/{id}")
    public ResponseResult<ThoughtReportModel> getThoughtReportById(@PathVariable String id) {
        ThoughtReportModel result = thoughtReportService.getThoughtReportById(id);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取待审核思想汇报列表")
    @GetMapping("/pending")
    public ResponseResult<List<ThoughtReportModel>> getPendingThoughtReports() {
        List<ThoughtReportModel> result = thoughtReportService.getPendingThoughtReports();
        return ResponseResult.success(result);
    }

    @Operation(summary = "审核思想汇报")
    @PostMapping("/audit/{id}")
    public ResponseResult<ThoughtReportModel> auditThoughtReport(
            @PathVariable String id,
            @RequestBody Map<String, String> auditData,
            @CurrentUser Map<String, Object> currentUser) {
        String auditorId = (String) currentUser.get("userId");
        String auditStatus = auditData.get("auditStatus");
        String auditComment = auditData.get("auditComment");
        
        ThoughtReportModel result = thoughtReportService.auditThoughtReport(id, auditStatus, auditComment, auditorId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取所有思想汇报列表")
    @GetMapping("/list")
    public ResponseResult<List<ThoughtReportModel>> getAllThoughtReports() {
        List<ThoughtReportModel> result = thoughtReportService.getAllThoughtReports();
        return ResponseResult.success(result);
    }

    @Operation(summary = "搜索思想汇报")
    @GetMapping("/search")
    public ResponseResult<List<ThoughtReportModel>> searchThoughtReports(@RequestParam String keyword) {
        List<ThoughtReportModel> result = thoughtReportService.searchThoughtReports(keyword);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据汇报类型获取思想汇报列表")
    @GetMapping("/type/{reportType}")
    public ResponseResult<List<ThoughtReportModel>> getThoughtReportsByType(@PathVariable String reportType) {
        List<ThoughtReportModel> result = thoughtReportService.getThoughtReportsByType(reportType);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据状态获取思想汇报列表")
    @GetMapping("/status/{status}")
    public ResponseResult<List<ThoughtReportModel>> getThoughtReportsByStatus(@PathVariable String status) {
        List<ThoughtReportModel> result = thoughtReportService.getThoughtReportsByStatus(status);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据审核人获取思想汇报列表")
    @GetMapping("/reviewer/{reviewerId}")
    public ResponseResult<List<ThoughtReportModel>> getThoughtReportsByReviewer(@PathVariable String reviewerId) {
        List<ThoughtReportModel> result = thoughtReportService.getThoughtReportsByReviewer(reviewerId);
        return ResponseResult.success(result);
    }
} 