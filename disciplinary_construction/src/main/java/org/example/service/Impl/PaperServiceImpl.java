package org.example.service.Impl;

import org.example.model.PaperModel;
import org.example.repository.PaperRepository;
import org.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}