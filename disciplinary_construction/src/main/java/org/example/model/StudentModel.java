package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "students")
public class StudentModel {
    @Id
    private String id;
    private String studentId; // 学号
    private String name; // 姓名
    private String gender; // 性别
    private Integer age; // 年龄
    private String password; // 密码（加密存储）
    private String maritalStatus; // 婚姻状况
    private String nation; // 民族
    private Date birthDate; // 出生日期
    private String idCard; // 身份证号
    private String educationType; // 学制类型（如全日制、非全日制等）
    private String email; // 邮箱
    private String phone; // 手机号
    private String major; // 专业
    private String grade; // 年级/入学年份
    private String className; // 班级
    private String supervisor; // 导师姓名
    private String researchDirection; // 研究方向
    private String status; // 学籍状态（在读/毕业/休学等）
    private Date createTime; // 注册时间
    private Date updateTime; // 信息更新时间
    private String politicalStatus; // 政治面貌
    private String address; // 现住址
    private String nativePlace; // 籍贯
    private String workStatus; // 工作状态（如年级干部、院校学生组织成员、学工助管等）
}
