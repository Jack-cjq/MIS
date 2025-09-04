package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "honors")
public class HonorModel {
    @Id
    private String id;
    private String userId; // 学生ID（关联StudentModel）
    private String title; // 荣誉名称
    private String category; // 类别（竞赛/论文/专利/奖学金/社会服务等）
    private String level; // 级别（国家/省级/校级/院级）
    private String awardOrg; // 授予单位
    private Date awardDate; // 获奖日期
    private String description; // 荣誉描述
    private String evidenceUrl; // 佐证材料（证书/文件）
    private List<String> tags; // 标签（如“AI”“Top会议”“一作”）
    private Boolean isPublic; // 是否公开展示在荣誉墙


    private String auditStatus; // 审核状态
    private String auditComment; // 审核意见
    private String auditorId; // 审核人ID
    private LocalDateTime auditTime; // 审核时间

    // 元信息
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
