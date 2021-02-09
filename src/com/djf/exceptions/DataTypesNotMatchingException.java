package com.djf.exceptions;

public class DataTypesNotMatchingException extends RuntimeException {
    public DataTypesNotMatchingException(String msg){
        super(msg);
    }
    public DataTypesNotMatchingException() {
        super("Data types do not match!!!");
    }
}
