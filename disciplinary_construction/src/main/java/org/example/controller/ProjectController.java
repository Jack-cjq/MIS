package org.example.controller;

import org.example.model.ProjectModel;
import org.example.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/projects")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // ==================== 学生端接口 ====================

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentProjects(@PathVariable String studentId) {
        try {
            List<ProjectModel> projects = projectService.getProjectsByStudentId(studentId);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取项目数据失败: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addProject(@RequestBody ProjectModel project) {
        try {
            if (project.getAuditStatus() == null) {
                project.setAuditStatus("待审核");
            }
            ProjectModel saved = projectService.saveProject(project);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("添加项目失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id, @RequestBody ProjectModel project) {
        try {
            project.setId(id);
            ProjectModel updated = projectService.saveProject(project);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("更新项目失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("删除项目失败: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable String id) {
        try {
            ProjectModel project = projectService.getProjectById(id);
            if (project == null) {
                return ResponseEntity.badRequest().body("项目不存在");
            }
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取项目失败: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProjects(@RequestParam Map<String, String> params) {
        try {
            List<ProjectModel> projects = projectService.searchProjects(params);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("搜索项目失败: " + e.getMessage());
        }
    }

    // ==================== 管理员端接口 ====================

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllProjects() {
        try {
            List<ProjectModel> projects = projectService.getAllProjects();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取项目数据失败: " + e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/audit")
    public ResponseEntity<?> auditProject(@PathVariable String id, @RequestBody Map<String, String> auditData) {
        try {
            String auditStatus = auditData.get("auditStatus");
            String auditComment = auditData.get("auditComment");
            String auditorId = auditData.get("auditorId"); 
            String auditorName = auditData.get("auditorName");

            ProjectModel project = projectService.getProjectById(id);
            if (project == null) {
                return ResponseEntity.badRequest().body("项目不存在");
            }
            
            project.setAuditStatus(auditStatus);
            project.setAuditComment(auditComment);
            project.setAuditorId(auditorId);
            project.setAuditorName(auditorName);

            ProjectModel updated = projectService.saveProject(project);
            
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("审核失败: " + e.getMessage());
        }
    }
}