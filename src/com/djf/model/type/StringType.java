package com.djf.model.type;

import com.djf.model.value.StringValue;
import com.djf.model.value.Value;

public class StringType implements Type{

    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }
}
