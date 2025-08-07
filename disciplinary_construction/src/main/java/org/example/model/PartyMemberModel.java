package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "party_members")
public class PartyMemberModel {
    @Id
    private String id;
    private String studentId; // 学号
    private String name; // 姓名
    private String gender; // 性别
    private String nation; // 民族
    private String birth; // 出生日期
    private String nativePlace; // 籍贯
    private String className; // 班级
    private String branch; // 党支部
    private String joinDate; // 入党日期
    private String isRegular; // 是否正式党员
    private String applicationNo; // 入党申请书编号
    private String position; // 党内职务
    private String rewardsPunishments; // 党内外奖惩情况
    private String avatar; // 头像
    private String remark; // 备注
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private String auditStatus; // 审核状态
    private String auditComment; // 审核意见
    private String auditorId; // 审核人ID
    private LocalDateTime auditTime; // 审核时间
} 