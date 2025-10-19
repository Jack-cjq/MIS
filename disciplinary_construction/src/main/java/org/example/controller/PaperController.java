package org.example.controller;

import org.example.model.PaperModel;
import org.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/papers")
@CrossOrigin
public class PaperController {

    @Autowired
    private PaperService paperService;

    // ==================== 学生端接口 ====================

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentPapers(@PathVariable String studentId) {
        try {
            List<PaperModel> papers = paperService.getPapersByStudentId(studentId);
            return ResponseEntity.ok(papers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取论文数据失败: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addPaper(@RequestBody PaperModel paper) {
        try {
            if (paper.getAuditStatus() == null) {
                paper.setAuditStatus("待审核");
            }
            PaperModel saved = paperService.savePaper(paper);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("添加论文失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaper(
            @PathVariable String id,
            @RequestBody PaperModel paper) {
        try {
            paper.setId(id);
            PaperModel updated = paperService.savePaper(paper);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("更新论文失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaper(@PathVariable String id) {
        try {
            paperService.deletePaper(id);
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("删除论文失败: " + e.getMessage());
        }
    }

    // 获取单条论文记录
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaper(@PathVariable String id) {
        try {
            PaperModel paper = paperService.getPaperById(id);
            if (paper == null) {
                return ResponseEntity.badRequest().body("论文不存在");
            }
            return ResponseEntity.ok(paper);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取论文失败: " + e.getMessage());
        }
    }

    // 搜索论文
    @GetMapping("/search")
    public ResponseEntity<?> searchPapers(@RequestParam Map<String, String> params) {
        try {
            List<PaperModel> papers = paperService.searchPapers(params);
            return ResponseEntity.ok(papers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("搜索论文失败: " + e.getMessage());
        }
    }

    // ==================== 管理员端接口 ====================

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllPapers() {
        try {
            List<PaperModel> papers = paperService.getAllPapers();
            return ResponseEntity.ok(papers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取论文数据失败: " + e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/audit")
    public ResponseEntity<?> auditPaper(
            @PathVariable String id, 
            @RequestBody Map<String, String> auditData) {
        try {
            String auditStatus = auditData.get("auditStatus");
            String auditComment = auditData.get("auditComment");
            String auditorId = auditData.get("auditorId"); 
            String auditorName = auditData.get("auditorName");
            
            PaperModel paper = paperService.getPaperById(id);
            if (paper == null) {
                return ResponseEntity.badRequest().body("论文不存在");
            }
            
            paper.setAuditStatus(auditStatus);
            paper.setAuditComment(auditComment);
            paper.setAuditorId(auditorId);
            paper.setAuditorName(auditorName);
            
            PaperModel updated = paperService.savePaper(paper);
            
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("审核失败: " + e.getMessage());
        }
    }
}