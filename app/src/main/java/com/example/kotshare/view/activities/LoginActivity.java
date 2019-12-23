package com.example.kotshare.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kotshare.R;
import com.example.kotshare.controller.UserController;
import com.example.kotshare.data_access.services.AuthenticationService;
import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.data_access.services.Token;
import com.example.kotshare.model.Login;
import com.example.kotshare.model.User;
import com.example.kotshare.view.SharedPreferencesAccessor;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserController userController = new UserController();
    private AuthenticationService authenticationService = ServicesConfiguration.getInstance()
            .getRetrofit().create(AuthenticationService.class);


    User foundUser;

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
            loadLoginView();
        } else {
            Token token = SharedPreferencesAccessor.getInstance().getToken();
            ServicesConfiguration.getInstance().setToken(token);
            Call<User> foundUserCall = userController.find(token.getUserId());

            foundUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful())
                    {
                        SharedPreferencesAccessor.getInstance().updateSavedUser(LoginActivity.this,
                                response.body(), token);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        loadLoginView();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    loadLoginView();
                }
            });
        }
    }

    private void loadLoginView()
    {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        defineEvents();
    }

    private void defineEvents()
    {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login login = new Login();
                login.setEmail(email.getText().toString());
                login.setPassword(password.getText().toString());

                new Thread(() ->
                {
                    Call<Token> tokenCall = authenticationService.getToken(login);
                    tokenCall.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if(response.isSuccessful()) {
                            Token token = response.body();
                            ServicesConfiguration.getInstance().setToken(token);
                            Call<User> foundUserCall = userController.find(token.getUserId());

                            foundUserCall.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful())
                                    {
                                        foundUser = response.body();
                                        if (foundUser != null) {
                                            SharedPreferencesAccessor.getInstance().updateSavedUser(LoginActivity.this, foundUser, token);
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    else
                                    {
                                        Log.i("app", response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(LoginActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG);
                                }
                            });
                        }
                        else
                        {
                            Log.i("app", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG);
                    }
                    });
                }).start();
            }
        });
    }

    private boolean hasStoredUser()
    {
        return SharedPreferencesAccessor.getInstance().hasStoredUser(this);
    }
}
