package com.example.kotshare.view.activities;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kotshare.R;
import com.example.kotshare.data_access.StudentRoomDAO;
import com.example.kotshare.data_access.StudentRoomDataAccess;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.view.recycler_views.CharacteristicStudentRoom;
import com.example.kotshare.view.recycler_views.CharacteristicsAdapter;
import com.example.kotshare.view.recycler_views.SliderPhotosAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentRoomActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.textView_singleStudentRoomTitle)
    TextView textView_singleStudentRoomTitle;

    @BindView(R.id.imageSlider)
    SliderView sliderStudentRoom;
    private StudentRoom studentRoom;
    private StudentRoomDataAccess studentRoomDataAccess;

    @BindView(R.id.informationsStudentRoom)
    RecyclerView characteristicsRecyclerView;
    private RecyclerView.Adapter characteristicsAdapter;
    private RecyclerView.LayoutManager characteristicsLayoutManager;

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;

    public StudentRoomActivity()
    {
        this.studentRoomDataAccess = new StudentRoomDAO();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getIntExtra(getString(R.string.STUDENT_ROOM_ID), -1);

        if(id != -1)
        {
            studentRoom = studentRoomDataAccess.find(id);

            if(studentRoom != null) {
                setContentView(R.layout.activity_student_room);

                ButterKnife.bind(this);

                textView_singleStudentRoomTitle.setText(studentRoom.getTitle());

                sliderStudentRoom.setSliderAdapter(new SliderPhotosAdapter(this.getApplicationContext()));
                sliderStudentRoom.startAutoCycle();
                sliderStudentRoom.setIndicatorAnimation(IndicatorAnimations.WORM);
                sliderStudentRoom.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            }
        }

        // Characteristics
        ArrayList<CharacteristicStudentRoom> characteristics = new ArrayList<>();
        characteristics.add(new CharacteristicStudentRoom(R.drawable.ic_coin, "360€ /mois"));
        characteristics.add(new CharacteristicStudentRoom(R.drawable.ic_bathtub, "Salle de bain partagée"));
        characteristics.add(new CharacteristicStudentRoom(R.drawable.ic_kitchen, "Cuisine partagée"));
        characteristics.add(new CharacteristicStudentRoom(R.drawable.ic_tree, "Jardin accessible"));
        characteristics.add(new CharacteristicStudentRoom(R.drawable.ic_roommate, "4 colocataires"));
        characteristics.add(new CharacteristicStudentRoom(R.drawable.ic_wifi, "Wifi compris"));

        characteristicsRecyclerView.setHasFixedSize(true);
        characteristicsLayoutManager = new LinearLayoutManager(this);
        characteristicsAdapter = new CharacteristicsAdapter(characteristics);

        characteristicsRecyclerView.setLayoutManager(characteristicsLayoutManager);
        characteristicsRecyclerView.setAdapter(characteristicsAdapter);


        // Google map
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mapAPI = googleMap;
        LatLng aubel = new LatLng(50.6970841, 5.8245657);
        mapAPI.addMarker(new MarkerOptions().position(aubel).title("Aubel"));
        float zoom = (float)11.0;
        mapAPI.moveCamera(CameraUpdateFactory.newLatLngZoom(aubel, zoom));
    }

}
