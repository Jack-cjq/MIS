package org.example.service;

import org.example.model.PaperModel;
import java.util.List;

public interface PaperService {
    // 学生操作
    PaperModel addPaper(PaperModel paper);
    PaperModel updatePaper(PaperModel paper);
    void deletePaper(String id);
    List<PaperModel> getStudentPapers(String studentId);

    // 管理员操作
    List<PaperModel> getPendingPapers();
    PaperModel auditPaper(String id, String auditStatus, String auditComment, String auditorId);
    List<PaperModel> getAllPapers();
}