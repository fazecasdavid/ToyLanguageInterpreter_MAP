package com.djf.model.adt;

import java.util.Map;

public interface MyIDictionary<K, V> extends Iterable<Map.Entry<K,V>>{
    void  clear();
    boolean containsKey(K key);
    V get(K key);
    void put(K key, V value);
    V remove(K key);
    void setContent(Map<K, V> content);
    Map<K, V> getContent();
}
