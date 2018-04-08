package com.example.shams.moviestageone.network.connection.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by shams on 16/03/18.
 */

public class NetworkStatues {

    public static boolean isConnected(Activity activity){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
