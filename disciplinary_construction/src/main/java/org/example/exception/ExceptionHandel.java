package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.response.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandel {
    public ExceptionHandel() {
        log.info("ExceptionHandel已创建");
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseResult<String> handelBusinessException(BusinessException exception) {
        log.error("发生BusinessException：{}", exception.getResponseCode());
        return ResponseResult.error(exception.getResponseCode());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleRuntimeException(RuntimeException exception) {
        log.error("发生RuntimeException：{}", exception.getMessage());
        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("data", null);
        result.put("msg", exception.getMessage());
        return result;
    }
}
