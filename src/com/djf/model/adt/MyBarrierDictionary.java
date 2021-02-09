package com.djf.model.adt;

public class MyBarrierDictionary<K, V> extends MyDictionary<K, V> implements MyIBarrierDictionary<K, V>{
    private int nextFreeLocation;

    public MyBarrierDictionary() {
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
