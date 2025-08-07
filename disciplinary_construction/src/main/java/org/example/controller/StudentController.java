package org.example.controller;

import org.example.annotation.CurrentUser;
import org.example.model.StudentModel;
import org.example.service.StudentService;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/msi/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginForm) {
        String account = loginForm.get("account");
        String password = loginForm.get("password");
        
        Map<String, Object> result = new HashMap<>();
        StudentModel student = studentService.login(account, password);
        
        if (student != null) {
            // 生成JWT token
            String token = jwtUtil.generateToken(student.getId(), "student", student.getName());
            
            // 返回token和基本用户信息
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("userId", student.getId());
            data.put("studentId", student.getStudentId());
            data.put("name", student.getName());
            data.put("userType", "student");
            
            result.put("code", 200);
            result.put("data", data);
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

    // 获取学生统计信息
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalStudents", studentService.findAllStudents().size());
            statistics.put("activeStudents", studentService.findAllStudents().stream()
                .filter(s -> "在读".equals(s.getStatus())).count());
            statistics.put("graduatedStudents", studentService.findAllStudents().stream()
                .filter(s -> "毕业".equals(s.getStatus())).count());
            statistics.put("maleStudents", studentService.findAllStudents().stream()
                .filter(s -> "男".equals(s.getGender())).count());
            statistics.put("femaleStudents", studentService.findAllStudents().stream()
                .filter(s -> "女".equals(s.getGender())).count());
            
            result.put("code", 200);
            result.put("data", statistics);
            result.put("msg", "获取统计信息成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取统计信息失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 获取最新动态
    @GetMapping("/news")
    public ResponseEntity<Map<String, Object>> getLatestNews() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 模拟最新动态数据
            Map<String, Object> news = new HashMap<>();
            news.put("title", "系统公告");
            news.put("content", "欢迎使用学生管理系统！");
            news.put("time", new java.util.Date());
            
            result.put("code", 200);
            result.put("data", news);
            result.put("msg", "获取最新动态成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取最新动态失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 获取图表数据
    @GetMapping("/chart")
    public ResponseEntity<Map<String, Object>> getChartData() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 模拟图表数据
            Map<String, Object> chartData = new HashMap<>();
            chartData.put("labels", new String[]{"计算机科学与技术", "软件工程", "信息安全"});
            chartData.put("data", new int[]{120, 80, 60});
            
            result.put("code", 200);
            result.put("data", chartData);
            result.put("msg", "获取图表数据成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取图表数据失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 获取学生个人统计数据
    @GetMapping("/personal-stats/{studentId}")
    public ResponseEntity<Map<String, Object>> getPersonalStats(@PathVariable String studentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 这里应该根据studentId获取该学生的个人统计数据
            // 暂时返回模拟数据
            Map<String, Object> personalStats = new HashMap<>();
            personalStats.put("internshipCount", 2);
            personalStats.put("competitionCount", 1);
            personalStats.put("paperCount", 0);
            personalStats.put("projectCount", 1);
            
            result.put("code", 200);
            result.put("data", personalStats);
            result.put("msg", "获取个人统计数据成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取个人统计数据失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 获取当前用户个人信息
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getCurrentUserProfile(@CurrentUser Map<String, Object> currentUser) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 从JWT Token中获取当前用户ID
            String userId = (String) currentUser.get("userId");
            StudentModel student = studentService.findById(userId);
            if (student != null) {
                result.put("code", 200);
                result.put("data", student);
                result.put("msg", "获取个人信息成功");
            } else {
                result.put("code", 404);
                result.put("data", null);
                result.put("msg", "用户信息不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取个人信息失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 获取当前用户基本信息（用于页面刷新后恢复用户信息）
    @GetMapping("/current-user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> result = new HashMap<>();
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            result.put("code", 401);
            result.put("data", null);
            result.put("msg", "缺少有效的Authorization头");
            return ResponseEntity.ok(result);
        }
        
        String token = authHeader.substring(7);
        
        if (!jwtUtil.validateToken(token)) {
            result.put("code", 401);
            result.put("data", null);
            result.put("msg", "Token无效或已过期");
            return ResponseEntity.ok(result);
        }
        
        try {
            String userId = jwtUtil.getUserIdFromToken(token);
            String userType = jwtUtil.getUserTypeFromToken(token);
            
            if ("student".equals(userType)) {
                StudentModel student = studentService.findById(userId);
                if (student != null) {
                    // 只返回基本信息，不包含敏感信息
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("id", student.getId());
                    userInfo.put("studentId", student.getStudentId());
                    userInfo.put("name", student.getName());
                    userInfo.put("userType", "student");
                    
                    result.put("code", 200);
                    result.put("data", userInfo);
                    result.put("msg", "获取当前用户信息成功");
                } else {
                    result.put("code", 404);
                    result.put("data", null);
                    result.put("msg", "用户信息不存在");
                }
            } else {
                result.put("code", 403);
                result.put("data", null);
                result.put("msg", "用户类型不匹配");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取当前用户信息失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // Token刷新接口
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> result = new HashMap<>();
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            result.put("code", 401);
            result.put("data", null);
            result.put("msg", "缺少Authorization头");
            return ResponseEntity.ok(result);
        }
        
        String token = authHeader.substring(7);
        
        try {
            // 即使token即将过期，也允许刷新
            if (jwtUtil.validateToken(token)) {
                // 检查token是否即将过期
                if (jwtUtil.isTokenNearExpiration(token)) {
                    // 生成新的token
                    String newToken = jwtUtil.refreshToken(token);
                    
                    Map<String, Object> data = new HashMap<>();
                    data.put("token", newToken);
                    data.put("userId", jwtUtil.getUserIdFromToken(newToken));
                    data.put("userType", jwtUtil.getUserTypeFromToken(newToken));
                    data.put("username", jwtUtil.getUsernameFromToken(newToken));
                    
                    result.put("code", 200);
                    result.put("data", data);
                    result.put("msg", "Token刷新成功");
                } else {
                    result.put("code", 200);
                    result.put("data", null);
                    result.put("msg", "Token尚未过期，无需刷新");
                }
            } else {
                result.put("code", 401);
                result.put("data", null);
                result.put("msg", "Token无效或已过期");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "Token刷新失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(result);
    }

    // 更新个人信息
    @PutMapping("/profile/update")
    public ResponseEntity<Map<String, Object>> updateUserProfile(@RequestBody StudentModel student, @CurrentUser Map<String, Object> currentUser) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 确保只能更新自己的信息
            String userId = (String) currentUser.get("userId");
            student.setId(userId);
            
            StudentModel updatedStudent = studentService.updateStudent(student);
            result.put("code", 200);
            result.put("data", updatedStudent);
            result.put("msg", "更新个人信息成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "更新个人信息失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    // 获取学习记录
    @GetMapping("/study-records")
    public ResponseEntity<Map<String, Object>> getStudyRecords(@CurrentUser Map<String, Object> currentUser) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 从JWT Token中获取当前用户ID
            String userId = (String) currentUser.get("userId");
            
            // 暂时返回模拟的学习记录数据
            List<Map<String, Object>> studyRecords = new ArrayList<>();
            
            Map<String, Object> record1 = new HashMap<>();
            record1.put("semester", "2023-2024-1");
            record1.put("course", "数据结构");
            record1.put("score", 85);
            record1.put("credit", 4);
            record1.put("status", "已通过");
            studyRecords.add(record1);
            
            Map<String, Object> record2 = new HashMap<>();
            record2.put("semester", "2023-2024-1");
            record2.put("course", "计算机网络");
            record2.put("score", 78);
            record2.put("credit", 3);
            record2.put("status", "已通过");
            studyRecords.add(record2);
            
            Map<String, Object> record3 = new HashMap<>();
            record3.put("semester", "2023-2024-1");
            record3.put("course", "操作系统");
            record3.put("score", 92);
            record3.put("credit", 4);
            record3.put("status", "已通过");
            studyRecords.add(record3);
            
            result.put("code", 200);
            result.put("data", studyRecords);
            result.put("msg", "获取学习记录成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "获取学习记录失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
