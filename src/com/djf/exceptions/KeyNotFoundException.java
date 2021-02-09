package com.djf.exceptions;

public class KeyNotFoundException extends RuntimeException{
    public KeyNotFoundException(String msg){
        super(msg);
    }
    public KeyNotFoundException(){
        super("Key not found in dictionary!!!");
    }
}
