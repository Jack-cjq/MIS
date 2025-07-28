package org.example.service;

import org.example.model.AdminModel;
import java.util.List;
import java.util.Map;

public interface AdminService {
    
    // 管理员登录
    AdminModel login(String username, String password);
    
    // 创建管理员
    AdminModel createAdmin(AdminModel admin);
    
    // 更新管理员信息
    AdminModel updateAdmin(AdminModel admin);
    
    // 删除管理员
    boolean deleteAdmin(String adminId);
    
    // 根据ID查找管理员
    AdminModel findById(String id);
    
    // 根据用户名查找管理员
    AdminModel findByUsername(String username);
    
    // 获取所有管理员
    List<AdminModel> findAllAdmins();
    
    // 根据角色查找管理员
    List<AdminModel> findByRole(String role);
    
    // 更新管理员状态
    boolean updateAdminStatus(String adminId, boolean isActive);
    
    // 更新最后登录时间
    void updateLastLoginTime(String adminId);
    
    // 验证管理员权限
    boolean hasPermission(String adminId, String permission);
    
    // 创建默认管理员
    void createDefaultAdmin();
} 