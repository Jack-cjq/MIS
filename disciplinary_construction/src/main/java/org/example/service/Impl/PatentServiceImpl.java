package org.example.service.Impl;

import org.example.model.PatentModel;
import org.example.repository.PatentRepository;
import org.example.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatentServiceImpl implements PatentService {

    @Autowired
    private PatentRepository patentRepository;

    @Override
    public PatentModel addPatent(PatentModel patent) {
        patent.setCreateTime(LocalDateTime.now());
        patent.setUpdateTime(LocalDateTime.now());
        patent.setAuditStatus("待审核");
        return patentRepository.save(patent);
    }

    @Override
    public PatentModel updatePatent(PatentModel patent) {
        patent.setUpdateTime(LocalDateTime.now());
        return patentRepository.save(patent);
    }

    @Override
    public void deletePatent(String id) {
        patentRepository.deleteById(id);
    }

    @Override
    public List<PatentModel> getStudentPatents(String studentId) {
        return patentRepository.findByStudentId(studentId);
    }

    @Override
    public List<PatentModel> getPendingPatents() {
        return patentRepository.findByAuditStatus("待审核");
    }

    @Override
    public PatentModel auditPatent(String id, String auditStatus, String auditComment, String auditorId) {
        PatentModel patent = patentRepository.findById(id).orElse(null);
        if (patent != null) {
            patent.setAuditStatus(auditStatus);
            patent.setAuditComment(auditComment);
            patent.setAuditorId(auditorId);
            patent.setAuditTime(LocalDateTime.now());
            return patentRepository.save(patent);
        }
        return null;
    }

    @Override
    public List<PatentModel> getAllPatents() {
        return patentRepository.findAll();
    }
}