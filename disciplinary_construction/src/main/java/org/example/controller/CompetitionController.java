package org.example.controller;


import org.example.model.CompetitionModel;
import org.example.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/competitions")
@CrossOrigin
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;


    // ==================== 学生端接口 ====================

    /**
     * 获取学生的竞赛数据
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentCompetitions(@PathVariable String studentId) {
        try {
            List<CompetitionModel> competitions = competitionService.getStudentCompetitions(studentId);
            return ResponseEntity.ok(competitions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取竞赛数据失败: " + e.getMessage());
        }
    }

    /**
     * 添加竞赛
     */
    @PostMapping
    public ResponseEntity<?> addCompetition(@RequestBody CompetitionModel competition) {
        try {
            if (competition.getAuditStatus() == null) {
                competition.setAuditStatus("待审核");
            }
            CompetitionModel saved = competitionService.addCompetition(competition);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("添加竞赛失败: " + e.getMessage());
        }
    }

    /**
     * 更新竞赛
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompetition(@PathVariable String id, @RequestBody CompetitionModel competition) {
        try {
            // 注意：这里改为 String 类型的 id
            competition.setId(id);
            CompetitionModel updated = competitionService.updateCompetition(competition);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("更新竞赛失败: " + e.getMessage());
        }
    }

    /**
     * 删除竞赛
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompetition(@PathVariable String id) {
        try {
            competitionService.deleteCompetition(id);
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("删除竞赛失败: " + e.getMessage());
        }
    }


    // 获取单条竞赛记录
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompetition(@PathVariable String id) {
        try {
            CompetitionModel competition = competitionService.getCompetitionById(id);
            if (competition == null) {
                return ResponseEntity.badRequest().body("竞赛不存在");
            }
            return ResponseEntity.ok(competition);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取竞赛失败: " + e.getMessage());
        }
    }

    // 获取活跃的竞赛列表（用于下拉选择）
    @GetMapping("/active")
    public ResponseEntity<?> getActiveCompetitions() {
        try {
            List<CompetitionModel> competitions = competitionService.getActiveCompetitions();
            return ResponseEntity.ok(competitions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取活跃竞赛失败: " + e.getMessage());
        }
    }

    // 新增：搜索竞赛
    @GetMapping("/search")
    public ResponseEntity<?> searchCompetitions(@RequestParam Map<String, String> params) {
        try {
            List<CompetitionModel> competitions = competitionService.searchCompetitions(params);
            return ResponseEntity.ok(competitions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("搜索竞赛失败: " + e.getMessage());
        }
    }

    // ==================== 管理员端接口 ====================

    /**
     * 获取所有竞赛数据（管理员）
     */
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllCompetitions() {
        try {
            List<CompetitionModel> competitions = competitionService.getAllCompetitions();
            return ResponseEntity.ok(competitions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取竞赛数据失败: " + e.getMessage());
        }
    }

    /**
     * 审核竞赛
     */
    @PutMapping("/admin/{id}/audit")
    public ResponseEntity<?> auditCompetition(@PathVariable String id, @RequestBody Map<String, String> auditData) {
        try {
            String auditStatus = auditData.get("auditStatus");
            String auditComment = auditData.get("auditComment");
            String auditorId = auditData.get("auditorId");
            String auditorName = auditData.get("auditorName");
            
            CompetitionModel competition = competitionService.getCompetitionById(id);
            if (competition == null) {
                return ResponseEntity.badRequest().body("竞赛不存在");
            }
            
            competition.setAuditStatus(auditStatus);
            competition.setAuditComment(auditComment);
            competition.setAuditorId(auditorId);
            competition.setAuditorName(auditorName);

            CompetitionModel updated = competitionService.saveCompetition(competition);
            
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("审核失败: " + e.getMessage());
        }
    }
}