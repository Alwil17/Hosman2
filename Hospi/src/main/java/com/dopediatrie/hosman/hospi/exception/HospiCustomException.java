package com.dopediatrie.hosman.hospi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HospiCustomException extends RuntimeException{

    private String errorCode;

    public HospiCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}