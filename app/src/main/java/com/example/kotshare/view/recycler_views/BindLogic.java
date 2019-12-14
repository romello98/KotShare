package com.example.kotshare.view.recycler_views;

public class BindLogic<T>
{
    private int layoutId;
    private BindFunction bindFunction;

    public BindLogic(int layoutId, BindFunction<T> bindFunction) {
        this.layoutId = layoutId;
        this.bindFunction = bindFunction;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public BindFunction getBindFunction() {
        return bindFunction;
    }

    public void setBindFunction(BindFunction bindFunction) {
        this.bindFunction = bindFunction;
    }

    public interface BindFunction<T>
    {
        void bind(T item, GenericRecyclerViewAdapter.ViewHolder viewHolder);
    }
}
