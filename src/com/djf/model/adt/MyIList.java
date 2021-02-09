package com.djf.model.adt;

import java.util.List;

public interface MyIList<E> extends Iterable<E>{
    void addToEnd(E element);
    void removeFromIndex(int index);
    E getFromIndex(int index);
    void clear();
    int getSize();
    List<E> getContent();
}
