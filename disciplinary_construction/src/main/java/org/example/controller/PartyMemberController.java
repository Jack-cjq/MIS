package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.CurrentUser;
import org.example.model.PartyMemberModel;
import org.example.response.ResponseResult;
import org.example.service.PartyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/party-member")
@CrossOrigin
@Tag(name = "党员管理", description = "党员相关接口")
public class PartyMemberController {

    @Autowired
    private PartyMemberService partyMemberService;

    @Operation(summary = "添加党员信息")
    @PostMapping("/add")
    public ResponseResult<PartyMemberModel> addPartyMember(@RequestBody PartyMemberModel partyMember, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        partyMember.setStudentId(userId);
        PartyMemberModel result = partyMemberService.addPartyMember(partyMember);
        return ResponseResult.success(result);
    }

    @Operation(summary = "更新党员信息")
    @PutMapping("/update")
    public ResponseResult<PartyMemberModel> updatePartyMember(@RequestBody PartyMemberModel partyMember, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        partyMember.setStudentId(userId);
        PartyMemberModel result = partyMemberService.updatePartyMember(partyMember);
        return ResponseResult.success(result);
    }

    @Operation(summary = "删除党员信息")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<String> deletePartyMember(@PathVariable String id) {
        partyMemberService.deletePartyMember(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取学生党员信息")
    @GetMapping("/student/{studentId}")
    public ResponseResult<List<PartyMemberModel>> getStudentPartyMembers(@PathVariable String studentId) {
        List<PartyMemberModel> result = partyMemberService.getStudentPartyMembers(studentId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据ID获取党员信息")
    @GetMapping("/{id}")
    public ResponseResult<PartyMemberModel> getPartyMemberById(@PathVariable String id) {
        PartyMemberModel result = partyMemberService.getPartyMemberById(id);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取待审核党员列表")
    @GetMapping("/pending")
    public ResponseResult<List<PartyMemberModel>> getPendingPartyMembers() {
        List<PartyMemberModel> result = partyMemberService.getPendingPartyMembers();
        return ResponseResult.success(result);
    }

    @Operation(summary = "审核党员信息")
    @PostMapping("/audit/{id}")
    public ResponseResult<PartyMemberModel> auditPartyMember(
            @PathVariable String id,
            @RequestBody Map<String, String> auditData,
            @CurrentUser Map<String, Object> currentUser) {
        String auditorId = (String) currentUser.get("userId");
        String auditStatus = auditData.get("auditStatus");
        String auditComment = auditData.get("auditComment");
        
        PartyMemberModel result = partyMemberService.auditPartyMember(id, auditStatus, auditComment, auditorId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取所有党员列表")
    @GetMapping("/list")
    public ResponseResult<List<PartyMemberModel>> getAllPartyMembers() {
        List<PartyMemberModel> result = partyMemberService.getAllPartyMembers();
        return ResponseResult.success(result);
    }

    @Operation(summary = "搜索党员")
    @GetMapping("/search")
    public ResponseResult<List<PartyMemberModel>> searchPartyMembers(@RequestParam String keyword) {
        List<PartyMemberModel> result = partyMemberService.searchPartyMembers(keyword);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据党支部获取党员列表")
    @GetMapping("/branch/{branch}")
    public ResponseResult<List<PartyMemberModel>> getPartyMembersByBranch(@PathVariable String branch) {
        List<PartyMemberModel> result = partyMemberService.getPartyMembersByBranch(branch);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据党员状态获取列表")
    @GetMapping("/status/{isRegular}")
    public ResponseResult<List<PartyMemberModel>> getPartyMembersByStatus(@PathVariable String isRegular) {
        List<PartyMemberModel> result = partyMemberService.getPartyMembersByStatus(isRegular);
        return ResponseResult.success(result);
    }
} 