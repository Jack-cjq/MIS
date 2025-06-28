package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.model.AlumniModel;
import org.example.response.ResponseResult;
import org.example.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/msi/alumni")
@Tag(name = "校友管理接口")
public class AlumniController {
    @Autowired
    private AlumniService alumniService;

    public AlumniController() {
        log.info("AlumniController已创建");
    }

    @Operation(summary = "添加校友信息")
    @PostMapping("/insertAlumni")
    public ResponseResult<String> insertAlumni(@RequestBody AlumniModel alumniModel) {
        alumniService.insertAlumni(alumniModel);
        return ResponseResult.success();
    }

    @Operation(summary = "更新校友信息")
    @PostMapping("/updateAlumni")
    public ResponseResult<String> updateAlumni(@RequestBody AlumniModel alumniModel) {
        alumniService.updateAlumni(alumniModel);
        return ResponseResult.success();
    }

    @Operation(summary = "通过学号查询校友信息")
    @PostMapping("/findAlumniByStudentId")
    public ResponseResult<AlumniModel> findAlumniByStudentId(String studentId) {
        AlumniModel alumni = alumniService.findAlumniByStudentId(studentId);
        return ResponseResult.success(alumni);
    }

    @Operation(summary = "通过名称查询校友信息")
    @PostMapping("/findAlumniByName")
    public ResponseResult<List<AlumniModel>> findAlumniByName(String name) {
        List<AlumniModel> alumniList = alumniService.findAlumniByName(name);
        return ResponseResult.success(alumniList);
    }

    @Operation(summary = "查询所有校友信息")
    @PostMapping("/findAllAlumni")
    public ResponseResult<List<AlumniModel>> findAllAlumni() {
        List<AlumniModel> alumniList = alumniService.findAllAlumni();
        return ResponseResult.success(alumniList);
    }
}
