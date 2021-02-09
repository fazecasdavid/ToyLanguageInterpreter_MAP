package com.djf.model.adt;

import java.util.Deque;
import java.util.Iterator;

public interface MyIStack<E> extends Iterable<E> {
    E pop();
    void push(E element);
    boolean isEmpty();
    Deque<E> getContent();
}
