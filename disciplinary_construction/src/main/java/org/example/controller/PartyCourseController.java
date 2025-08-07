package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.CurrentUser;
import org.example.model.PartyCourseModel;
import org.example.response.ResponseResult;
import org.example.service.PartyCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/party-course")
@CrossOrigin
@Tag(name = "党课管理", description = "党课相关接口")
public class PartyCourseController {

    @Autowired
    private PartyCourseService partyCourseService;

    @Operation(summary = "添加党课记录")
    @PostMapping("/add")
    public ResponseResult<PartyCourseModel> addPartyCourse(@RequestBody PartyCourseModel partyCourse, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        String userName = (String) currentUser.get("userName");
        partyCourse.setStudentId(userId);
        partyCourse.setStudentName(userName);
        PartyCourseModel result = partyCourseService.addPartyCourse(partyCourse);
        return ResponseResult.success(result);
    }

    @Operation(summary = "更新党课记录")
    @PutMapping("/update")
    public ResponseResult<PartyCourseModel> updatePartyCourse(@RequestBody PartyCourseModel partyCourse, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        String userName = (String) currentUser.get("userName");
        partyCourse.setStudentId(userId);
        partyCourse.setStudentName(userName);
        PartyCourseModel result = partyCourseService.updatePartyCourse(partyCourse);
        return ResponseResult.success(result);
    }

    @Operation(summary = "删除党课记录")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<String> deletePartyCourse(@PathVariable String id) {
        partyCourseService.deletePartyCourse(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取学生党课记录")
    @GetMapping("/student/{studentId}")
    public ResponseResult<List<PartyCourseModel>> getStudentPartyCourses(@PathVariable String studentId) {
        List<PartyCourseModel> result = partyCourseService.getStudentPartyCourses(studentId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据ID获取党课记录")
    @GetMapping("/{id}")
    public ResponseResult<PartyCourseModel> getPartyCourseById(@PathVariable String id) {
        PartyCourseModel result = partyCourseService.getPartyCourseById(id);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取待审核党课列表")
    @GetMapping("/pending")
    public ResponseResult<List<PartyCourseModel>> getPendingPartyCourses() {
        List<PartyCourseModel> result = partyCourseService.getPendingPartyCourses();
        return ResponseResult.success(result);
    }

    @Operation(summary = "审核党课记录")
    @PostMapping("/audit/{id}")
    public ResponseResult<PartyCourseModel> auditPartyCourse(
            @PathVariable String id,
            @RequestBody Map<String, String> auditData,
            @CurrentUser Map<String, Object> currentUser) {
        String auditorId = (String) currentUser.get("userId");
        String auditStatus = auditData.get("auditStatus");
        String auditComment = auditData.get("auditComment");
        
        PartyCourseModel result = partyCourseService.auditPartyCourse(id, auditStatus, auditComment, auditorId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取所有党课列表")
    @GetMapping("/list")
    public ResponseResult<List<PartyCourseModel>> getAllPartyCourses() {
        List<PartyCourseModel> result = partyCourseService.getAllPartyCourses();
        return ResponseResult.success(result);
    }

    @Operation(summary = "搜索党课")
    @GetMapping("/search")
    public ResponseResult<List<PartyCourseModel>> searchPartyCourses(@RequestParam String keyword) {
        List<PartyCourseModel> result = partyCourseService.searchPartyCourses(keyword);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据课程类型获取党课列表")
    @GetMapping("/type/{courseType}")
    public ResponseResult<List<PartyCourseModel>> getPartyCoursesByType(@PathVariable String courseType) {
        List<PartyCourseModel> result = partyCourseService.getPartyCoursesByType(courseType);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据课程状态获取党课列表")
    @GetMapping("/status/{status}")
    public ResponseResult<List<PartyCourseModel>> getPartyCoursesByStatus(@PathVariable String status) {
        List<PartyCourseModel> result = partyCourseService.getPartyCoursesByStatus(status);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据授课教师获取党课列表")
    @GetMapping("/instructor/{instructor}")
    public ResponseResult<List<PartyCourseModel>> getPartyCoursesByInstructor(@PathVariable String instructor) {
        List<PartyCourseModel> result = partyCourseService.getPartyCoursesByInstructor(instructor);
        return ResponseResult.success(result);
    }
} 