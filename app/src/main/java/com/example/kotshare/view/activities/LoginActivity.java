package com.example.kotshare.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kotshare.R;
import com.example.kotshare.controller.UserController;
import com.example.kotshare.data_access.services.AuthenticationService;
import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.data_access.services.Token;
import com.example.kotshare.exceptions.NoConnectivityException;
import com.example.kotshare.model.Login;
import com.example.kotshare.model.User;
import com.example.kotshare.model.UserForm;
import com.example.kotshare.utils.Validator;
import com.example.kotshare.view.SharedPreferencesAccessor;
import com.example.kotshare.view.Utils;
import com.example.kotshare.view.fragments.LoginFragment;
import com.example.kotshare.view.fragments.SignupFragment;

import java.io.IOException;
import java.util.ArrayList;

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

    @BindView(R.id.actionButton)
    Button actionButton;

    @BindView(R.id.switchButton)
    Button switchButton;

    @BindView(R.id.formView)
    FrameLayout formView;

    private LoginFragment loginFragment;
    private SignupFragment signupFragment;

    private View.OnClickListener onLoginButtonPressed;
    private View.OnClickListener onSignupButtonPressed;
    private boolean isLoginFormDisplayed = true;

    private Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!hasStoredUser()) {
            loadLoginView();
        } else {
            token = SharedPreferencesAccessor.getInstance().getToken();
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
                    else loadLoginView();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    if(!Utils.isConnected(LoginActivity.this))
                        Utils.alertNoInternetConnection(LoginActivity.this,
                                getCurrentFocus());
                    else
                        Utils.showDialog(LoginActivity.this, getString(R.string.error_unknown),
                                getString(R.string.error_unknown));
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
        onLoginButtonPressed = view -> {
            if(!Utils.isConnected(this)) {
                Utils.alertNoInternetConnection(this, view);
                return;
            }

            Login login = loginFragment.getLoginForm();
            Thread tokenThread = new Thread(() -> {
                Call<Token> tokenCall = authenticationService.getToken(login);
                try {
                    Response<Token> response = tokenCall.execute();
                    if(response.isSuccessful()) {
                        token = response.body();
                        ServicesConfiguration.getInstance().setToken(token);
                    }
                    else
                    {
                        Log.i("app", response.message());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(!Utils.isConnected(LoginActivity.this))
                        Utils.alertNoInternetConnection(LoginActivity.this, view);
                    else
                        Utils.showDialog(LoginActivity.this, getString(R.string.error_unknown),
                                getString(R.string.error_unknown));
                }
            });
            tokenThread.start();

            try {
                tokenThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(token != null) {
                if(Utils.isConnected(this))
                    new Thread(() -> {
                    Call<User> foundUserCall = userController.find(token.getUserId());
                    try {
                        Response<User> response = foundUserCall.execute();
                        if (response.isSuccessful()) {
                            foundUser = response.body();
                            if (foundUser != null) {
                                SharedPreferencesAccessor.getInstance().updateSavedUser(
                                        LoginActivity.this, foundUser, token);
                                Intent intent = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Log.i("app", response.message());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if(!Utils.isConnected(LoginActivity.this))
                            Utils.alertNoInternetConnection(this, view);
                        else
                            Utils.showDialog(LoginActivity.this, getString(R.string.error_unknown),
                                    getString(R.string.error_unknown));
                    }
                }).start();
                else
                    Utils.alertNoInternetConnection(this, view);
            }
        };

        onSignupButtonPressed = view -> {
            UserForm userForm = signupFragment.getUserForm();
            ArrayList<String> errors = Validator.getInstance(LoginActivity.this)
                    .validateForm(userForm, signupFragment.getPasswordConfirmation());

            if(!errors.isEmpty())
            {
                StringBuilder stringBuilder = new StringBuilder();
                String dialogTitle = getString(R.string.errors_title);

                for(int i = 0; i < errors.size() - 1; i++)
                    stringBuilder.append(errors.get(i) +"\n\n");
                stringBuilder.append(errors.get(errors.size() - 1));

                Utils.showDialog(LoginActivity.this, dialogTitle,
                        stringBuilder.toString());
            }
            else
            {
                if(Utils.isConnected(this))
                    new Thread(() -> {
                    Call<User> call = userController.signup(userForm);
                    try {
                        Response<User> response = call.execute();
                        if(response.isSuccessful())
                            Utils.showDialog(LoginActivity.this, getString(R.string.success),
                                    getString(R.string.success_account_creating));
                        else
                            Utils.showDialog(LoginActivity.this, getString(R.string.error_request),
                                    getString(R.string.error_request_client));
                    } catch (IOException e) {
                        e.printStackTrace();
                        if(e instanceof NoConnectivityException)
                            Utils.showDialog(LoginActivity.this, getString(R.string.error_network),
                                    getString(R.string.error_connection));
                        else
                            Utils.showDialog(LoginActivity.this, getString(R.string.error_unknown),
                                    getString(R.string.error_unknown_happened));
                    }
                }).start();
                else
                    Utils.alertNoInternetConnection(this, view);
            }
        };

        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();
        actionButton.setOnClickListener(onLoginButtonPressed);
        switchButton.setOnClickListener(this::switchForm);
        switchToFragment(loginFragment);
    }

    private void switchForm(View view)
    {
        if(isLoginFormDisplayed)
        {
            actionButton.setText(R.string.signup);
            actionButton.setOnClickListener(onSignupButtonPressed);
            switchButton.setText(R.string.log_in);
            switchToFragment(signupFragment);
        }
        else
        {
            actionButton.setText(R.string.log_in);
            actionButton.setOnClickListener(onLoginButtonPressed);
            switchButton.setText(R.string.signup);
            switchToFragment(loginFragment);
        }
        isLoginFormDisplayed = !isLoginFormDisplayed;
    }

    private void switchToFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(formView.getId(), fragment).commit();
    }

    private boolean hasStoredUser()
    {
        return SharedPreferencesAccessor.getInstance().hasStoredUser(this);
    }
}
