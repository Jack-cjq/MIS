package org.example.service;

import org.example.model.ProjectModel;
import java.util.List;
import java.util.Map;

public interface ProjectService {
    // 学生操作
    ProjectModel addProject(ProjectModel project);
    ProjectModel updateProject(ProjectModel project);
    void deleteProject(String id);
    List<ProjectModel> getStudentProjects(String studentId);

    // 管理员操作
    List<ProjectModel> getPendingProjects();
    ProjectModel auditProject(String id, String auditStatus, String auditComment, String auditorId);
    List<ProjectModel> getAllProjects();

    // 兼容方法
    List<ProjectModel> getProjectsByStudentId(String studentId);
    ProjectModel saveProject(ProjectModel project);
    void deleteProject(Long id);
    ProjectModel getProjectById(Long id);
    ProjectModel getProjectById(String id);
    
    // 搜索方法
    List<ProjectModel> searchProjects(Map<String, String> params);

    // 统计方法
    int countByAuditStatus(String auditStatus);
}