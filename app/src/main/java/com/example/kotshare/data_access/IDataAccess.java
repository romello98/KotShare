package com.example.kotshare.data_access;

import com.example.kotshare.model.PagedResult;

import java.util.ArrayList;

import retrofit2.Call;

public interface IDataAccess<T>
{
    T add(T object);
    Call<PagedResult<T>> getAll(Integer pageIndex, Integer pageSize);
    Call<T> find(int id);
    Call<PagedResult<T>> where(Predicate<T> predicate);
    T update(T object);
    void delete(T object);

    public interface Predicate<R>
    {
        boolean verify(R object);
    }
}
