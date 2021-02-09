package com.djf.model.adt;

public interface MyIHeapDictionary<K, V> extends MyIDictionary<K, V> {
    int getNextFreeLocation();
}
