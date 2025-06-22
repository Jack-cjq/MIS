package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), data);
    }

    public static <T> ResponseResult<T> error() {
        return new ResponseResult<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), null);
    }

    public static <T> ResponseResult<T> error(T data) {
        return new ResponseResult<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage(), data);
    }

    public static <T> ResponseResult<T> error(ResponseCode responseCode) {
        return new ResponseResult<>(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> ResponseResult<T> error(ResponseCode responseCode, T data) {
        return new ResponseResult<>(responseCode.getCode(), responseCode.getMessage(), data);
    }
}


