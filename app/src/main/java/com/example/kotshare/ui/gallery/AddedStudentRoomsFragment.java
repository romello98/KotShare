package com.example.kotshare.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kotshare.R;

public class AddedStudentRoomsFragment extends Fragment {

    private AddedStudentRoomsViewModel addedStudentRoomsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addedStudentRoomsViewModel =
                ViewModelProviders.of(this).get(AddedStudentRoomsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_added_student_rooms, container, false);
        final TextView textView = root.findViewById(R.id.text_added_student_rooms);
        addedStudentRoomsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}