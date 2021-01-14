package com.example.td1.utils;

import java.io.Serializable;

public class Paired<U, V> implements Serializable {
    public final U first;
    public final V second;

    private Paired(U first, V second) {
        this.first = first;
        this.second = second;
    }

    // Factory method for creating a Typed immutable instance of Triplet
    public static <U, V> Paired<U, V> of(U a, V b) {
        return new Paired<>(a, b);
    }
}
