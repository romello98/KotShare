package com.example.kotshare.ui.my_student_rooms;

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

public class MyStudentRoomsFragment extends Fragment {

    private MyStudentRoomsViewModel myStudentRoomsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myStudentRoomsViewModel =
                ViewModelProviders.of(this).get(MyStudentRoomsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_student_rooms, container, false);
        return root;
    }
}