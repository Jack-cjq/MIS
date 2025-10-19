package org.example.service.Impl;

import org.example.model.PaperModel;
import org.example.repository.PaperRepository;
import org.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map; 
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Override
    public PaperModel addPaper(PaperModel paper) {
        paper.setCreateTime(LocalDateTime.now());
        paper.setUpdateTime(LocalDateTime.now());
        paper.setAuditStatus("待审核");
        return paperRepository.save(paper);
    }

    @Override
    public PaperModel updatePaper(PaperModel paper) {
        paper.setUpdateTime(LocalDateTime.now());
        return paperRepository.save(paper);
    }

    @Override
    public void deletePaper(String id) {
        paperRepository.deleteById(id);
    }

    @Override
    public List<PaperModel> getStudentPapers(String studentId) {
        return paperRepository.findByStudentId(studentId);
    }

    @Override
    public List<PaperModel> getPendingPapers() {
        return paperRepository.findByAuditStatus("待审核");
    }

    @Override
    public PaperModel auditPaper(String id, String auditStatus, String auditComment, String auditorId) {
        PaperModel paper = paperRepository.findById(id).orElse(null);
        if (paper != null) {
            paper.setAuditStatus(auditStatus);
            paper.setAuditComment(auditComment);
            paper.setAuditorId(auditorId);
            paper.setAuditTime(LocalDateTime.now());
            return paperRepository.save(paper);
        }
        return null;
    }

    @Override
    public List<PaperModel> getAllPapers() {
        return paperRepository.findAll();
    }

    @Override
    public PaperModel getPaperById(String id) {
        return paperRepository.findById(id).orElse(null);
    }

    @Override
    public List<PaperModel> getPapersByStudentId(String studentId) {
        return getStudentPapers(studentId); 
    }
    
    @Override
    public PaperModel savePaper(PaperModel paper) {
        if (paper.getId() == null) {
            return addPaper(paper);
        } else {
            return updatePaper(paper);
        }
    }
    
    @Override
    public void deletePaper(Long id) {
        deletePaper(id.toString()); 
    }
    
    @Override
    public PaperModel getPaperById(Long id) {
        return getPaperById(id.toString()); 
    }

    // ==================== 新增：搜索方法 ====================
    @Override
    public List<PaperModel> searchPapers(Map<String, String> params) {
        String keyword = params.get("keyword");
        String journal = params.get("journal");
        String status = params.get("status");
        
        List<PaperModel> allPapers = paperRepository.findAll();
        
        return allPapers.stream()
                .filter(paper -> {
                    boolean matches = true;
                    
                    if (keyword != null && !keyword.isEmpty()) {
                        matches = matches && (
                            (paper.getPaperTitle() != null && paper.getPaperTitle().toLowerCase().contains(keyword.toLowerCase())) ||
                            (paper.getStudentName() != null && paper.getStudentName().toLowerCase().contains(keyword.toLowerCase())) ||
                            (paper.getDoi() != null && paper.getDoi().toLowerCase().contains(keyword.toLowerCase()))
                        );
                    }
                    
                    if (journal != null && !journal.isEmpty()) {
                        matches = matches && journal.equals(paper.getJournalName());
                    }
                    
                    if (status != null && !status.isEmpty()) {
                        matches = matches && status.equals(paper.getAuditStatus());
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
            return (int) paperRepository.countByAuditStatus(auditStatus);
        } catch (Exception e) {
            return 0;
        }
    }
}