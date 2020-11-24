package com.example.td1.utils;

import java.io.Serializable;

public class Triplet<U, V, T>  implements Serializable {
    public final U first;
    public final V second;
    public final T third;

    private Triplet(U first, V second, T third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    // Factory method for creating a Typed immutable instance of Triplet
    public static <U, V, T> Triplet <U, V, T> of(U a, V b, T c)
    {
        return new Triplet <>(a, b, c);
    }
}
