package com.example.kotshare.view.nav.likes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LikesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LikesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is likes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}