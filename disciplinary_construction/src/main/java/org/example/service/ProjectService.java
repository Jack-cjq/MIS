package org.example.service;

import org.example.model.ProjectModel;
import java.util.List;

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
}