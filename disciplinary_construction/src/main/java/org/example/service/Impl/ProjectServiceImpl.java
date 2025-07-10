package org.example.service.Impl;

import org.example.model.ProjectModel;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectModel addProject(ProjectModel project) {
        project.setCreateTime(LocalDateTime.now());
        project.setUpdateTime(LocalDateTime.now());
        project.setAuditStatus("待审核");
        return projectRepository.save(project);
    }

    @Override
    public ProjectModel updateProject(ProjectModel project) {
        project.setUpdateTime(LocalDateTime.now());
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectModel> getStudentProjects(String studentId) {
        return projectRepository.findByStudentId(studentId);
    }

    @Override
    public List<ProjectModel> getPendingProjects() {
        return projectRepository.findByAuditStatus("待审核");
    }

    @Override
    public ProjectModel auditProject(String id, String auditStatus, String auditComment, String auditorId) {
        ProjectModel project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.setAuditStatus(auditStatus);
            project.setAuditComment(auditComment);
            project.setAuditorId(auditorId);
            project.setAuditTime(LocalDateTime.now());
            return projectRepository.save(project);
        }
        return null;
    }

    @Override
    public List<ProjectModel> getAllProjects() {
        return projectRepository.findAll();
    }
}