package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "daily_activities")
public class DailyActivityModel {
    @Id
    private String id;

    // 学生基本信息
    private String studentId;       // 学生ID
    private String studentName;     // 学生姓名

    // 活动基础信息
    private String activityType;    // 活动类型（lecture=讲座，conference=会议，competition=竞赛，honor=荣誉）
    private String title;           // 活动标题
    private String organizer;       // 主办方/授予单位
    private String level;           // 级别（国家、省级、校级、院级等）
    private String attachmentUrl;   // 证明材料地址（PDF、证书照片等）

    // 活动起止时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;    // 开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;      // 结束时间



    // 审核信息
    private String auditStatus;     // 审核状态（pending/approved/rejected）
    private String auditComment;    // 审核意见
    private String auditorId;       // 审核人ID
    private LocalDateTime auditTime;// 审核时间

    // 元信息
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
