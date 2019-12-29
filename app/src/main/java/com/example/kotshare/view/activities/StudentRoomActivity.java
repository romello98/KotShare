package com.example.kotshare.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kotshare.R;
import com.example.kotshare.controller.RatingController;
import com.example.kotshare.controller.StudentRoomController;
import com.example.kotshare.model.Rating;
import com.example.kotshare.model.data_model.RatingDataModel;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.utils.Calculator;
import com.example.kotshare.utils.ViewUtils;
import com.example.kotshare.view.SharedPreferencesAccessor;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @BindView(R.id.ratingStars)
    RatingBar ratingBar;

    @BindView(R.id.textView_ratingMean)
    TextView textView_ratingMean;

    private static final float STEP_SIZE = 0.5f;

    private StudentRoom studentRoom;
    private Rating currentRating;
    private StudentRoomController studentRoomController;
    private RatingController ratingController;
    private Runnable getAllRatingsAction;

    /*
    @BindView(R.id.phone)
    ImageView phoneButton;
    */

    @BindView(R.id.informationsStudentRoom)
    RecyclerView characteristicsRecyclerView;
    private RecyclerView.Adapter characteristicsAdapter;
    private RecyclerView.LayoutManager characteristicsLayoutManager;

    private boolean isSettingRating = true;

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;

    public StudentRoomActivity()
    {
        this.studentRoomController = new StudentRoomController();
        this.ratingController = new RatingController();
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
        textView_localisation.setText(studentRoom.getAddress());

        loadRatingBar();

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
                        String.format(Locale.FRENCH, "%d", studentRoom.getMonthlyPrice())
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

    public void loadRatingBar()
    {
        ratingBar.setStepSize(STEP_SIZE);
        setRating();
        getAllRatingsAction = () -> {
            Call<List<Float>> call = ratingController.getRatings(studentRoom.getId());
            try {
                Response<List<Float>> response = call.execute();
                if(response.isSuccessful())
                {
                    runOnUiThread(() -> {
                        if(response.body().size() > 0) {
                            Float mean = Calculator.mean(response.body());
                            textView_ratingMean.setText(mean.toString() + " / 5");
                        } else {
                            textView_ratingMean.setText(getString(R.string.no_vote));
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread getAllRatings = new Thread(getAllRatingsAction);

        getAllRatings.start();

        ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            if(isSettingRating) return;
            final Integer userId = SharedPreferencesAccessor.getInstance().getUser().getId();
            final Integer studentRoomId = studentRoom.getId();

            Thread addRating = new Thread(() -> {
                Call<Rating> call = ratingController.setRating(new RatingDataModel(userId, studentRoomId, v));
                try {
                    Response<Rating> response = call.execute();
                    if(!response.isSuccessful())
                        ViewUtils.showDialog(StudentRoomActivity.this, getString(R.string.error_request),
                                getString(R.string.error_request_client));
                    else
                    {
                        new Thread(getAllRatingsAction).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(ViewUtils.isConnected(StudentRoomActivity.this))
                        ViewUtils.showDialog(StudentRoomActivity.this, getString(R.string.error_unknown),
                                getString(R.string.error_unknown_happened));
                    else
                        ViewUtils.alertNoInternetConnection(StudentRoomActivity.this,
                                ratingBar);
                }
            });

            Thread deleteRating = new Thread(() -> {
                Call<Void> call = ratingController.delete(userId, studentRoomId);
                try {
                    Response<Void> response = call.execute();
                    if(response.isSuccessful())
                        addRating.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            deleteRating.start();
        });
    }

    private void setRating()
    {
        final Integer userId = SharedPreferencesAccessor.getInstance().getUser().getId();
        final Integer studentRoomId = studentRoom.getId();
        new Thread(() -> {
            Call<Rating> call = ratingController.getRating(userId, studentRoomId);
            try {
                Response<Rating> response = call.execute();
                if(response.isSuccessful())
                {
                    Rating rating = response.body();
                    if(rating != null)
                        runOnUiThread(() ->
                        {
                            ratingBar.setRating(rating.getRatingValue());
                            isSettingRating = false;
                        });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
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
