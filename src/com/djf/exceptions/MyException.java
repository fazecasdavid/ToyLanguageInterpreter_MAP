package com.djf.exceptions;

public class MyException extends RuntimeException{
    public MyException(String msg){
        super(msg);
    }
    public MyException(){
        super("An exception occurred!!!");
    }
}
