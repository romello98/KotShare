package com.example.kotshare.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.kotshare.R;
import com.example.kotshare.controller.CityController;
import com.example.kotshare.controller.StudentRoomController;
import com.example.kotshare.model.City;
import com.example.kotshare.model.Photo;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.form.StudentRoomForm;
import com.example.kotshare.utils.Validator;
import com.example.kotshare.utils.ViewUtils;
import com.example.kotshare.view.PhotosManager;
import com.example.kotshare.view.SharedPreferencesAccessor;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class EditStudentRoomActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;


    @BindView(R.id.editText_studentRoomName)
    EditText editText_studentRoomName;

    @BindView(R.id.editText_studentRoomDescription)
    EditText editText_studentRoomDescription;

    @BindView(R.id.button_addOrEditStudentRoom)
    Button button_addOrEditStudentRoom;

    @BindView(R.id.button_deleteStudentRoom)
    Button button_deleteStudentRoom;

    @BindView(R.id.spinner_studentRoomPlace)
    Spinner spinner_studentRoomPlace;

    @BindView(R.id.editText_studentRoomPrice)
    EditText editText_studentRoomPrice;

    @BindView(R.id.editText_studentRoomStartDate)
    EditText editText_studentRoomStartDate;

    @BindView(R.id.editText_studentRoomEndDate)
    EditText editText_studentRoomEndDate;

    @BindView(R.id.editText_studentRoomRoommates)
    EditText editText_studentRoomRoommates;

    @BindView(R.id.editText_studentRoomStreetNumber)
    EditText editText_studentRoomStreetNumber;

    @BindView(R.id.editText_studentRoomStreet)
    EditText editText_studentRoomStreet;

    @BindView(R.id.button_selectPhotos)
    Button button_selectPhotos;

    @BindView(R.id.switch_wifi)
    Switch switch_wifi;

    @BindView(R.id.switch_bathroom)
    Switch switch_bathroom;

    @BindView(R.id.switch_garden)
    Switch switch_garden;

    @BindView(R.id.switch_kitchen)
    Switch switch_kitchen;


    private City[] cities;

    private StudentRoomController studentRoomController;
    private CityController cityController;
    private Uri[] uploadedPhotosURIs;
    private boolean photosChanged = false;
    private int studentRoomId;
    private StudentRoom studentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_room);
        studentRoomController = new StudentRoomController();
        cityController = new CityController();
        ButterKnife.bind(this);
        Intent intent = getIntent();
        studentRoomId = intent.getIntExtra(getString(R.string.STUDENT_ROOM_ID), -1);

        if (studentRoomId == -1) {
            setTitle(getString(R.string.add_student_room));
            button_deleteStudentRoom.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0));
            new Thread(this::initSpinner).start();
            button_addOrEditStudentRoom.setOnClickListener(view -> {
                StudentRoomForm studentRoomForm = buildForm();
                ArrayList<String> errors = Validator.getInstance(this)
                        .validateStudentRoomForm(studentRoomForm);

                if (!errors.isEmpty()) {
                    ViewUtils.showErrors(this, errors);
                } else {
                    new Thread(() -> {
                        Call<StudentRoom> call = studentRoomController.addStudentRoom(studentRoomForm);
                        try {
                            Response<StudentRoom> response = call.execute();
                            if (response.isSuccessful()) {
                                if(photosChanged)
                                    updatePhotos();
                                ViewUtils.showDialog(this, getString(R.string.success),
                                        getString(R.string.success_student_room_added));
                            } else {
                                ViewUtils.showDialog(this, getString(R.string.error_request),
                                        getString(R.string.error_request_client));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            if (ViewUtils.isConnected(this))
                                ViewUtils.showDialog(this, getString(R.string.error_unknown),
                                        getString(R.string.error_unknown_happened));
                            else
                                ViewUtils.alertNoInternetConnection(this, view);
                        }
                    }).start();
                }
            });
        } else {
            setTitle(getString(R.string.edit_student_room));
            getStudentRoom(studentRoomId);
            button_addOrEditStudentRoom.setText(getString(R.string.edit_student_room));
            button_addOrEditStudentRoom.setOnClickListener(view -> {
                StudentRoomForm studentRoomForm = buildForm();
                ArrayList<String> errors = Validator.getInstance(this)
                        .validateStudentRoomForm(studentRoomForm);

                if (!errors.isEmpty()) {
                    ViewUtils.showErrors(this, errors);
                } else {
                    new Thread(() -> {
                        Call<StudentRoom> call = studentRoomController.updateStudentRoom(studentRoomId,
                                studentRoomForm);
                        try {
                            Response<StudentRoom> response = call.execute();
                            if (response.isSuccessful()) {
                                if (photosChanged) {
                                    updatePhotos();
                                }
                                ViewUtils.showDialog(this, getString(R.string.success),
                                        getString(R.string.success_student_room_updated));
                                initSpinner();
                            } else {
                                ViewUtils.showDialog(this, getString(R.string.error_request),
                                        getString(R.string.error_request_client));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            if (ViewUtils.isConnected(this))
                                ViewUtils.showDialog(this, getString(R.string.error_unknown),
                                        getString(R.string.error_unknown_happened));
                            else
                                ViewUtils.alertNoInternetConnection(this, view);
                        }
                    }).start();
                }
            });
        }

        initPhotosButton();
    }

    private void initPhotosButton() {
        button_selectPhotos.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }

    private void updatePhotos() throws IOException {
            PhotosManager.getInstance(this).updatePhotos(studentRoomId == -1,
                    studentRoomId, uploadedPhotosURIs);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK && null != data) {
                    try {
                        // Get the Image from data
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        ArrayList<String> imagesEncodedList = new ArrayList<>();
                        if(data.getData()!=null){
                            Uri singleImageUri = data.getData();
                            uploadedPhotosURIs = new Uri[1];
                            uploadedPhotosURIs[0] = singleImageUri;
                            photosChanged = true;
                        } else {
                            ClipData clipData = data.getClipData();
                            if (clipData != null) {
                                int clipSize = clipData.getItemCount();
                                if(clipSize > 0) {
                                    uploadedPhotosURIs = new Uri[clipSize];
                                    for (int i = 0; i < clipSize; i++)
                                        uploadedPhotosURIs[i] = clipData.getItemAt(i).getUri();
                                    photosChanged = true;
                                }
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private StudentRoomForm buildForm()
    {
        StudentRoomForm studentRoomForm = new StudentRoomForm();
        studentRoomForm.setHidden(false);
        studentRoomForm.setTitle(editText_studentRoomName.getText().toString());
        studentRoomForm.setDescription(editText_studentRoomDescription.getText().toString());
        studentRoomForm.setCityId(getCityChoice());
        studentRoomForm.setStartRentingDate(getDate(editText_studentRoomStartDate.getText()));
        studentRoomForm.setEndRentingDate(getDate(editText_studentRoomEndDate.getText()));
        studentRoomForm.setFreeWifi(switch_wifi.isChecked());
        studentRoomForm.setHasGarden(switch_garden.isChecked());
        studentRoomForm.setPersonnalBathroom(switch_bathroom.isChecked());
        studentRoomForm.setPersonnalKitchen(switch_kitchen.isChecked());
        if(studentRoom == null && studentRoomId == -1) {
            //Bruxelles
            studentRoomForm.setLat(50.8466);
            studentRoomForm.setLong(4.3528);
        } else {
            studentRoomForm.setLat(studentRoom.getLat());
            studentRoomForm.setLong(studentRoomForm.getLong());
        }
        studentRoomForm.setUserId(SharedPreferencesAccessor.getInstance().getUser().getId());
        String price = editText_studentRoomPrice.getText().toString();
        studentRoomForm.setMonthlyPrice(!price.matches("\\d+") ? null :
                Integer.parseInt(price));
        String roommatesNumber = editText_studentRoomRoommates.getText().toString();
        studentRoomForm.setNumberRoommate(!roommatesNumber.matches("\\d+") ? null :
                Integer.parseInt(roommatesNumber));
        studentRoomForm.setStreet(editText_studentRoomStreet.getText().toString());
        studentRoomForm.setStreetNumber(editText_studentRoomStreetNumber.getText().toString());
        return studentRoomForm;
    }

    private void getStudentRoom(int id)
    {
        new Thread(() -> {
            Call<StudentRoom> call = studentRoomController.find(id);
            try {
                Response<StudentRoom> response = call.execute();
                if(response.isSuccessful())
                {
                    studentRoom = response.body();
                    initSpinner();
                    runOnUiThread(() -> this.fetchForm(studentRoom));
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            ViewUtils.showDialog(this, getString(R.string.errors_title),
                    getString(R.string.error_unknown_happened));
        }).start();
    }

    private void fetchForm(StudentRoom studentRoom)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int cityPosition = indexOfCity(studentRoom.getCity().getId());

        editText_studentRoomName.setText(studentRoom.getTitle());
        editText_studentRoomDescription.setText(studentRoom.getDescription());
        editText_studentRoomPrice.setText(String.format("%d", studentRoom.getMonthlyPrice()));
        editText_studentRoomStreet.setText(studentRoom.getStreet());
        editText_studentRoomStreetNumber.setText(studentRoom.getStreetNumber());
        editText_studentRoomRoommates.setText(emptyIfNull(studentRoom.getNumberRoommate()));
        if(studentRoom.getStartRentingDate() != null)
            editText_studentRoomStartDate.setText(simpleDateFormat.format(studentRoom.getStartRentingDate()));
        if(studentRoom.getEndRentingDate() != null)
            editText_studentRoomEndDate.setText(simpleDateFormat.format(studentRoom.getEndRentingDate()));
        if(cityPosition != -1)
            spinner_studentRoomPlace.setSelection(cityPosition);
        switch_wifi.setChecked(studentRoom.getFreeWifi());
        switch_bathroom.setChecked(studentRoom.getPersonnalBathroom());
        switch_kitchen.setChecked(studentRoom.getPersonnalKitchen());
        switch_garden.setChecked(studentRoom.getHasGarden());
    }

    private String emptyIfNull(Integer integer)
    {
        return integer == null ? "" : integer.toString();
    }

    private String emptyIfNull(String string)
    {
        return string == null ? "" : string;
    }

    private int indexOfCity(Integer cityId)
    {
        int position = 0;
        for(City city : cities) {
            if (city.getId() != null && city.getId() == cityId)
                return position;
            position++;
        }
        return -1;
    }

    private Date getDate(Editable editable)
    {
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            date.setLenient(false);
            Date extractedDate = date.parse(editable.toString());
            return extractedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initSpinner()
    {
        Call<List<City>> citiesCall = cityController.getAll();
        Response<List<City>> response = null;
        try {
            response = citiesCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response.isSuccessful()) {
            List<City> cities = new ArrayList<>();
            City defaultCityChoice = new City();
            ArrayAdapter<City> adapter;

            defaultCityChoice.setName(EditStudentRoomActivity.this.getString(R.string.select_city));
            cities.add(defaultCityChoice);
            cities.addAll(response.body());
            this.cities = cities.toArray(new City[0]);
            adapter = new ArrayAdapter<>(EditStudentRoomActivity.this,
                    android.R.layout.simple_spinner_item, cities);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            runOnUiThread( () -> spinner_studentRoomPlace.setAdapter(adapter));
        }
        else
        {
            ViewUtils.showDialog(this, getString(R.string.error_request),
                    getString(R.string.error_request_client));
        }
    }

    private Integer getCityChoice()
    {
        Spinner spinner = spinner_studentRoomPlace;
        if(spinner.getSelectedItem() == null || spinner.getSelectedItemPosition() == 0) return null;
        City selectedCity = (City)spinner.getSelectedItem();
        return selectedCity.getId();
    }

    @Override
    public boolean onSupportNavigateUp(){
        super.onBackPressed();
        return true;
    }

}
