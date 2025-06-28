package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.response.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandel {
    public ExceptionHandel() {
        log.info("ExceptionHandel已创建");
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult<String> handelBusinessException(BusinessException exception) {
        log.error("发生BusinessException：{}", exception.getResponseCode());
        return ResponseResult.error(exception.getResponseCode());
    }
}
