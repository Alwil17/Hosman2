package com.dopediatrie.hosman.secretariat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecretariatCustomException extends RuntimeException{

    private String errorCode;

    public SecretariatCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}