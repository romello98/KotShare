package com.example.kotshare.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kotshare.R;
import com.example.kotshare.controller.SchoolController;
import com.example.kotshare.model.City;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.School;
import com.example.kotshare.model.UserForm;
import com.example.kotshare.view.Utils;
import com.example.kotshare.view.nav.home.HomeFragment;
import com.example.kotshare.view.recycler_views.Util;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SignupFragment extends Fragment
{
    @BindView(R.id.editText_signupPassword)
    EditText password;

    @BindView(R.id.editText_signupEmail)
    EditText email;

    @BindView(R.id.editText_signupPasswordConfirmation)
    EditText passwordConfirmation;

    @BindView(R.id.spinner_schools)
    Spinner schools;

    @BindView(R.id.editText_firstName)
    EditText firstName;

    @BindView(R.id.editText_name)
    EditText lastName;

    @BindView(R.id.editText_phone)
    EditText phoneNumber;
    private SchoolController schoolController;

    public SignupFragment()
    {
        schoolController = new SchoolController();
    }

    public String getPasswordConfirmation()
    {
        return passwordConfirmation.getText().toString();
    }

    public boolean arePasswordsEqual()
    {
        return password.getText().toString().equals(getPasswordConfirmation());
    }

    public UserForm getUserForm()
    {
        UserForm userForm = new UserForm();
        School selectedSchool = (School)schools.getSelectedItem();
        userForm.setPassword(password.getText().toString());
        userForm.setEmail(email.getText().toString());
        userForm.setPhoneNumber(phoneNumber.getText().toString());
        userForm.setSchoolId(selectedSchool == null ? null : selectedSchool.getId());
        userForm.setFirstName(firstName.getText().toString());
        userForm.setLastName(lastName.getText().toString());
        return userForm;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        loadSchoolsSpinner();
        return view;
    }

    private void loadSchoolsSpinner()
    {
        if(!Utils.isConnected(getContext()))
        {
            Utils.alertNoInternetConnection(getContext(), getView());
            return;
        }

        Thread loadSpinnerThread = new Thread(() -> {
            Call<PagedResult<School>> call = schoolController.getAllSchools(null,
                    100);
            try {
                Response<PagedResult<School>> response = call.execute();
                if(response.isSuccessful())
                {
                    School defaultChoice = new School();
                    ArrayList<School> schools = new ArrayList<>();
                    ArrayAdapter<School> adapter;

                    defaultChoice.setName(getString(R.string.select_school));
                    schools.add(defaultChoice);
                    schools.addAll(response.body().getItems());
                    adapter = new ArrayAdapter<>(SignupFragment.this.getContext(),
                            android.R.layout.simple_spinner_item, schools.toArray(new School[0]));

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.schools.setAdapter(adapter);
                }
                else
                {
                    Utils.showDialog(SignupFragment.this.getActivity(), getString(R.string.error_request),
                            getString(R.string.error_request_client));
                }
            } catch (IOException e) {
                e.printStackTrace();
                if(Utils.isConnected(SignupFragment.this.getContext()))
                    Utils.showDialog(SignupFragment.this.getActivity(), getString(R.string.error_network),
                        getString(R.string.error_connection));
                else
                    Utils.alertNoInternetConnection(SignupFragment.this.getContext(), getView());
            }
        });
        loadSpinnerThread.start();

        try {
            loadSpinnerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public School getSelectedSchool() {
        return (School) schools.getSelectedItem();
    }
}
