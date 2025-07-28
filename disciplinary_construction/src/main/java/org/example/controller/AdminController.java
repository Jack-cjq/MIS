package org.example.controller;

import org.example.model.AdminModel;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/msi/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginForm) {
        String username = loginForm.get("username");
        String password = loginForm.get("password");
        
        Map<String, Object> result = new HashMap<>();
        AdminModel admin = adminService.login(username, password);
        
        if (admin != null) {
            result.put("code", 200);
            result.put("data", admin);
            result.put("msg", "管理员登录成功");
        } else {
            result.put("code", 401);
            result.put("data", null);
            result.put("msg", "用户名或密码错误");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createAdmin(@RequestBody AdminModel admin) {
        Map<String, Object> result = new HashMap<>();
        try {
            AdminModel createdAdmin = adminService.createAdmin(admin);
            result.put("code", 200);
            result.put("data", createdAdmin);
            result.put("msg", "管理员创建成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "创建失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateAdmin(@RequestBody AdminModel admin) {
        Map<String, Object> result = new HashMap<>();
        try {
            AdminModel updatedAdmin = adminService.updateAdmin(admin);
            result.put("code", 200);
            result.put("data", updatedAdmin);
            result.put("msg", "管理员信息更新成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("data", null);
            result.put("msg", "更新失败: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<Map<String, Object>> deleteAdmin(@PathVariable String adminId) {
        Map<String, Object> result = new HashMap<>();
        boolean success = adminService.deleteAdmin(adminId);
        if (success) {
            result.put("code", 200);
            result.put("msg", "管理员删除成功");
        } else {
            result.put("code", 404);
            result.put("msg", "管理员不存在");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllAdmins() {
        Map<String, Object> result = new HashMap<>();
        List<AdminModel> admins = adminService.findAllAdmins();
        result.put("code", 200);
        result.put("data", admins);
        result.put("msg", "获取管理员列表成功");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAdminById(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        AdminModel admin = adminService.findById(id);
        if (admin != null) {
            result.put("code", 200);
            result.put("data", admin);
            result.put("msg", "获取管理员信息成功");
        } else {
            result.put("code", 404);
            result.put("data", null);
            result.put("msg", "管理员不存在");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<Map<String, Object>> getAdminsByRole(@PathVariable String role) {
        Map<String, Object> result = new HashMap<>();
        List<AdminModel> admins = adminService.findByRole(role);
        result.put("code", 200);
        result.put("data", admins);
        result.put("msg", "获取角色管理员列表成功");
        return ResponseEntity.ok(result);
    }

    @PutMapping("/status/{adminId}")
    public ResponseEntity<Map<String, Object>> updateAdminStatus(
            @PathVariable String adminId, 
            @RequestBody Map<String, Boolean> statusMap) {
        Map<String, Object> result = new HashMap<>();
        Boolean isActive = statusMap.get("isActive");
        boolean success = adminService.updateAdminStatus(adminId, isActive);
        if (success) {
            result.put("code", 200);
            result.put("msg", "管理员状态更新成功");
        } else {
            result.put("code", 404);
            result.put("msg", "管理员不存在");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/permission/{adminId}/{permission}")
    public ResponseEntity<Map<String, Object>> checkPermission(
            @PathVariable String adminId, 
            @PathVariable String permission) {
        Map<String, Object> result = new HashMap<>();
        boolean hasPermission = adminService.hasPermission(adminId, permission);
        result.put("code", 200);
        result.put("data", hasPermission);
        result.put("msg", hasPermission ? "有权限" : "无权限");
        return ResponseEntity.ok(result);
    }
} 