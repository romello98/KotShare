package com.example.kotshare.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.kotshare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditStudentRoomActivity extends AppCompatActivity {

    @BindView(R.id.editText_studentRoomName)
    EditText editText_studentRoomName;

    @BindView(R.id.button_addOrEditStudentRoom)
    Button button_addOrEditStudentRoom;

    @BindView(R.id.button_deleteStudentRoom)
    Button button_deleteStudentRoom;

    @BindView(R.id.editText_studentRoomPlace)
    EditText editText_studentRoomPlace;

    @BindView(R.id.editText_studentRoomStartDate)
    EditText editText_studentRoomStartDate;

    @BindView(R.id.editText_studentRoomEndDate)
    EditText editText_studentRoomEndDate;

    @BindView(R.id.editText_studentRoomRoommates)
    EditText editText_studentRoomRoommates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_room);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        if(intent.getIntExtra(getString(R.string.STUDENT_ROOM_ID), -1) == -1)
        {
            setTitle(getString(R.string.add_student_room));
            button_deleteStudentRoom.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0));
        }
        else
        {
            setTitle(getString(R.string.edit_student_room));
            button_addOrEditStudentRoom.setText(getString(R.string.edit_student_room));
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        super.onBackPressed();
        return true;
    }
}
