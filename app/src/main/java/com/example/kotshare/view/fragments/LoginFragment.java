package com.example.kotshare.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.kotshare.R;
import com.example.kotshare.model.Login;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment {

    @BindView(R.id.editTextEmail)
    EditText email;

    @BindView(R.id.editTextPassword)
    EditText password;

    public LoginFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getEmail()
    {
        return email.getText().toString();
    }

    public String getPassword()
    {
        return password.getText().toString();
    }

    public Login getLoginForm()
    {
        Login login = new Login();
        login.setPassword(getPassword());
        login.setEmail(getEmail());
        return login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
