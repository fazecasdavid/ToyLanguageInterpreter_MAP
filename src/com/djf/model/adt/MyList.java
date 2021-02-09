package com.djf.model.adt;

import com.djf.exceptions.ListOutOfRangeException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<E> implements MyIList<E> {

    private final List<E> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public synchronized void addToEnd(E element) {
        list.add(element);
    }

    @Override
    public synchronized void removeFromIndex(int index) {
        if (index >= list.size() || index < 0)
            throw new ListOutOfRangeException();
        list.remove(index);
    }

    @Override
    public synchronized E getFromIndex(int index) {
        if (index >= list.size() || index < 0)
            throw new ListOutOfRangeException();

        return list.get(index);
    }

    @Override
    public synchronized void clear() {
        list.clear();
    }

    @Override
    public synchronized int getSize() {
        return list.size();
    }

    @Override
    public List<E> getContent() {
        return list;
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public synchronized String toString() {
        return list.toString();
    }


}
