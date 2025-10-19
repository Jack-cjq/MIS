package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "competition_dict")
public class CompetitionDictModel {
    @Id
    private String id;

    private Integer serialNumber;       // 序号
    private String competitionName;     // 赛事名称
    private String competitionLevel;    // 赛事等级（重大赛事/重点赛事/一般赛事）
    private String competitionStatus = "启用";   // 默认"启用"
}