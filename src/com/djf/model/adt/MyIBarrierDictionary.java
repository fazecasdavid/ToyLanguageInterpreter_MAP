package com.djf.model.adt;

public interface MyIBarrierDictionary<K, V> extends MyIDictionary<K, V>{
    int getNextFreeLocation();
}
