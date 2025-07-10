package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.CompetitionDictModel;
import org.example.response.ResponseResult;
import org.example.service.CompetitionDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/msi/competition-dict")
@Tag(name = "竞赛字典管理", description = "竞赛字典相关接口")
public class CompetitionDictController {

    @Autowired
    private CompetitionDictService competitionDictService;

    @Operation(summary = "搜索竞赛")
    @GetMapping("/search")
    public ResponseResult<List<CompetitionDictModel>> searchCompetitions(@RequestParam String keyword) {
        List<CompetitionDictModel> competitions = competitionDictService.searchCompetitions(keyword);
        return ResponseResult.success(competitions);
    }

    @Operation(summary = "获取所有可用竞赛")
    @GetMapping("/active")
    public ResponseResult<List<CompetitionDictModel>> getAllActiveCompetitions() {
        List<CompetitionDictModel> competitions = competitionDictService.getAllActiveCompetitions();
        return ResponseResult.success(competitions);
    }

    @Operation(summary = "根据名称获取竞赛信息")
    @GetMapping("/name/{name}")
    public ResponseResult<CompetitionDictModel> getCompetitionByName(@PathVariable String name) {
        CompetitionDictModel competition = competitionDictService.getCompetitionByName(name);
        return ResponseResult.success(competition);
    }

    // 管理员功能
    @Operation(summary = "管理员添加竞赛")
    @PostMapping("/admin/add")
    public ResponseResult<CompetitionDictModel> addCompetition(@RequestBody CompetitionDictModel competition) {
        CompetitionDictModel result = competitionDictService.addCompetition(competition);
        return ResponseResult.success(result);
    }

    @Operation(summary = "管理员修改竞赛")
    @PutMapping("/admin/update")
    public ResponseResult<CompetitionDictModel> updateCompetition(@RequestBody CompetitionDictModel competition) {
        CompetitionDictModel result = competitionDictService.updateCompetition(competition);
        return ResponseResult.success(result);
    }

    @Operation(summary = "管理员删除竞赛")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseResult<String> deleteCompetition(@PathVariable String id) {
        competitionDictService.deleteCompetition(id);
        return ResponseResult.success("删除成功");
    }

    @Operation(summary = "获取所有竞赛（包含禁用的）")
    @GetMapping("/admin/all")
    public ResponseResult<List<CompetitionDictModel>> getAllCompetitions() {
        List<CompetitionDictModel> competitions = competitionDictService.getAllCompetitions();
        return ResponseResult.success(competitions);
    }

}