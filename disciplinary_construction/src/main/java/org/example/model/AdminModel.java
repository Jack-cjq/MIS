package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "admins")
public class AdminModel {
    @Id
    private String id;
    private String adminId; // 管理员ID
    private String username; // 用户名
    private String password; // 密码（加密存储）
    private String realName; // 真实姓名
    private String email; // 邮箱
    private String phone; // 手机号
    private String role; // 角色（super_admin-超级管理员，admin-普通管理员）
    private String department; // 所属部门
    private String position; // 职位
    private List<String> permissions; // 权限列表
    private Boolean isActive; // 是否激活
    private Date lastLoginTime; // 最后登录时间
    private String lastLoginIp; // 最后登录IP
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private String createBy; // 创建人
    private String updateBy; // 更新人
    private String remark; // 备注
} 