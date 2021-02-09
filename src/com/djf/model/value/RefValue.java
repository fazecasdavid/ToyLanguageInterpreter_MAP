package com.djf.model.value;

import com.djf.model.type.RefType;
import com.djf.model.type.Type;

public class RefValue implements Value {
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof RefValue))
            return false;
        return address == ((RefValue)other).address;
    }
}
