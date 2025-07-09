package org.example.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.model.AlumniModel;
import org.example.response.ResponseCode;
import org.example.response.ResponseResult;
import org.example.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.Date;
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

    @Operation(summary = "分页模糊查询校友信息")
    @PostMapping("/searchAlumniList")
    public ResponseResult<List<AlumniModel>> searchAlumniList(
            String searchValue,
            @RequestParam(defaultValue = "") String searchYear,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Date year = null;
        if (!searchYear.isEmpty()) {
            year = Date.from(
                    LocalDate.of(Integer.parseInt(searchYear), 1, 1)
                            .atStartOfDay(ZoneId.of("Asia/Shanghai"))
                            .toInstant()
            );
        }
        return ResponseResult.success(alumniService.searchAlumniList(searchValue, year, currentPage, pageSize));
    }

    @Operation(summary = "分页模糊查询校友信息个数")
    @PostMapping("/getTotalCount")
    public ResponseResult<Integer> getTotalCount(
            String searchValue,
            @RequestParam(defaultValue = "") String searchYear
    ) {
        Date year = null;
        if (!searchYear.isEmpty()) {
            year = Date.from(
                    LocalDate.of(Integer.parseInt(searchYear), 1, 1)
                            .atStartOfDay(ZoneId.of("Asia/Shanghai"))
                            .toInstant()
            );
        }
        return ResponseResult.success(alumniService.getTotalCount(searchValue, year));
    }
}
