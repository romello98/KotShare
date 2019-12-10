package com.example.kotshare.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kotshare.R;
import com.example.kotshare.controller.UserController;
import com.example.kotshare.model.User;
import com.example.kotshare.view.SharedPreferencesAccessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private UserController userController = new UserController();

    @BindView(R.id.editTextEmail)
    EditText email;

    @BindView(R.id.editTextPassword)
    EditText password;

    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!hasStoredUser()) {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            defineEvents();
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void defineEvents()
    {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User foundUser = retrieveUser();
                String userPassword = password.getText().toString();

                if(foundUser != null && userController.isCorrectPassword(foundUser, userPassword)) {
                    SharedPreferencesAccessor.getInstance().updateSavedUser(foundUser, LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private User retrieveUser()
    {
        //TODO: vérifier que l'utilisateur existe et possède les bons identifiants
        String userEmail = email.getText().toString();
        return userController.getUserByEmail(userEmail);
    }

    private boolean hasStoredUser()
    {
        return SharedPreferencesAccessor.getInstance().hasStoredUser(this);
    }
}
