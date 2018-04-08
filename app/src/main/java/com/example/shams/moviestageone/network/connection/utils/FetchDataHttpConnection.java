package com.example.shams.moviestageone.network.connection.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class FetchDataHttpConnection {

    public static final String LOG_TAG = FetchDataHttpConnection.class.getSimpleName();

    private FetchDataHttpConnection(){

    }

    public static URL createUrl(String url){
        //Done
        URL mUrl = null;
        try {
            mUrl = new URL(url);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG , "Invalid URL" );
        }
        return mUrl;
    }

    public static String makeHttpRequest(URL url) throws IOException {

        //Done

        if (url == null){
            return null;
        }

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasNext = scanner.hasNext();
            if (hasNext){
                return scanner.next();
            }else {
                return null;
            }
        }finally {
            httpURLConnection.disconnect();
        }
    }

}
