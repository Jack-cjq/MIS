package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "volunteer_services")
public class VolunteerServiceModel {
    @Id
    private String id;
    private String serviceName; // 志愿服务名称
    private String serviceDate; // 服务日期
    private String serviceType; // 服务类型
    private String serviceHours; // 服务时长
    private String serviceLocation; // 服务地点
    private String serviceDescription; // 服务描述
    private String studentId; // 学生ID
    private String studentName; // 学生姓名
    private String organization; // 服务组织
    private String contactPerson; // 联系人
    private String contactPhone; // 联系电话
    private String status; // 服务状态
    private String remark; // 备注
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private String auditStatus; // 审核状态
    private String auditComment; // 审核意见
    private String auditorId; // 审核人ID
    private LocalDateTime auditTime; // 审核时间
} 