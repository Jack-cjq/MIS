package org.example.controller;

import org.example.model.StudentModel;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msi/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentModel> createStudent(@RequestBody StudentModel student) throws IllegalAccessException {
        StudentModel saveStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(saveStudent);
    }

    @GetMapping("getAllStudent")
    public List<StudentModel> getAllStudent(){
        return studentService.getAllStudent();
    }
}
