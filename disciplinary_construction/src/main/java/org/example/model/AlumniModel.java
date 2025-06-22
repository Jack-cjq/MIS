package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "alumni")
public class AlumniModel {
    @Id
    private String id;//主键
    private String studentId;//学号
    private LocalDate enrollmentDate;//入学年份
    private LocalDate graduationDate;//毕业年份
    private String workLocation;//就业地区（待定）
    private String workField;//所在行业
    private String workPlace;//所在单位
    private String jobType;//岗位类型
}
