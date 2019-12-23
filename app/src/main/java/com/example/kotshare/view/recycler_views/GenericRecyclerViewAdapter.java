package com.example.kotshare.view.recycler_views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotshare.controller.StudentRoomController;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenericRecyclerViewAdapter<T> extends RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder> {

    private List<T> items;
    private int pageNumber = 0;
    private StudentRoomController studentRoomController;
    private ViewHolderDispatcher<T> viewHolderDispatcher;
    private HashMap<ViewHolderType, BindLogic<T>> viewHolderTypes;
    private final ViewHolderType[] typeValues = ViewHolderType.values();
    private List<ViewHolder> viewHolders = new ArrayList<>();

    public GenericRecyclerViewAdapter(List<T> items,
                                      ViewHolderDispatcher<T> viewHolderDispatcher,
                                      HashMap<ViewHolderType, BindLogic<T>> viewHolders)
    {
        this.items = items;
        this.viewHolderDispatcher = viewHolderDispatcher;
        this.viewHolderTypes = viewHolders;
        this.studentRoomController = new StudentRoomController();
    }

    @NonNull
    @Override
    public GenericRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderType viewHolderType = typeValues[viewType];
        BindLogic<T> bindLogic = this.viewHolderTypes.get(viewHolderType);
        int layoutId = bindLogic.getLayoutId();
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        Log.i("app", "Created view !!!");

        ViewHolder viewHolder = new GenericRecyclerViewAdapter.ViewHolder<T>(view) {
            @Override
            public void bind(T item) {
                bindLogic.getBindFunction().bind(item, this);
            }
        };

        viewHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenericRecyclerViewAdapter.ViewHolder holder, int position) {
        T item = items.get(position);
        holder.bind(item);
    }

    public void loadNext()
    {
        pageNumber++;
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void initPageNumber()
    {
        this.pageNumber = 0;
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
