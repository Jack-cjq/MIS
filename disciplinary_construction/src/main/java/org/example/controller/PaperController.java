package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.CurrentUser;
import org.example.model.PaperModel;
import org.example.response.ResponseResult;
import org.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/paper")
@CrossOrigin
@Tag(name = "发表文章管理", description = "发表文章相关接口")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Operation(summary = "添加发表文章")
    @PostMapping("/add")
    public ResponseResult<PaperModel> addPaper(@RequestBody PaperModel paper, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        paper.setStudentId(userId);
        PaperModel result = paperService.addPaper(paper);
        return ResponseResult.success(result);
    }

    @Operation(summary = "更新发表文章")
    @PutMapping("/update")
    public ResponseResult<PaperModel> updatePaper(@RequestBody PaperModel paper, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        paper.setStudentId(userId);
        PaperModel result = paperService.updatePaper(paper);
        return ResponseResult.success(result);
    }

    @Operation(summary = "删除发表文章")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<String> deletePaper(@PathVariable String id) {
        paperService.deletePaper(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取当前用户的发表文章列表")
    @GetMapping("/my-papers")
    public ResponseResult<List<PaperModel>> getMyPapers(@CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        List<PaperModel> papers = paperService.getStudentPapers(userId);
        return ResponseResult.success(papers);
    }

    @Operation(summary = "获取待审核的发表文章列表")
    @GetMapping("/pending")
    public ResponseResult<List<PaperModel>> getPendingPapers() {
        List<PaperModel> papers = paperService.getPendingPapers();
        return ResponseResult.success(papers);
    }

    @Operation(summary = "审核发表文章")
    @PostMapping("/audit")
    public ResponseResult<PaperModel> auditPaper(
            @RequestParam String id,
            @RequestParam String auditStatus,
            @RequestParam String auditComment,
            @RequestParam String auditorId) {
        PaperModel result = paperService.auditPaper(id, auditStatus, auditComment, auditorId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取所有发表文章")
    @GetMapping("/all")
    public ResponseResult<List<PaperModel>> getAllPapers() {
        List<PaperModel> papers = paperService.getAllPapers();
        return ResponseResult.success(papers);
    }
}