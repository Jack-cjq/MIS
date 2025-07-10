package org.example.repository;

import org.example.model.ProjectModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<ProjectModel, String> {
    List<ProjectModel> findByStudentId(String studentId);
    List<ProjectModel> findByAuditStatus(String auditStatus);
    List<ProjectModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);
}