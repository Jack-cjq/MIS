package org.example.service;

import org.example.model.PaperModel;
import java.util.List;
import java.util.Map;

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
    
    // 基础查询方法
    PaperModel getPaperById(String id);

     // 兼容方法
    List<PaperModel> getPapersByStudentId(String studentId);
    PaperModel savePaper(PaperModel paper);
    void deletePaper(Long id);
    PaperModel getPaperById(Long id);
    
    // 搜索方法
    List<PaperModel> searchPapers(Map<String, String> params);

    // 统计方法
    int countByAuditStatus(String auditStatus);
}