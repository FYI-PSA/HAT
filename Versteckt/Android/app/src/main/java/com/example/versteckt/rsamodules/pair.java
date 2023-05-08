package com.example.versteckt.rsamodules;

public class pair<T1, T2>
{
    public T1 first;
    public T2 second;

    public pair()
    {}

    public pair(T1 firstArg)
    {
        first = firstArg;
    }

    public pair(T1 firstArg, T2 secondArg)
    {
        first = firstArg;
        second = secondArg;
    }
}