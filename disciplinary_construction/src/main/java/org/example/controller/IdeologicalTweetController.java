package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.CurrentUser;
import org.example.model.IdeologicalTweetModel;
import org.example.response.ResponseResult;
import org.example.service.IdeologicalTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msi/ideological-tweet")
@CrossOrigin
@Tag(name = "思想动态管理", description = "思想动态相关接口")
public class IdeologicalTweetController {

    @Autowired
    private IdeologicalTweetService ideologicalTweetService;

    @Operation(summary = "发布思想动态")
    @PostMapping("/add")
    public ResponseResult<IdeologicalTweetModel> addIdeologicalTweet(@RequestBody IdeologicalTweetModel ideologicalTweet, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        ideologicalTweet.setStudentId(userId);
        IdeologicalTweetModel result = ideologicalTweetService.addIdeologicalTweet(ideologicalTweet);
        return ResponseResult.success(result);
    }

    @Operation(summary = "更新思想动态")
    @PutMapping("/update")
    public ResponseResult<IdeologicalTweetModel> updateIdeologicalTweet(@RequestBody IdeologicalTweetModel ideologicalTweet, @CurrentUser Map<String, Object> currentUser) {
        String userId = (String) currentUser.get("userId");
        ideologicalTweet.setStudentId(userId);
        IdeologicalTweetModel result = ideologicalTweetService.updateIdeologicalTweet(ideologicalTweet);
        return ResponseResult.success(result);
    }

    @Operation(summary = "删除思想动态")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<String> deleteIdeologicalTweet(@PathVariable String id) {
        ideologicalTweetService.deleteIdeologicalTweet(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取学生思想动态")
    @GetMapping("/student/{studentId}")
    public ResponseResult<List<IdeologicalTweetModel>> getStudentIdeologicalTweets(@PathVariable String studentId) {
        List<IdeologicalTweetModel> result = ideologicalTweetService.getStudentIdeologicalTweets(studentId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据ID获取思想动态")
    @GetMapping("/{id}")
    public ResponseResult<IdeologicalTweetModel> getIdeologicalTweetById(@PathVariable String id) {
        IdeologicalTweetModel result = ideologicalTweetService.getIdeologicalTweetById(id);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取待审核思想动态列表")
    @GetMapping("/pending")
    public ResponseResult<List<IdeologicalTweetModel>> getPendingIdeologicalTweets() {
        List<IdeologicalTweetModel> result = ideologicalTweetService.getPendingIdeologicalTweets();
        return ResponseResult.success(result);
    }

    @Operation(summary = "审核思想动态")
    @PostMapping("/audit/{id}")
    public ResponseResult<IdeologicalTweetModel> auditIdeologicalTweet(
            @PathVariable String id,
            @RequestBody Map<String, String> auditData,
            @CurrentUser Map<String, Object> currentUser) {
        String auditorId = (String) currentUser.get("userId");
        String auditStatus = auditData.get("auditStatus");
        String auditComment = auditData.get("auditComment");
        
        IdeologicalTweetModel result = ideologicalTweetService.auditIdeologicalTweet(id, auditStatus, auditComment, auditorId);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取所有思想动态列表")
    @GetMapping("/list")
    public ResponseResult<List<IdeologicalTweetModel>> getAllIdeologicalTweets() {
        List<IdeologicalTweetModel> result = ideologicalTweetService.getAllIdeologicalTweets();
        return ResponseResult.success(result);
    }

    @Operation(summary = "搜索思想动态")
    @GetMapping("/search")
    public ResponseResult<List<IdeologicalTweetModel>> searchIdeologicalTweets(@RequestParam String keyword) {
        List<IdeologicalTweetModel> result = ideologicalTweetService.searchIdeologicalTweets(keyword);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据动态类型获取思想动态列表")
    @GetMapping("/type/{tweetType}")
    public ResponseResult<List<IdeologicalTweetModel>> getIdeologicalTweetsByType(@PathVariable String tweetType) {
        List<IdeologicalTweetModel> result = ideologicalTweetService.getIdeologicalTweetsByType(tweetType);
        return ResponseResult.success(result);
    }

    @Operation(summary = "根据状态获取思想动态列表")
    @GetMapping("/status/{status}")
    public ResponseResult<List<IdeologicalTweetModel>> getIdeologicalTweetsByStatus(@PathVariable String status) {
        List<IdeologicalTweetModel> result = ideologicalTweetService.getIdeologicalTweetsByStatus(status);
        return ResponseResult.success(result);
    }
} 