package com.example.kotshare.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.kotshare.R;
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

    private SharedPreferencesAccessor()
    {
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

        this.user = user;

        expiringDateTime.add(Calendar.DATE, 10);
        storedExpiringTime = simpleDateFormat.format(expiringDateTime.getTime());
        editor.putInt(context.getString(R.string.USER_ID), user.getId());
        editor.putString(context.getString(R.string.USER_CREDENTIALS_EXPIRING_TIME), storedExpiringTime);
        editor.apply();
    }

    public boolean hasStoredUser(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.PREFERENCES_FILE),
                Context.MODE_PRIVATE
        );
        boolean hasStoredId = sharedPreferences.contains(context.getString(R.string.USER_ID));
        String storedExpiringTime = sharedPreferences.getString(
                context.getString(R.string.USER_CREDENTIALS_EXPIRING_TIME),
                null);
        Calendar expiringDateTime;
        SimpleDateFormat simpleDateFormat;

        if(!hasStoredId || storedExpiringTime == null) return false;

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

        return expiringDateTime.after(Calendar.getInstance());
    }
}
