package org.example.model;


import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "students")
public class StudentModel {

    @Id //主键注释
    private String id;

    private String name;

    private Integer userStatus;

    private Integer age;
}
