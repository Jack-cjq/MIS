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
@Document(collection = "project")
public class ProjectModel {
    @Id
    private String id;

    private String studentId;           // 学生ID
    private String studentName;         // 学生姓名
    private String projectName;         // 项目名称
    private String projectLevel;        // 项目级别：校（市）级、省（部）级、国家级
    private String ranking;            // 排名（数字）
    private String teamMembers;         // 团队成员
    private String instructorName;      // 指导老师
    private String attachments;         // 附件路径

    // 项目起止时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;        // 项目开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;          // 项目结束时间

    // 审核相关
    private String auditStatus;         // 审核状态
    private String auditComment;        // 审核意见
    private String auditorId;           // 审核人ID
    private LocalDateTime auditTime;    // 审核时间

    // 时间戳
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}