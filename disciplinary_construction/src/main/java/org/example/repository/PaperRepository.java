package org.example.repository;

import org.example.model.PaperModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends MongoRepository<PaperModel, String> {
    List<PaperModel> findByStudentId(String studentId);
    List<PaperModel> findByAuditStatus(String auditStatus);
    List<PaperModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);
}