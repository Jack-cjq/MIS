package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "ideological_tweets")
public class IdeologicalTweetModel {
    @Id
    private String id;
    private String title; // 思想动态标题
    private String content; // 思想动态内容
    private String tweetDate; // 动态日期
    private String studentId; // 学生ID
    private String studentName; // 学生姓名
    private String tweetType; // 动态类型
    private String status; // 动态状态
    private String remark; // 备注
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private String auditStatus; // 审核状态
    private String auditComment; // 审核意见
    private String auditorId; // 审核人ID
    private LocalDateTime auditTime; // 审核时间
} 