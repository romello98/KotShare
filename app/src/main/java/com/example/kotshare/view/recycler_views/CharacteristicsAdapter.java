package com.example.kotshare.view.recycler_views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotshare.R;

import java.util.ArrayList;


public class CharacteristicsAdapter extends RecyclerView.Adapter<CharacteristicsAdapter.CharacteristicsViewHolder>
{
    private ArrayList<CharacteristicStudentRoom> characteristicsList;

    public static class CharacteristicsViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mImageView;
        public TextView mTextView;

        public CharacteristicsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.mImageView);
            mTextView = itemView.findViewById(R.id.mTextView);
        }
    }

    public CharacteristicsAdapter(ArrayList<CharacteristicStudentRoom> characteristicsList)
    {
        this.characteristicsList = characteristicsList;
    }

    @Override
    public CharacteristicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.characteristic_student_room, parent, false);
        CharacteristicsViewHolder evh = new CharacteristicsViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(CharacteristicsViewHolder holder, int position)
    {
        CharacteristicStudentRoom currentCharacteristic = characteristicsList.get(position);

        holder.mImageView.setImageResource(currentCharacteristic.getImageResource());
        holder.mTextView.setText(currentCharacteristic.getText());
    }

    @Override
    public int getItemCount()
    {
        return characteristicsList.size();
    }
}
