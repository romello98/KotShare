package com.example.kotshare.view.nav.logout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kotshare.R;
import com.example.kotshare.view.LoginActivity;
import com.example.kotshare.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LogoutFragment extends Fragment {

    private LogoutViewModel logoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                getString(R.string.PREFERENCES_FILE),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(getString(R.string.USER_EMAIL));
        editor.remove(getString(R.string.USER_PASSWORD));
        editor.remove(getString(R.string.USER_CREDENTIALS_EXPIRING_TIME));
        editor.apply();
        startActivity(intent);
        return null;
    }
}