package com.example.td1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public class ImageFromURL extends AsyncTask<String, Void, Object[]> {

    ActivityWaitingImage activity;

    public ImageFromURL(ActivityWaitingImage activity) {
        this.activity = activity;
    }

    @Override
    protected Object[] doInBackground(String... urlAndIndex) {

        String urlImage = urlAndIndex[0];
        Bitmap img = null;
        try {
            InputStream in = new URL(urlImage).openStream();
            img = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e) {
            Log.e("Pas d'image", "penser à utiliser une image générique");
        }
        return new Object[]{img, urlAndIndex[1]};
    }

    @Override
    protected void onPostExecute(Object[] result) {
        this.activity.getImage(result);
    }
}
