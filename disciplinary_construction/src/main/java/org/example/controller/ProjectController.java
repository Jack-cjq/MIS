package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.CurrentUser;
import org.example.model.ProjectModel;
import org.example.response.ResponseResult;
import org.example.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/project")
@CrossOrigin
@Tag(name = "项目管理", description = "项目相关接口")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "添加项目")
    @PostMapping("/add")
    public ResponseResult<ProjectModel> addProject(@RequestBody ProjectModel project, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        project.setStudentId(userId);
        ProjectModel result = projectService.addProject(project);
        return ResponseResult.success(result);
    }

    @Operation(summary = "更新项目")
    @PutMapping("/update")
    public ResponseResult<ProjectModel> updateProject(@RequestBody ProjectModel project, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        project.setStudentId(userId);
        ProjectModel result = projectService.updateProject(project);
        return ResponseResult.success(result);
    }

    @Operation(summary = "删除项目")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<String> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取当前用户的项目列表")
    @GetMapping("/my-projects")
    public ResponseResult<List<ProjectModel>> getMyProjects(@CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        List<ProjectModel> projects = projectService.getStudentProjects(userId);
        return ResponseResult.success(projects);
    }

    @Operation(summary = "获取待审核的项目列表")
    @GetMapping("/pending")
    public ResponseResult<List<ProjectModel>> getPendingProjects() {
        List<ProjectModel> projects = projectService.getPendingProjects();
        return ResponseResult.success(projects);
    }

    @Operation(summary = "审核项目")
    @PostMapping("/audit")
    public ResponseResult<ProjectModel> auditProject(
            @RequestParam String id,
            @RequestParam String auditStatus,
            @RequestParam String auditComment,
            @RequestParam String auditorId) {
        ProjectModel result = projectService.auditProject(id, auditStatus, auditComment, auditorId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取所有项目")
    @GetMapping("/all")
    public ResponseResult<List<ProjectModel>> getAllProjects() {
        List<ProjectModel> projects = projectService.getAllProjects();
        return ResponseResult.success(projects);
    }
}