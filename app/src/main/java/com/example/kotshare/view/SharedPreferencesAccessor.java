package com.example.kotshare.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.kotshare.R;
import com.example.kotshare.data_access.UserDAO;
import com.example.kotshare.data_access.UserDataAccess;
import com.example.kotshare.model.User;
import com.example.kotshare.view.activities.LoginActivity;
import com.example.kotshare.view.activities.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SharedPreferencesAccessor
{
    private static SharedPreferencesAccessor sharedPreferencesAccessor;
    private User user;
    private UserDataAccess userDataAccess;

    private SharedPreferencesAccessor()
    {
        this.userDataAccess = new UserDAO();
    }

    public static SharedPreferencesAccessor getInstance()
    {
        if(sharedPreferencesAccessor == null)
            sharedPreferencesAccessor = new SharedPreferencesAccessor();
        return sharedPreferencesAccessor;
    }

    public User getUser() {
        return user;
    }

    public void updateSavedUser(User user, Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.PREFERENCES_FILE),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Calendar expiringDateTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                context.getString(R.string.DEFAULT_DATE_FORMAT),
                Locale.ENGLISH);
        String storedExpiringTime;

        expiringDateTime.add(Calendar.DATE, 10);
        storedExpiringTime = simpleDateFormat.format(expiringDateTime.getTime());
        editor.putInt(context.getString(R.string.USER_ID), user.getId());
        editor.putString(context.getString(R.string.USER_CREDENTIALS_EXPIRING_TIME), storedExpiringTime);
        editor.apply();
        this.user = user;
    }

    public boolean hasStoredUser(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.PREFERENCES_FILE),
                Context.MODE_PRIVATE
        );
        int storedId = sharedPreferences.getInt(context.getString(R.string.USER_ID),
                -1);
        String storedExpiringTime = sharedPreferences.getString(
                context.getString(R.string.USER_CREDENTIALS_EXPIRING_TIME),
                null);
        Calendar expiringDateTime;
        SimpleDateFormat simpleDateFormat;

        if(storedId == -1 || storedExpiringTime == null) return false;

        expiringDateTime = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(
                context.getString(R.string.DEFAULT_DATE_FORMAT),
                Locale.ENGLISH
        );
        try {
            expiringDateTime.setTime(simpleDateFormat.parse(storedExpiringTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.user = userDataAccess.find(storedId);
        Log.i("app", this.user.toString());

        return expiringDateTime.after(Calendar.getInstance());
    }

    public boolean isCurrentUser(User user)
    {
        boolean validUsers = this.user != null && this.user.getId() != null && user != null;
        return validUsers && this.user.getId().equals(user.getId());
    }
}
