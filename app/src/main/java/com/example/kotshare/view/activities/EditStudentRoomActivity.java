package com.example.kotshare.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.form.StudentRoomForm;
import com.example.kotshare.utils.Validator;
import com.example.kotshare.utils.ViewUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_room);
        studentRoomController = new StudentRoomController();
        cityController = new CityController();
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int studentRoomId = intent.getIntExtra(getString(R.string.STUDENT_ROOM_ID), -1);

        if(studentRoomId == -1)
        {
            setTitle(getString(R.string.add_student_room));
            button_deleteStudentRoom.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0));
            new Thread(this::initSpinner).start();
            button_addOrEditStudentRoom.setOnClickListener(view -> {
                StudentRoomForm studentRoomForm = buildForm();
                ArrayList<String> errors = Validator.getInstance(this)
                        .validateStudentRoomForm(studentRoomForm);

                if(!errors.isEmpty())
                {
                    ViewUtils.showErrors(this, errors);
                }
                else
                {
                    new Thread(() -> {
                        Call<StudentRoom> call = studentRoomController.addStudentRoom(studentRoomForm);
                        try {
                            Response<StudentRoom> response = call.execute();
                            if(response.isSuccessful())
                            {
                                ViewUtils.showDialog(this, getString(R.string.success),
                                        getString(R.string.success_student_room_added));
                            }
                            else
                            {
                                ViewUtils.showDialog(this, getString(R.string.error_request),
                                        getString(R.string.error_request_client));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            if(ViewUtils.isConnected(this))
                                ViewUtils.showDialog(this, getString(R.string.error_unknown),
                                        getString(R.string.error_unknown_happened));
                            else
                                ViewUtils.alertNoInternetConnection(this, view);
                        }
                    }).start();
                }
            });
        }
        else
        {
            setTitle(getString(R.string.edit_student_room));
            getStudentRoom(studentRoomId);
            button_addOrEditStudentRoom.setText(getString(R.string.edit_student_room));
            button_addOrEditStudentRoom.setOnClickListener(view -> {
                StudentRoomForm studentRoomForm = buildForm();
                ArrayList<String> errors = Validator.getInstance(this)
                        .validateStudentRoomForm(studentRoomForm);

                if(!errors.isEmpty())
                {
                    ViewUtils.showErrors(this, errors);
                }
                else
                {
                    new Thread(() -> {
                        Call<StudentRoom> call = studentRoomController.updateStudentRoom(studentRoomId,
                                studentRoomForm);
                        try {
                            Response<StudentRoom> response = call.execute();
                            if(response.isSuccessful())
                            {
                                ViewUtils.showDialog(this, getString(R.string.success),
                                        getString(R.string.success_student_room_added));
                                initSpinner();
                            }
                            else
                            {
                                ViewUtils.showDialog(this, getString(R.string.error_request),
                                        getString(R.string.error_request_client));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            if(ViewUtils.isConnected(this))
                                ViewUtils.showDialog(this, getString(R.string.error_unknown),
                                        getString(R.string.error_unknown_happened));
                            else
                                ViewUtils.alertNoInternetConnection(this, view);
                        }
                    }).start();
                }
            });
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
        studentRoomForm.setLat(50.8466);
        studentRoomForm.setLong(4.3528);
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
                    StudentRoom studentRoom = response.body();
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
        switch_wifi.setSelected(studentRoom.getFreeWifi());
        switch_bathroom.setSelected(studentRoom.getPersonnalBathroom());
        switch_kitchen.setSelected(studentRoom.getPersonnalKitchen());
        switch_garden.setSelected(studentRoom.getHasGarden());
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
