package org.example.controller;

import org.example.model.PatentModel;
import org.example.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/patents")
@CrossOrigin
public class PatentController {

    @Autowired
    private PatentService patentService;

    // ==================== 学生端接口 ====================

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentPatents(@PathVariable String studentId) {
        try {
            List<PatentModel> patents = patentService.getPatentsByStudentId(studentId);
            return ResponseEntity.ok(patents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取专利数据失败: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addPatent(@RequestBody PatentModel patent) {
        try {
            if (patent.getAuditStatus() == null) {
                patent.setAuditStatus("待审核");
            }
            PatentModel saved = patentService.savePatent(patent);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("添加专利失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatent(@PathVariable String id, @RequestBody PatentModel patent) {
        try {
            patent.setId(id);
            PatentModel updated = patentService.savePatent(patent);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("更新专利失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatent(@PathVariable String id) {
        try {
            patentService.deletePatent(id);
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("删除专利失败: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatent(@PathVariable String id) {
        try {
            PatentModel patent = patentService.getPatentById(id);
            if (patent == null) {
                return ResponseEntity.badRequest().body("专利不存在");
            }
            return ResponseEntity.ok(patent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取专利失败: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPatents(@RequestParam Map<String, String> params) {
        try {
            List<PatentModel> patents = patentService.searchPatents(params);
            return ResponseEntity.ok(patents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("搜索专利失败: " + e.getMessage());
        }
    }

    // ==================== 管理员端接口 ====================

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllPatents() {
        try {
            List<PatentModel> patents = patentService.getAllPatents();
            return ResponseEntity.ok(patents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取专利数据失败: " + e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/audit")
    public ResponseEntity<?> auditPatent(@PathVariable String id, @RequestBody Map<String, String> auditData) {
        try {
            String auditStatus = auditData.get("auditStatus");
            String auditComment = auditData.get("auditComment");
            String auditorId = auditData.get("auditorId"); 
            String auditorName = auditData.get("auditorName"); 

            PatentModel patent = patentService.getPatentById(id);
            if (patent == null) {
                return ResponseEntity.badRequest().body("专利不存在");
            }
            
            patent.setAuditStatus(auditStatus);
            patent.setAuditComment(auditComment);
            patent.setAuditorId(auditorId);
            patent.setAuditorName(auditorName);

            PatentModel updated = patentService.savePatent(patent);
            
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("审核失败: " + e.getMessage());
        }
    }
}