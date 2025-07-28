package org.example.service.Impl;

import org.example.model.AdminModel;
import org.example.repository.AdminRepository;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminModel login(String username, String password) {
        Optional<AdminModel> adminOpt = adminRepository.findByUsernameAndPassword(username, password);
        if (adminOpt.isPresent()) {
            AdminModel admin = adminOpt.get();
            // 检查管理员是否激活
            if (admin.getIsActive() != null && admin.getIsActive()) {
                // 更新最后登录时间
                admin.setLastLoginTime(new Date());
                adminRepository.save(admin);
                return admin;
            }
        }
        return null;
    }

    @Override
    public AdminModel createAdmin(AdminModel admin) {
        admin.setCreateTime(new Date());
        admin.setUpdateTime(new Date());
        admin.setIsActive(true);
        return adminRepository.save(admin);
    }

    @Override
    public AdminModel updateAdmin(AdminModel admin) {
        admin.setUpdateTime(new Date());
        return adminRepository.save(admin);
    }

    @Override
    public boolean deleteAdmin(String adminId) {
        Optional<AdminModel> adminOpt = adminRepository.findByAdminId(adminId);
        if (adminOpt.isPresent()) {
            adminRepository.delete(adminOpt.get());
            return true;
        }
        return false;
    }

    @Override
    public AdminModel findById(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public AdminModel findByUsername(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<AdminModel> findAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public List<AdminModel> findByRole(String role) {
        return adminRepository.findByRole(role);
    }

    @Override
    public boolean updateAdminStatus(String adminId, boolean isActive) {
        Optional<AdminModel> adminOpt = adminRepository.findByAdminId(adminId);
        if (adminOpt.isPresent()) {
            AdminModel admin = adminOpt.get();
            admin.setIsActive(isActive);
            admin.setUpdateTime(new Date());
            adminRepository.save(admin);
            return true;
        }
        return false;
    }

    @Override
    public void updateLastLoginTime(String adminId) {
        Optional<AdminModel> adminOpt = adminRepository.findByAdminId(adminId);
        if (adminOpt.isPresent()) {
            AdminModel admin = adminOpt.get();
            admin.setLastLoginTime(new Date());
            adminRepository.save(admin);
        }
    }

    @Override
    public boolean hasPermission(String adminId, String permission) {
        Optional<AdminModel> adminOpt = adminRepository.findByAdminId(adminId);
        if (adminOpt.isPresent()) {
            AdminModel admin = adminOpt.get();
            // 超级管理员拥有所有权限
            if ("super_admin".equals(admin.getRole())) {
                return true;
            }
            // 检查普通管理员是否有特定权限
            return admin.getPermissions() != null && admin.getPermissions().contains(permission);
        }
        return false;
    }

    @Override
    public void createDefaultAdmin() {
        // 检查是否已存在默认管理员
        Optional<AdminModel> existingAdmin = adminRepository.findByUsername("admin");
        if (!existingAdmin.isPresent()) {
            AdminModel defaultAdmin = new AdminModel();
            defaultAdmin.setAdminId("ADMIN001");
            defaultAdmin.setUsername("admin");
            defaultAdmin.setPassword("admin123"); // 实际项目中应该加密
            defaultAdmin.setRealName("系统管理员");
            defaultAdmin.setEmail("admin@hbut.edu.cn");
            defaultAdmin.setPhone("13800000000");
            defaultAdmin.setRole("super_admin");
            defaultAdmin.setDepartment("信息化建设小组");
            defaultAdmin.setPosition("系统管理员");
            defaultAdmin.setPermissions(Arrays.asList(
                "student:view", "student:edit", "student:delete",
                "alumni:view", "alumni:edit", "alumni:delete",
                "internship:view", "internship:edit", "internship:delete",
                "competition:view", "competition:edit", "competition:delete",
                "paper:view", "paper:edit", "paper:delete",
                "patent:view", "patent:edit", "patent:delete",
                "project:view", "project:edit", "project:delete",
                "party:view", "party:edit", "party:delete",
                "admin:manage"
            ));
            defaultAdmin.setIsActive(true);
            defaultAdmin.setCreateTime(new Date());
            defaultAdmin.setUpdateTime(new Date());
            defaultAdmin.setCreateBy("system");
            defaultAdmin.setRemark("系统默认管理员");
            
            adminRepository.save(defaultAdmin);
        }
    }
} 