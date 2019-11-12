package com.example.kotshare.view.nav.student_rooms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddedStudentRoomsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddedStudentRoomsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is student rooms fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}