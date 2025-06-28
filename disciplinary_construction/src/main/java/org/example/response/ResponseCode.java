package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum ResponseCode {
    OK(200, "成功"),
    ERROR(10000, "错误"),
    STUDENT_NOT_FOUND(10001, "学生不存在"),

    ;
    private final Integer code;
    private final String message;

}
