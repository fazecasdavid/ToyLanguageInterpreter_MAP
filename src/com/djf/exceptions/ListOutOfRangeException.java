package com.djf.exceptions;

public class ListOutOfRangeException extends RuntimeException{
    public ListOutOfRangeException(String msg){
        super(msg);
    }
    public ListOutOfRangeException(){
        super("List out or range!!!");
    }
}
