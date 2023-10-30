package com.dopediatrie.hosman.stock.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StockCustomException extends RuntimeException{

    private String errorCode;

    public StockCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}