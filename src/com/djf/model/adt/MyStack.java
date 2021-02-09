package com.djf.model.adt;

import com.djf.exceptions.EmptyStackException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.function.Consumer;

public class MyStack<T> implements MyIStack<T>, Iterable<T>{

    private final Deque<T> stack;

    public MyStack() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public T pop() {
        if (stack.isEmpty())
            throw new EmptyStackException();
        return stack.pop();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Deque<T> getContent() {
        return stack;
    }

    @Override
    public String toString() {
        return stack.toString();
    }


    @Override
    public Iterator<T> iterator() {
        return stack.iterator();
    }



}
