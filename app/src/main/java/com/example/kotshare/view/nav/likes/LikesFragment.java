package com.example.kotshare.view.nav.likes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.kotshare.R;

public class LikesFragment extends Fragment {

    private LikesViewModel likesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        likesViewModel =
                ViewModelProviders.of(this).get(LikesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_likes, container, false);
        return root;
    }
}