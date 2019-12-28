package com.example.kotshare.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;

import com.example.kotshare.R;
import com.example.kotshare.view.activities.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);
        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }

    public static void showDialog(Activity context, String title, String message)
    {
        context.runOnUiThread(() -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            StringBuilder stringBuilder = new StringBuilder();

            dialog.setTitle(title);
            dialog.setMessage(message);
            dialog.setCancelable(true);
            dialog.create().show();
        });
    }

    public static boolean isConnected(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void alertNoInternetConnection(Context context, View view)
    {
        Snackbar.make(view, context.getString(R.string.error_connection), Snackbar.LENGTH_LONG).show();
    }
}