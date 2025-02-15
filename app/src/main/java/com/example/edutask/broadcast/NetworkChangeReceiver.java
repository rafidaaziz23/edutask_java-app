package com.example.edutask.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.edutask.MainActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private MainActivity mainActivity;

    public NetworkChangeReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isConnected(context)) {
            mainActivity.dismissNoInternetDialog(); // Tutup popup jika ada internet
        } else {
            mainActivity.showNoInternetDialog(); // Tampilkan popup jika tidak ada internet
        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}
