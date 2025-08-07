package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.CurrentUser;
import org.example.model.PatentModel;
import org.example.response.ResponseResult;
import org.example.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/patent")
@CrossOrigin
@Tag(name = "专利管理", description = "专利相关接口")
public class PatentController {

    @Autowired
    private PatentService patentService;

    @Operation(summary = "添加专利")
    @PostMapping("/add")
    public ResponseResult<PatentModel> addPatent(@RequestBody PatentModel patent, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        patent.setStudentId(userId);
        PatentModel result = patentService.addPatent(patent);
        return ResponseResult.success(result);
    }

    @Operation(summary = "更新专利")
    @PutMapping("/update")
    public ResponseResult<PatentModel> updatePatent(@RequestBody PatentModel patent, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        patent.setStudentId(userId);
        PatentModel result = patentService.updatePatent(patent);
        return ResponseResult.success(result);
    }

    @Operation(summary = "删除专利")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<String> deletePatent(@PathVariable String id) {
        patentService.deletePatent(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取当前用户的专利列表")
    @GetMapping("/my-patents")
    public ResponseResult<List<PatentModel>> getMyPatents(@CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        List<PatentModel> patents = patentService.getStudentPatents(userId);
        return ResponseResult.success(patents);
    }

    @Operation(summary = "获取待审核的专利列表")
    @GetMapping("/pending")
    public ResponseResult<List<PatentModel>> getPendingPatents() {
        List<PatentModel> patents = patentService.getPendingPatents();
        return ResponseResult.success(patents);
    }

    @Operation(summary = "审核专利")
    @PostMapping("/audit")
    public ResponseResult<PatentModel> auditPatent(
            @RequestParam String id,
            @RequestParam String auditStatus,
            @RequestParam String auditComment,
            @RequestParam String auditorId) {
        PatentModel result = patentService.auditPatent(id, auditStatus, auditComment, auditorId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取所有专利")
    @GetMapping("/all")
    public ResponseResult<List<PatentModel>> getAllPatents() {
        List<PatentModel> patents = patentService.getAllPatents();
        return ResponseResult.success(patents);
    }
}