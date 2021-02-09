package com.djf.model.adt;

import com.djf.exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {

    private Map<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    // shallow copy
    public MyDictionary(Map<K, V> dictionary) {
        this.dictionary = new HashMap<>(dictionary);
    }
    // shallow copy
    public MyDictionary(MyIDictionary<K,V> dictionary) {
        this.dictionary = new HashMap<>(dictionary.getContent());
    }

    @Override
    public synchronized void clear() {
        dictionary.clear();
    }

    @Override
    public synchronized boolean containsKey(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public synchronized V get(K key) {
        if (!containsKey(key))
            throw new KeyNotFoundException();
        return dictionary.get(key);
    }

    @Override
    public synchronized void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public synchronized String toString() {
        return dictionary.toString();
    }

    @Override
    public synchronized V remove(K key) {
        return dictionary.remove(key);
    }

    @Override
    public synchronized void setContent(Map<K, V> content) {
        dictionary = content;
    }

    @Override
    public synchronized Map<K, V> getContent() {
        return dictionary;
    }


    @Override
    public synchronized Iterator<Map.Entry<K, V>> iterator() {
        return dictionary.entrySet().iterator();
    }
}
