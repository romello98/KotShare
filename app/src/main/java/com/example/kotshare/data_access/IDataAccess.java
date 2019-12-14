package com.example.kotshare.data_access;

import java.util.ArrayList;

public interface IDataAccess<T>
{
    T add(T object);
    ArrayList<T> getAll();
    T find(int id);
    ArrayList<T> where(Predicate<T> predicate);
    T update(T object);
    void delete(T object);

    public interface Predicate<R>
    {
        boolean verify(R object);
    }
}
