package org.example.service.Impl;

import org.example.model.PatentModel;
import org.example.repository.PatentRepository;
import org.example.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; 

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

    @Override
    public List<PatentModel> getPatentsByStudentId(String studentId) {
        return getStudentPatents(studentId);
   }

    @Override
    public PatentModel savePatent(PatentModel patent) {
        if (patent.getId() == null) {
            return addPatent(patent);
        } else {
            return updatePatent(patent);
        }
    }

    @Override
    public void deletePatent(Long id) {
        deletePatent(id.toString());
    }

    @Override
    public PatentModel getPatentById(Long id) {
        return patentRepository.findById(id.toString()).orElse(null);
    }

    @Override
    public PatentModel getPatentById(String id) {
        return patentRepository.findById(id).orElse(null);
    }

    // ==================== 搜索方法 ====================
    @Override
    public List<PatentModel> searchPatents(Map<String, String> params) {
        String keyword = params.get("keyword");
        String patentType = params.get("patentType");
        String status = params.get("status");

        List<PatentModel> allPatents = patentRepository.findAll();

        return allPatents.stream()
                .filter(patent -> {
                    boolean matches = true;
                    
                    if (keyword != null && !keyword.isEmpty()) {
                        matches = matches && (
                            (patent.getPatentTitle() != null && patent.getPatentTitle().toLowerCase().contains(keyword.toLowerCase())) ||
                            (patent.getStudentName() != null && patent.getStudentName().toLowerCase().contains(keyword.toLowerCase())) ||
                            (patent.getPatentNumber() != null && patent.getPatentNumber().toLowerCase().contains(keyword.toLowerCase()))
                        );
                    }
                    
                    if (patentType != null && !patentType.isEmpty()) {
                        matches = matches && patentType.equals(patent.getPatentType());
                    }
                    
                    if (status != null && !status.isEmpty()) {
                        matches = matches && status.equals(patent.getAuditStatus());
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
            return (int) patentRepository.countByAuditStatus(auditStatus);
        } catch (Exception e) {
            return 0;
        }
    }
}