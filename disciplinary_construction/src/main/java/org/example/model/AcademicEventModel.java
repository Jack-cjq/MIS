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
@Document(collection = "academic_events")
public class AcademicEventModel {
    @Id
    private String id;
    private String studentId;           // 学生ID
    private String studentName;         // 学生姓名
    private String title; // 活动标题
    private String type; // 学术活动类型（会议/讲座/研讨会等）
    private String organizer; // 主办方
    private String speaker; // 主讲人
    private String attachmentUrl; // 附件地址（如日程PDF）


    // 活动起止时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;        // 活动开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;          // 活动结束时间



    private String auditStatus; // 审核状态
    private String auditComment; // 审核意见
    private String auditorId; // 审核人ID
    private LocalDateTime auditTime; // 审核时间

    // 元信息
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}


