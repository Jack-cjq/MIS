package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "internship_employment")
public class InternshipEmploymentModel {
    @Id
    private String id;
    private String userId; // 用户ID
    private String type; // 类型：实习/就业
    private String company; // 单位名称
    private String position; // 职位
    private Date startDate; // 开始时间
    private Date endDate; // 结束时间
    private String status; // 状态（如：在岗、已结束等）
    private String remark; // 备注
} 