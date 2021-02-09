package com.djf.model.type;

import com.djf.model.value.RefValue;
import com.djf.model.value.Value;

public class RefType implements Type{
    private final Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }


    @Override
    public Value getDefaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof RefType)
            return inner.equals(((RefType) other).inner);
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" + inner + ")";
    }

    public Type getInner() {
        return inner;
    }
}


