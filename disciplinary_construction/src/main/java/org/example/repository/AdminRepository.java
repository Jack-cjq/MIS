package org.example.repository;

import org.example.model.AdminModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<AdminModel, String> {
    
    // 根据用户名查找管理员
    Optional<AdminModel> findByUsername(String username);
    
    // 根据管理员ID查找
    Optional<AdminModel> findByAdminId(String adminId);
    
    // 根据邮箱查找
    Optional<AdminModel> findByEmail(String email);
    
    // 根据用户名和密码查找（用于登录验证）
    Optional<AdminModel> findByUsernameAndPassword(String username, String password);
    
    // 查找所有激活的管理员
    List<AdminModel> findByIsActiveTrue();
    
    // 根据角色查找管理员
    List<AdminModel> findByRole(String role);
    
    // 根据部门查找管理员
    List<AdminModel> findByDepartment(String department);
} 