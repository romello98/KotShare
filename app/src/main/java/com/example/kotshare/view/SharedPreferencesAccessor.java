package com.example.kotshare.view;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kotshare.R;
import com.example.kotshare.controller.UserController;
import com.example.kotshare.data_access.services.Token;
import com.example.kotshare.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SharedPreferencesAccessor
{
    private static SharedPreferencesAccessor sharedPreferencesAccessor;
    private User user;
    private Token token;
    private UserController userController;

    private SharedPreferencesAccessor()
    {
        this.userController = new UserController();
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

    public Token getToken() {
        return token;
    }

    public void updateSavedUser(Context context, User user, Token token)
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

        expiringDateTime.add(Calendar.SECOND, token.getExpiresIn());
        storedExpiringTime = simpleDateFormat.format(expiringDateTime.getTime());
        editor.putInt(context.getString(R.string.USER_ID), user.getId());
        editor.putString(context.getString(R.string.USER_CREDENTIALS_EXPIRING_TIME), storedExpiringTime);
        editor.putString("user_token", token.getAccessToken());
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
        String accessToken = sharedPreferences.getString(context.getString(R.string.USER_TOKEN), null);
        Calendar expiringDateTime;
        SimpleDateFormat simpleDateFormat;

        if(storedId == -1 || accessToken == null || storedExpiringTime == null) return false;

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

        if(expiringDateTime.after(Calendar.getInstance()))
        {
            this.token = new Token();
            this.token.setAccessToken(accessToken);
            this.token.setUserId(storedId);
            this.token.setExpiresIn((int) ((expiringDateTime.getTimeInMillis()
                    - Calendar.getInstance().getTimeInMillis()) / 60000.));
            return true;
        }
        return false;
    }

    public boolean isCurrentUser(User user)
    {
        boolean validUsers = this.user != null && this.user.getId() != null && user != null;
        return validUsers && this.user.getId().equals(user.getId());
    }

    public void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.PREFERENCES_FILE),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(context.getString(R.string.USER_ID));
        editor.remove(context.getString(R.string.USER_CREDENTIALS_EXPIRING_TIME));
        editor.remove(context.getString(R.string.USER_TOKEN));
        editor.apply();
        this.user = null;
    }
}
