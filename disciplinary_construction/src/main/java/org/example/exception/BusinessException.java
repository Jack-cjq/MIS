package org.example.exception;

import lombok.Getter;
import lombok.Setter;
import org.example.response.ResponseCode;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private final ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.toString());
        this.responseCode = responseCode;
    }
}
