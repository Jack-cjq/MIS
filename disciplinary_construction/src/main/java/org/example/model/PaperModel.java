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
@Document(collection = "paper")
public class PaperModel {
    @Id
    private String id;

    private String studentId;           // 学生ID
    private String studentName;         // 学生姓名
    private String paperTitle;          // 论文题目
    private String journalName;         // 期刊名称
    private String journalLevel;        // 期刊级别
    private String authorRank;         // 作者排名（数字）
    private String coAuthors;           // 合作作者
    private String doi;                 // DOI号
    private String attachments;         // 附件路径

    // 论文发表相关时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate publicationDate;  // 见刊时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate acceptanceDate;   // 接收时间
    private String publicationStatus;   // 发表状态：已见刊/已接收/审稿中

    // 审核相关
    private String auditStatus;         // 审核状态
    private String auditComment;        // 审核意见
    private String auditorId;           // 审核人ID
    private LocalDateTime auditTime;    // 审核时间

    // 时间戳
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}