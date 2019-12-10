package com.example.kotshare.view.nav.log_out;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kotshare.R;
import com.example.kotshare.view.activities.LoginActivity;

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

        editor.remove(getString(R.string.USER_ID));
        editor.remove(getString(R.string.USER_CREDENTIALS_EXPIRING_TIME));
        editor.apply();
        startActivity(intent);
        return null;
    }
}