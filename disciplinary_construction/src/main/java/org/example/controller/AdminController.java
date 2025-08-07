package org.example.controller;

import org.example.annotation.CurrentUser;
import org.example.model.AdminModel;
import org.example.service.AdminService;
import org.example.util.JwtUtil;
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
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginForm) {
        String username = loginForm.get("username");
        String password = loginForm.get("password");
        
        Map<String, Object> result = new HashMap<>();
        AdminModel admin = adminService.login(username, password);
        
        if (admin != null) {
            // 生成JWT token
            String token = jwtUtil.generateToken(admin.getId(), "admin", admin.getUsername());
            
            // 返回token和基本用户信息
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("userId", admin.getId());
            data.put("adminId", admin.getAdminId());
            data.put("username", admin.getUsername());
            data.put("realName", admin.getRealName());
            data.put("userType", "admin");
            
            result.put("code", 200);
            result.put("data", data);
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

    // 获取当前管理员信息
    @GetMapping("/current-user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@CurrentUser Map<String, Object> currentUser) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            String userId = (String) currentUser.get("userId");
            String userType = (String) currentUser.get("userType");
            
            if ("admin".equals(userType)) {
                AdminModel admin = adminService.findById(userId);
                if (admin != null) {
                    // 只返回基本信息，不包含敏感信息
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("id", admin.getId());
                    userInfo.put("adminId", admin.getAdminId());
                    userInfo.put("username", admin.getUsername());
                    userInfo.put("realName", admin.getRealName());
                    userInfo.put("userType", "admin");
                    
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