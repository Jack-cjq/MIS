package org.example.repository;

import org.example.model.PatentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatentRepository extends MongoRepository<PatentModel, String> {
    List<PatentModel> findByStudentId(String studentId);
    List<PatentModel> findByAuditStatus(String auditStatus);
    List<PatentModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);
}