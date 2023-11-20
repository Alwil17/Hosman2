package com.dopediatrie.hosman.bm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BMCustomException extends RuntimeException{

    private String errorCode;

    public BMCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}