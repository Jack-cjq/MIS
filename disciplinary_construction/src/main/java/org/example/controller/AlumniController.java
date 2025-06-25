package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.model.AlumniModel;
import org.example.response.ResponseResult;
import org.example.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("/msi/alumni")
public class AlumniController {
    @Autowired
    private AlumniService alumniService;

    public AlumniController() {
        log.info("AlumniController已创建");
    }

    @PostMapping("/insertAlumni")
    public ResponseResult<String> insertAlumni(AlumniModel alumniModel) {
        alumniService.insertAlumni(alumniModel);
        return ResponseResult.success();
    }

    @PostMapping("/updateAlumni")
    public ResponseResult<String> updateAlumni(AlumniModel alumniModel) {
        alumniService.updateAlumni(alumniModel);
        return ResponseResult.success();
    }

    @PostMapping("/searchAlumniByStudentId")
    public ResponseResult<AlumniModel> searchAlumniByStudentId(String studentId) {
        AlumniModel alumni = alumniService.searchAlumniByStudentId(studentId);
        return ResponseResult.success(alumni);
    }

    @PostMapping("/searchAlumniByName")
    public ResponseResult<List<AlumniModel>> searchAlumniByName(String name) {
        List<AlumniModel> alumniList = alumniService.searchAlumniByName(name);
        return ResponseResult.success(alumniList);
    }


    @PostMapping("/findAllAlumni")
    public ResponseResult<List<AlumniModel>> findAllAlumni() {
        List<AlumniModel> alumniList = alumniService.findAllAlumni();
        return ResponseResult.success(alumniList);
    }
}
