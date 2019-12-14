package com.example.kotshare.view.recycler_views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class GenericRecyclerViewAdapter<T> extends RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder> {

    private List<T> items;
    private ViewHolderDispatcher<T> viewHolderDispatcher;
    private HashMap<ViewHolderType, BindLogic<T>> viewHolderTypes;
    private final ViewHolderType[] typeValues = ViewHolderType.values();

    public GenericRecyclerViewAdapter(List<T> items,
                                      ViewHolderDispatcher<T> viewHolderDispatcher,
                                      HashMap<ViewHolderType, BindLogic<T>> viewHolders)
    {
        this.items = items;
        this.viewHolderDispatcher = viewHolderDispatcher;
        this.viewHolderTypes = viewHolders;
    }

    @NonNull
    @Override
    public GenericRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderType viewHolderType = typeValues[viewType];
        BindLogic<T> bindLogic = this.viewHolderTypes.get(viewHolderType);
        int layoutId = bindLogic.getLayoutId();
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        return new GenericRecyclerViewAdapter.ViewHolder<T>(view) {
            @Override
            public void bind(T item) {
                bindLogic.getBindFunction().bind(item, this);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull GenericRecyclerViewAdapter.ViewHolder holder, int position) {
        T item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        T item = items.get(position);
        return viewHolderDispatcher.determineType(item).ordinal();
    }

    public abstract static class ViewHolder<R> extends RecyclerView.ViewHolder
    {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(R item);
    }

    public interface ViewHolderDispatcher<T> {
        ViewHolderType determineType(T item);
    }
}
