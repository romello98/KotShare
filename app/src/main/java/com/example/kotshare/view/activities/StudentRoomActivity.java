package com.example.kotshare.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kotshare.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class StudentRoomActivity extends AppCompatActivity {

    SliderView sliderStudentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_room);
        sliderStudentRoom = findViewById(R.id.imageSlider);
        sliderStudentRoom.setSliderAdapter(new SliderPhotosAdapter(this.getApplicationContext()));

        sliderStudentRoom.startAutoCycle();
        sliderStudentRoom.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderStudentRoom.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }
}
