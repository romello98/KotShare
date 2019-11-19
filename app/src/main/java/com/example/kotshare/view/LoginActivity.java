package com.example.kotshare.view;

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
                if(userExistsAndMatches()) {
                    SharedPreferences sharedPreferences = getSharedPreferences(
                            getString(R.string.PREFERENCES_FILE),
                            Context.MODE_PRIVATE
                    );
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Calendar expiringDateTime = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                            getString(R.string.DEFAULT_DATE_FORMAT),
                            Locale.ENGLISH);
                    String storedExpiringTime;

                    expiringDateTime.add(Calendar.DATE, 10);
                    storedExpiringTime = simpleDateFormat.format(expiringDateTime.getTime());
                    editor.putString(getString(R.string.USER_EMAIL), email.getText().toString());
                    editor.putString(getString(R.string.USER_PASSWORD), password.getText().toString());
                    editor.putString(getString(R.string.USER_CREDENTIALS_EXPIRING_TIME), storedExpiringTime);
                    editor.apply();
                    startActivity(intent);
                }
            }
        });
    }

    private boolean userExistsAndMatches()
    {
        //TODO: vérifier que l'utilisateur existe et possède les bons identifiants
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        User matchedUser = userController.getUserByEmail(userEmail);

        return matchedUser != null && userController.isCorrectPassword(matchedUser, userPassword);
    }

    private boolean hasStoredUser()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.PREFERENCES_FILE),
                Context.MODE_PRIVATE
        );
        boolean hasStoredEmail = sharedPreferences.contains(getString(R.string.USER_EMAIL));
        boolean hasStoredPassword = sharedPreferences.contains(getString(R.string.USER_PASSWORD));
        String storedExpiringTime = sharedPreferences.getString(
                getString(R.string.USER_CREDENTIALS_EXPIRING_TIME),
                null);
        Calendar expiringDateTime;
        SimpleDateFormat simpleDateFormat;

        if(!hasStoredEmail || !hasStoredPassword || storedExpiringTime == null) return false;

        expiringDateTime = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(
                getString(R.string.DEFAULT_DATE_FORMAT),
                Locale.ENGLISH
        );
        try {
            expiringDateTime.setTime(simpleDateFormat.parse(storedExpiringTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return expiringDateTime.after(Calendar.getInstance());
    }
}
