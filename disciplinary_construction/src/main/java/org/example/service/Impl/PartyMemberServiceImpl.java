package org.example.service.Impl;

import org.example.model.PartyMemberModel;
import org.example.repository.PartyMemberRepository;
import org.example.service.PartyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartyMemberServiceImpl implements PartyMemberService {

    @Autowired
    private PartyMemberRepository partyMemberRepository;

    @Override
    public PartyMemberModel addPartyMember(PartyMemberModel partyMember) {
        partyMember.setCreateTime(LocalDateTime.now());
        partyMember.setUpdateTime(LocalDateTime.now());
        partyMember.setAuditStatus("待审核");
        return partyMemberRepository.save(partyMember);
    }

    @Override
    public PartyMemberModel updatePartyMember(PartyMemberModel partyMember) {
        partyMember.setUpdateTime(LocalDateTime.now());
        return partyMemberRepository.save(partyMember);
    }

    @Override
    public void deletePartyMember(String id) {
        partyMemberRepository.deleteById(id);
    }

    @Override
    public List<PartyMemberModel> getStudentPartyMembers(String studentId) {
        return partyMemberRepository.findByStudentId(studentId);
    }

    @Override
    public PartyMemberModel getPartyMemberById(String id) {
        return partyMemberRepository.findById(id).orElse(null);
    }

    @Override
    public List<PartyMemberModel> getPendingPartyMembers() {
        return partyMemberRepository.findByAuditStatus("待审核");
    }

    @Override
    public PartyMemberModel auditPartyMember(String id, String auditStatus, String auditComment, String auditorId) {
        PartyMemberModel partyMember = partyMemberRepository.findById(id).orElse(null);
        if (partyMember != null) {
            partyMember.setAuditStatus(auditStatus);
            partyMember.setAuditComment(auditComment);
            partyMember.setAuditorId(auditorId);
            partyMember.setAuditTime(LocalDateTime.now());
            return partyMemberRepository.save(partyMember);
        }
        return null;
    }

    @Override
    public List<PartyMemberModel> getAllPartyMembers() {
        return partyMemberRepository.findAll();
    }

    @Override
    public List<PartyMemberModel> searchPartyMembers(String keyword) {
        List<PartyMemberModel> byName = partyMemberRepository.findByNameContaining(keyword);
        List<PartyMemberModel> byStudentId = partyMemberRepository.findByStudentIdContaining(keyword);
        byName.addAll(byStudentId);
        return byName;
    }

    @Override
    public List<PartyMemberModel> getPartyMembersByBranch(String branch) {
        return partyMemberRepository.findByBranch(branch);
    }

    @Override
    public List<PartyMemberModel> getPartyMembersByStatus(String isRegular) {
        return partyMemberRepository.findByIsRegular(isRegular);
    }

    // 创建默认党员数据
    public void createDefaultPartyMember() {
        // 为默认学生创建党员信息
        if (partyMemberRepository.findByStudentId("10240001").isEmpty()) {
            PartyMemberModel partyMember1 = new PartyMemberModel();
            partyMember1.setStudentId("10240001");
            partyMember1.setName("默认学生");
            partyMember1.setGender("男");
            partyMember1.setNation("汉族");
            partyMember1.setBirth("1999-01-01");
            partyMember1.setNativePlace("湖北省");
            partyMember1.setClassName("研一1班");
            partyMember1.setBranch("计算机学院党支部");
            partyMember1.setJoinDate("2022-06-01");
            partyMember1.setIsRegular("是");
            partyMember1.setApplicationNo("ZYS2022001");
            partyMember1.setPosition("组织委员");
            partyMember1.setRewardsPunishments("无");
            partyMember1.setRemark("默认党员数据");
            partyMember1.setAuditStatus("通过");
            partyMember1.setCreateTime(LocalDateTime.now());
            partyMember1.setUpdateTime(LocalDateTime.now());
            partyMemberRepository.save(partyMember1);
        }

        // 为测试学生创建党员信息
        if (partyMemberRepository.findByStudentId("20240001").isEmpty()) {
            PartyMemberModel partyMember2 = new PartyMemberModel();
            partyMember2.setStudentId("20240001");
            partyMember2.setName("学生111");
            partyMember2.setGender("女");
            partyMember2.setNation("汉族");
            partyMember2.setBirth("2000-01-01");
            partyMember2.setNativePlace("湖北省");
            partyMember2.setClassName("研一2班");
            partyMember2.setBranch("软件学院党支部");
            partyMember2.setJoinDate("2023-03-15");
            partyMember2.setIsRegular("否");
            partyMember2.setApplicationNo("ZYS2023001");
            partyMember2.setPosition("预备党员");
            partyMember2.setRewardsPunishments("无");
            partyMember2.setRemark("测试党员数据");
            partyMember2.setAuditStatus("通过");
            partyMember2.setCreateTime(LocalDateTime.now());
            partyMember2.setUpdateTime(LocalDateTime.now());
            partyMemberRepository.save(partyMember2);
        }

        // 为20230001学生创建党员信息（用于测试）
        if (partyMemberRepository.findByStudentId("20230001").isEmpty()) {
            PartyMemberModel partyMember3 = new PartyMemberModel();
            partyMember3.setStudentId("20230001");
            partyMember3.setName("张三");
            partyMember3.setGender("男");
            partyMember3.setNation("汉族");
            partyMember3.setBirth("2000-01-01");
            partyMember3.setNativePlace("江苏南京");
            partyMember3.setClassName("软件工程1班");
            partyMember3.setBranch("计算机学院党支部");
            partyMember3.setJoinDate("2022-06-01");
            partyMember3.setIsRegular("是");
            partyMember3.setApplicationNo("ZYS2022001");
            partyMember3.setPosition("组织委员");
            partyMember3.setRewardsPunishments("无");
            partyMember3.setRemark("测试党员数据");
            partyMember3.setAuditStatus("通过");
            partyMember3.setCreateTime(LocalDateTime.now());
            partyMember3.setUpdateTime(LocalDateTime.now());
            partyMemberRepository.save(partyMember3);
        }
    }
} 