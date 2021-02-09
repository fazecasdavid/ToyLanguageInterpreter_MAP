package com.djf.exceptions;

public class DivisionByZeroException extends RuntimeException{
    public DivisionByZeroException(String msg){
        super(msg);
    }
    public DivisionByZeroException(){
        super("Division by zero!!!");
    }

}
