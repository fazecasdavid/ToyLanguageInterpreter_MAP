package com.djf.model.adt;

import java.util.Iterator;
import java.util.Map;

public class MyHeapDictionary<K,V> extends MyDictionary<K,V> implements MyIHeapDictionary<K,V> {
    private int nextFreeLocation;

    public MyHeapDictionary() {
        super();
        nextFreeLocation = 1;
    }

    @Override
    public synchronized int getNextFreeLocation() {
        return nextFreeLocation;
    }

    @Override
    public synchronized void put(K key, V value) {
        super.put(key, value);
        nextFreeLocation++;
    }
}
