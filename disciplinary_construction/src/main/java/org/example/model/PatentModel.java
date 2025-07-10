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
@Document(collection = "patent")
public class PatentModel {
    @Id
    private String id;

    private String studentId;           // 学生ID
    private String studentName;         // 学生姓名
    private String patentTitle;         // 专利名称
    private String patentType;          // 专利类型
    private String patentNumber;        // 专利号
    private String inventorRank;       // 发明人排名（数字）
    private String coInventors;         // 合作发明人
    private String attachments;         // 附件路径

    // 专利相关时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate authorizationDate; // 授权时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate applicationDate;   // 受理时间
    private String patentStatus;         // 专利状态：已授权/已受理/申请中

    // 审核相关
    private String auditStatus;         // 审核状态
    private String auditComment;        // 审核意见
    private String auditorId;           // 审核人ID
    private LocalDateTime auditTime;    // 审核时间

    // 时间戳
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}