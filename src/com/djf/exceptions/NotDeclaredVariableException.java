package com.djf.exceptions;

public class NotDeclaredVariableException extends RuntimeException{
    public NotDeclaredVariableException(String msg){
        super(msg);
    }
    public NotDeclaredVariableException(){
        super("Not declared variable!!!");
    }
}
