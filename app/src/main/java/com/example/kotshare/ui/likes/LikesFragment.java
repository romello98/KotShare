package com.example.kotshare.ui.likes;

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

public class LikesFragment extends Fragment {

    private LikesViewModel likesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        likesViewModel =
                ViewModelProviders.of(this).get(LikesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_likes, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        likesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}