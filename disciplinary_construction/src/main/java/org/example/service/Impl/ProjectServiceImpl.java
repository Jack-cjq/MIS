package org.example.service.Impl;

import org.example.model.ProjectModel;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<ProjectModel> getProjectsByStudentId(String studentId) {
        return getStudentProjects(studentId);
    }

    @Override
    public ProjectModel saveProject(ProjectModel project) {
        if (project.getId() == null) {
            return addProject(project);
        } else {
            return updateProject(project);
        }
    }
    
    @Override
    public void deleteProject(Long id) {
        deleteProject(id.toString());
    }
    
    @Override
    public ProjectModel getProjectById(Long id) {
        return projectRepository.findById(id.toString()).orElse(null);
    }

    @Override
    public ProjectModel getProjectById(String id) {
        return projectRepository.findById(id).orElse(null);
    }

    // ==================== 新增：搜索方法 ====================
    @Override
    public List<ProjectModel> searchProjects(Map<String, String> params) {
        String keyword = params.get("keyword");
        String projectType = params.get("projectType");
        String status = params.get("status");
        
        List<ProjectModel> allProjects = projectRepository.findAll();
        
        return allProjects.stream()
                .filter(project -> {
                    boolean matches = true;
                    
                    if (keyword != null && !keyword.isEmpty()) {
                        matches = matches && (
                            (project.getProjectName() != null && project.getProjectName().toLowerCase().contains(keyword.toLowerCase())) ||
                            (project.getStudentName() != null && project.getStudentName().toLowerCase().contains(keyword.toLowerCase())) ||
                            (project.getInstructorName() != null && project.getInstructorName().toLowerCase().contains(keyword.toLowerCase()))
                        );
                    }
                    
                    if (projectType != null && !projectType.isEmpty()) {
                        matches = matches && projectType.equals(project.getProjectLevel());
                    }
                    
                    if (status != null && !status.isEmpty()) {
                        matches = matches && status.equals(project.getAuditStatus());
                    }
                    
                    return matches;
                })
                .collect(Collectors.toList());
    }
    /**
     * 根据审核状态统计数量
     */
    @Override
    public int countByAuditStatus(String auditStatus) {
        try {
            return (int) projectRepository.countByAuditStatus(auditStatus);
        } catch (Exception e) {
            return 0;
        }
    }
}