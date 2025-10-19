package org.example.repository;

import org.example.model.CompetitionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends MongoRepository<CompetitionModel, String> {
    List<CompetitionModel> findByStudentId(String studentId);
    List<CompetitionModel> findByAuditStatus(String auditStatus);
    List<CompetitionModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);

    long countByAuditStatus(String auditStatus);
}