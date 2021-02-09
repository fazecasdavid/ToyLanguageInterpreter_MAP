package com.djf.model.value;

import com.djf.model.type.BoolType;
import com.djf.model.type.Type;

public class BoolValue implements Value {

    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BoolValue))
            return false;

        return ((BoolValue) obj).value == value;
    }
}
