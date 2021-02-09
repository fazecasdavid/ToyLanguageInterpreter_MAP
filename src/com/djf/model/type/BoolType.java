package com.djf.model.type;

import com.djf.model.value.BoolValue;
import com.djf.model.value.Value;

public class BoolType implements Type{

    @Override
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "boolean";
    }

    @Override
    public Value getDefaultValue() {
        return new BoolValue(false);
    }
}
