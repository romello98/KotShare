package com.example.kotshare.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kotshare.R;
import com.example.kotshare.controller.StudentRoomController;
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
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRoomActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Nullable
    @BindView(R.id.textView_singleStudentRoomTitle)
    TextView textView_singleStudentRoomTitle;

    @BindView(R.id.localisation)
    TextView textView_localisation;

    @BindView(R.id.textView_description)
    TextView textView_description;

    @BindView(R.id.imageSlider)
    SliderView sliderStudentRoom;
    private StudentRoom studentRoom;
    private StudentRoomController studentRoomController;

    /*
    @BindView(R.id.phone)
    ImageView phoneButton;
    */

    @BindView(R.id.informationsStudentRoom)
    RecyclerView characteristicsRecyclerView;
    private RecyclerView.Adapter characteristicsAdapter;
    private RecyclerView.LayoutManager characteristicsLayoutManager;

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;

    public StudentRoomActivity()
    {
        this.studentRoomController = new StudentRoomController();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getIntExtra(getString(R.string.STUDENT_ROOM_ID), -1);

        new Thread(() -> {
            if(id != -1)
            {
                Call<StudentRoom> studentRoomCall = studentRoomController.find(id);
                Callback<StudentRoom> callback = new Callback<StudentRoom>() {
                    @Override
                    public void onResponse(Call<StudentRoom> call, Response<StudentRoom> response) {
                        if(response.isSuccessful()) {
                            studentRoom = response.body();
                            initView();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentRoom> call, Throwable t) {

                    }
                };
                studentRoomCall.enqueue(callback);

            }
        }).start();
    }

    private void initView()
    {
        setContentView(R.layout.activity_student_room);
        ButterKnife.bind(StudentRoomActivity.this);
        textView_singleStudentRoomTitle.setText(studentRoom.getTitle());
        textView_localisation.setText(studentRoom.getCity().toString());

        if(studentRoom.getDescription() == null)
            textView_description.setText(getString(R.string.no_description));
        else
            textView_description.setText(studentRoom.getDescription());

        sliderStudentRoom.setSliderAdapter(new SliderPhotosAdapter(StudentRoomActivity.this));
        sliderStudentRoom.startAutoCycle();
        sliderStudentRoom.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderStudentRoom.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        ArrayList<CharacteristicStudentRoom> characteristics = new ArrayList<>();
        characteristics.add(new CharacteristicStudentRoom(
                R.drawable.ic_coin, String.format(
                        getString(R.string.price_format),
                        String.format(Locale.FRENCH, "%.2f", studentRoom.getMonthlyPrice())
                )
        ));

        characteristics.add(new CharacteristicStudentRoom(
                R.drawable.ic_bathtub, getString(studentRoom.getPersonnalBathroom() ?
                R.string.private_bathroom : R.string.shared_bathroom) ));
        characteristics.add(new CharacteristicStudentRoom(
                R.drawable.ic_kitchen, getString(studentRoom.getPersonnalKitchen() ?
                R.string.private_kitchen : R.string.shared_kitchen)));
        characteristics.add(new CharacteristicStudentRoom(
                R.drawable.ic_tree, getString(studentRoom.getHasGarden() ?
                R.string.accessible_garden : R.string.no_garden)));
        characteristics.add(new CharacteristicStudentRoom(
                R.drawable.ic_roommate, String.format(getString(R.string.roommates_number),
                studentRoom.getNumberRoommate())));
        characteristics.add(new CharacteristicStudentRoom(
                R.drawable.ic_wifi, getString(studentRoom.getFreeWifi() ?
                R.string.included_wifi : R.string.not_included_wifi)));

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
        LatLng city = new LatLng(studentRoom.getLat(), studentRoom.getLong());
        mapAPI.addMarker(new MarkerOptions().position(city).title(studentRoom.getCity().getName()));
        float zoom = (float)11.0;
        mapAPI.moveCamera(CameraUpdateFactory.newLatLngZoom(city, zoom));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp(){
        super.onBackPressed();
        return true;
    }

    /*
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        Intent callIntent = new Intent();
        callIntent.setAction(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:123456789"));



        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(callIntent);
            }
        });
        return parent;
    }
    */
}
