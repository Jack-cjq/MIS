package org.example.controller;

import org.example.model.StudentModel;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/msi/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginForm) {
        String account = loginForm.get("account");
        String password = loginForm.get("password");
        
        Map<String, Object> result = new HashMap<>();
        StudentModel student = studentService.login(account, password);
        
        if (student != null) {
            result.put("code", 200);
            result.put("data", student);
            result.put("msg", "登录成功");
        } else {
            result.put("code", 401);
            result.put("data", null);
            result.put("msg", "账号或密码错误");
        }
        return ResponseEntity.ok(result);
    }

    // 获取所有学生（管理员权限）
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllStudents() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<StudentModel> students = studentService.findAllStudents();
            result.put("code", 200);
            result.put("data", students);
            result.put("msg", "获取学生列表成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取学生列表失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 根据ID获取学生信息
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            StudentModel student = studentService.findById(id);
            if (student != null) {
                result.put("code", 200);
                result.put("data", student);
                result.put("msg", "获取学生信息成功");
            } else {
                result.put("code", 404);
                result.put("data", null);
                result.put("msg", "学生不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取学生信息失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 创建学生（管理员权限）
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createStudent(@RequestBody StudentModel student) {
        Map<String, Object> result = new HashMap<>();
        try {
            StudentModel createdStudent = studentService.createStudent(student);
            result.put("code", 200);
            result.put("data", createdStudent);
            result.put("msg", "学生创建成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "创建失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 更新学生信息
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateStudent(@RequestBody StudentModel student) {
        Map<String, Object> result = new HashMap<>();
        try {
            StudentModel updatedStudent = studentService.updateStudent(student);
            result.put("code", 200);
            result.put("data", updatedStudent);
            result.put("msg", "学生信息更新成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "更新失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 删除学生（管理员权限）
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = studentService.deleteStudent(id);
            if (success) {
                result.put("code", 200);
                result.put("msg", "学生删除成功");
            } else {
                result.put("code", 404);
                result.put("msg", "学生不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "删除失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
