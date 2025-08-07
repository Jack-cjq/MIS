package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 实习就业信息模型
 * 用于存储学生的实习和就业信息
 * 适用于计算机学院的学生管理系统
 */
@Data
@Document(collection = "internship_employment")
public class InternshipEmploymentModel {
    @Id
    private String id;
    
    // 基础信息
    private String userId; // 用户ID
    private String studentId; // 学号
    private String studentName; // 学生姓名
    private String studentClass; // 班级
    private String major; // 专业
    private String grade; // 年级
    
    // 实习就业类型
    private String type; // 类型：实习/就业
    private String category; // 分类：暑期实习/毕业实习/正式就业/创业
    
    // 单位信息
    private String company; // 单位名称
    private String companyType; // 单位类型：国企/外企/民企/事业单位/创业公司/其他
    private String industry; // 行业：IT/金融/教育/制造业/互联网/人工智能/大数据/云计算/其他
    private String companySize; // 公司规模：初创(1-50人)/中小型(50-500人)/大型(500人以上)
    private String companyLocation; // 公司所在地
    
    // 职位信息
    private String position; // 职位
    private String department; // 部门
    private String workLocation; // 工作地点
    private String workType; // 工作类型：全职/兼职/实习/远程
    
    // 薪资信息
    private String salary; // 薪资
    private String salaryType; // 薪资类型：月薪/年薪/时薪/项目制
    private String salaryRange; // 薪资范围：3k以下/3k-5k/5k-8k/8k-12k/12k-20k/20k以上
    
    // 技术相关（计算机学院特色）
    private String technologyStack; // 技术栈：Java/Python/C++/JavaScript/Go/Rust/其他
    private String programmingLanguages; // 编程语言
    private String frameworks; // 框架：Spring/React/Vue/Angular/Django/Flask/其他
    private String databases; // 数据库：MySQL/PostgreSQL/MongoDB/Redis/其他
    private String cloudPlatforms; // 云平台：AWS/Azure/阿里云/腾讯云/其他
    private String developmentTools; // 开发工具：Git/Docker/Kubernetes/Jenkins/其他
    private String projectType; // 项目类型：Web开发/移动开发/数据分析/人工智能/系统架构/其他
    
    // 时间信息
    private Date startDate; // 开始时间
    private Date endDate; // 结束时间
    private String duration; // 持续时间
    
    // 状态信息
    private String status; // 状态：在岗/已结束/待入职/已离职/转正
    private String contractType; // 合同类型：正式/实习/临时/试用期
    
    // 审批信息
    private String approvalStatus; // 审批状态：待审批/已通过/已拒绝
    private String approvalRemark; // 审批备注
    private Date approvalTime; // 审批时间
    private String approvalBy; // 审批人
    
    // 详细信息
    private String benefits; // 福利待遇
    private String jobDescription; // 工作描述
    private String skills; // 所需技能
    private String responsibilities; // 工作职责
    private String achievements; // 工作成果
    private String learningOutcomes; // 学习收获
    
    // 联系信息
    private String contactPerson; // 联系人
    private String contactPhone; // 联系电话
    private String contactEmail; // 联系邮箱
    private String supervisorName; // 导师姓名
    private String supervisorContact; // 导师联系方式
    
    // 评价信息
    private String studentEvaluation; // 学生评价
    private String companyEvaluation; // 企业评价
    private String supervisorEvaluation; // 导师评价
    private Integer satisfactionScore; // 满意度评分(1-10)
    
    // 其他信息
    private String remark; // 备注
    private String attachments; // 附件（简历、证书等）
    private String tags; // 标签
    
    // 系统信息
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private String createBy; // 创建人
    private String updateBy; // 更新人
    private Boolean isDeleted; // 是否删除
} 