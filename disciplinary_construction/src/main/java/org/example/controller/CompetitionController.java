package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.CompetitionModel;
import org.example.response.ResponseResult;
import org.example.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msi/competition")
@CrossOrigin
@Tag(name = "学科竞赛管理", description = "学科竞赛相关接口")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @Operation(summary = "添加学科竞赛")
    @PostMapping("/add")
    public ResponseResult<CompetitionModel> addCompetition(@RequestBody CompetitionModel competition) {
        CompetitionModel result = competitionService.addCompetition(competition);
        return ResponseResult.success(result);
    }

    @Operation(summary = "更新学科竞赛")
    @PutMapping("/update")
    public ResponseResult<CompetitionModel> updateCompetition(@RequestBody CompetitionModel competition) {
        CompetitionModel result = competitionService.updateCompetition(competition);
        return ResponseResult.success(result);
    }

    @Operation(summary = "删除学科竞赛")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<String> deleteCompetition(@PathVariable String id) {
        competitionService.deleteCompetition(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取学生的学科竞赛列表")
    @GetMapping("/student/{studentId}")
    public ResponseResult<List<CompetitionModel>> getStudentCompetitions(@PathVariable String studentId) {
        List<CompetitionModel> competitions = competitionService.getStudentCompetitions(studentId);
        return ResponseResult.success(competitions);
    }

    @Operation(summary = "获取待审核的学科竞赛列表")
    @GetMapping("/pending")
    public ResponseResult<List<CompetitionModel>> getPendingCompetitions() {
        List<CompetitionModel> competitions = competitionService.getPendingCompetitions();
        return ResponseResult.success(competitions);
    }

    @Operation(summary = "审核学科竞赛")
    @PostMapping("/audit")
    public ResponseResult<CompetitionModel> auditCompetition(
            @RequestParam String id,
            @RequestParam String auditStatus,
            @RequestParam String auditComment,
            @RequestParam String auditorId) {
        CompetitionModel result = competitionService.auditCompetition(id, auditStatus, auditComment, auditorId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取所有学科竞赛")
    @GetMapping("/all")
    public ResponseResult<List<CompetitionModel>> getAllCompetitions() {
        List<CompetitionModel> competitions = competitionService.getAllCompetitions();
        return ResponseResult.success(competitions);
    }
}