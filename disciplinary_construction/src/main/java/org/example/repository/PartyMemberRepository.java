package org.example.repository;

import org.example.model.PartyMemberModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyMemberRepository extends MongoRepository<PartyMemberModel, String> {
    List<PartyMemberModel> findByStudentId(String studentId);
    List<PartyMemberModel> findByAuditStatus(String auditStatus);
    List<PartyMemberModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);
    List<PartyMemberModel> findByBranch(String branch);
    List<PartyMemberModel> findByIsRegular(String isRegular);
    List<PartyMemberModel> findByNameContaining(String name);
    List<PartyMemberModel> findByStudentIdContaining(String studentId);
} 