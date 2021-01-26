package com.example.td1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class ImageFromURL extends AsyncTask<String, Void, Object[]> {

    ActivityWaitingImage activity;
    WeakReference<Context> weakContext;

    public ImageFromURL(ActivityWaitingImage activity, Context context) {
        this.activity = activity;
        this.weakContext = new WeakReference<>(context);
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
            Context context = weakContext.get();
            img = getBitmapFromVectorDrawable(context, R.drawable.ic_male_clothes);
        }
        return new Object[]{img, urlAndIndex[1]};
    }

    @Override
    protected void onPostExecute(Object[] result) {
        this.activity.getImage(result);
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
