package com.djf.model.value;

import com.djf.model.type.IntType;
import com.djf.model.type.Type;

public class IntValue implements Value {
    private final int value;


    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntValue))
            return false;

        return ((IntValue) obj).value == value;
    }
}
