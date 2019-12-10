package com.example.kotshare.view.recycler_views;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kotshare.model.StudentRoom;

import java.util.List;

public class GenericRecyclerView extends RecyclerView.Adapter<GenericRecyclerView.GenericViewHolder> {

    private enum Type { FIRST, SECOND }

    private List<StudentRoom> items;
    private ViewHolderBindingActionSpliter<StudentRoom, FirstTypeViewHolder, SecondTypeViewHolder> viewHolderBindingActionSpliter;

    public GenericRecyclerView(List<StudentRoom> items,
                               ViewHolderBindingActionSpliter<StudentRoom, FirstTypeViewHolder,
                                       SecondTypeViewHolder> viewHolderBindingActionSpliter)
    {
        this.items = items;
        this.viewHolderBindingActionSpliter = viewHolderBindingActionSpliter;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == Type.FIRST.ordinal())
        {
            // Gonfler la vue du premier type
            view = null;
            return new FirstTypeViewHolder(view);
        }
        // Sinon, gonfler celle du deuxième type
        view = null;
        return new SecondTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        StudentRoom studentRoom = items.get(position);

        if(holder instanceof FirstTypeViewHolder)
            viewHolderBindingActionSpliter.bindAsFirstType(studentRoom, (FirstTypeViewHolder) holder);
        else
            viewHolderBindingActionSpliter.bindAsSecondType(studentRoom, (SecondTypeViewHolder) holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        StudentRoom studentRoom = items.get(position);
        if(viewHolderBindingActionSpliter.isFirstCategory(studentRoom))
            return Type.FIRST.ordinal();
        return Type.SECOND.ordinal();
    }

    public abstract class GenericViewHolder extends RecyclerView.ViewHolder
    {
        public GenericViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FirstTypeViewHolder extends GenericViewHolder
    {
        public FirstTypeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class SecondTypeViewHolder extends GenericViewHolder
    {
        public SecondTypeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface ViewHolderBindingActionSpliter<T, R extends GenericViewHolder,
            V extends GenericViewHolder>
    {
        boolean isFirstCategory(T item);
        void bindAsFirstType(T firstCategoryItem, R viewHolder);
        void bindAsSecondType(T secondCategoryItem, V viewHolder);
    }
}
