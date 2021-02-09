package com.djf.view.javafxUI;

public class Triplet<X, Y, Z> {
    private X first;
    private Y second;
    private Z third;

    public X getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }

    public Z getThird() {
        return third;
    }

    public void setFirst(X first) {
        this.first = first;
    }

    public void setSecond(Y second) {
        this.second = second;
    }

    public void setThird(Z third) {
        this.third = third;
    }

    public Triplet(X first, Y second, Z third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
