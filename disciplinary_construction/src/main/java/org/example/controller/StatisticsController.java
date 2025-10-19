package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.service.CompetitionService;
import org.example.service.ProjectService;
import org.example.service.PaperService;
import org.example.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/msi/admin/audit")
@CrossOrigin
public class StatisticsController {

    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private PatentService patentService;

    /**
     * 获取待审核统计
     */
    @GetMapping("/stats")
    public ResponseEntity<?> getAuditStats() {
        try {
            log.info("=== 获取待审核统计数据 ===");
            
            Map<String, Object> stats = new HashMap<>();

            // 竞赛统计
            Map<String, Integer> competitionStats = new HashMap<>();
            competitionStats.put("pending", (int) competitionService.countByAuditStatus("待审核"));
            competitionStats.put("approved", (int) competitionService.countByAuditStatus("已通过"));
            competitionStats.put("rejected", (int) competitionService.countByAuditStatus("已驳回"));
            stats.put("competition", competitionStats);

            // 项目统计
            Map<String, Integer> projectStats = new HashMap<>();
            projectStats.put("pending", (int) projectService.countByAuditStatus("待审核"));
            projectStats.put("approved", (int) projectService.countByAuditStatus("已通过"));
            projectStats.put("rejected", (int) projectService.countByAuditStatus("已驳回"));
            stats.put("project", projectStats);

            // 论文统计
            Map<String, Integer> paperStats = new HashMap<>();
            paperStats.put("pending", (int) paperService.countByAuditStatus("待审核"));
            paperStats.put("approved", (int) paperService.countByAuditStatus("已通过"));
            paperStats.put("rejected", (int) paperService.countByAuditStatus("已驳回"));
            stats.put("paper", paperStats);

            // 专利统计
            Map<String, Integer> patentStats = new HashMap<>();
            patentStats.put("pending", (int) patentService.countByAuditStatus("待审核"));
            patentStats.put("approved", (int) patentService.countByAuditStatus("已通过"));
            patentStats.put("rejected", (int) patentService.countByAuditStatus("已驳回"));
            stats.put("patent", patentStats);

            log.info("统计结果: {}", stats);
            return ResponseEntity.ok(stats);  // ✅ 直接返回 Map
        } catch (Exception e) {
            log.error("获取统计数据失败", e);
            return ResponseEntity.badRequest().body("获取统计数据失败: " + e.getMessage());
        }
    }
}