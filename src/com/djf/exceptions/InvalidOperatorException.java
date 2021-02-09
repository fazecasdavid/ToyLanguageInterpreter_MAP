package com.djf.exceptions;



public class InvalidOperatorException extends RuntimeException{
    public InvalidOperatorException(String msg){
        super(msg);
    }
    public InvalidOperatorException(){
        super("Use of invalid Operator");
    }

}
