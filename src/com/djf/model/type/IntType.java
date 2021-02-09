package com.djf.model.type;

import com.djf.model.value.IntValue;
import com.djf.model.value.Value;

public class IntType implements Type{

    @Override
    public boolean equals(Object another){
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }


    @Override
    public Value getDefaultValue() {
        return new IntValue(0);
    }
}
