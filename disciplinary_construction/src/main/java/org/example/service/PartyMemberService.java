package org.example.service;

import org.example.model.PartyMemberModel;
import java.util.List;

public interface PartyMemberService {
    // 学生操作
    PartyMemberModel addPartyMember(PartyMemberModel partyMember);
    PartyMemberModel updatePartyMember(PartyMemberModel partyMember);
    void deletePartyMember(String id);
    List<PartyMemberModel> getStudentPartyMembers(String studentId);
    PartyMemberModel getPartyMemberById(String id);

    // 管理员操作
    List<PartyMemberModel> getPendingPartyMembers();
    PartyMemberModel auditPartyMember(String id, String auditStatus, String auditComment, String auditorId);
    List<PartyMemberModel> getAllPartyMembers();
    List<PartyMemberModel> searchPartyMembers(String keyword);
    List<PartyMemberModel> getPartyMembersByBranch(String branch);
    List<PartyMemberModel> getPartyMembersByStatus(String isRegular);
} 