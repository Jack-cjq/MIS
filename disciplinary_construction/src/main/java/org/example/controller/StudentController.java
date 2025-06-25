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
        System.out.print("输入密码: " + password);
//        System.out.print("数据库密码: " + student.getPassword());
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
}
