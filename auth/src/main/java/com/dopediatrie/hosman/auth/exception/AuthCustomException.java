package com.dopediatrie.hosman.auth.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthCustomException extends RuntimeException{

    private String errorCode;

    public AuthCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}