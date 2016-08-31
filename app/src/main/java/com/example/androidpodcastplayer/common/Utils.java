package com.example.androidpodcastplayer.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.androidpodcastplayer.R;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.jsoup.Jsoup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
    References:
    [1] https://coderanch.com/t/381567/java/java/XML-dateTime-java-Date-SimpleDateFormat
 */

public class Utils {

    private Utils() {
        throw new AssertionError();
    }

    // hide the keyboard on executing search
    public static void hideKeyboard(Activity activity, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbarSticky(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
    }

    // Check that a network connection is available
    public  static boolean isClientConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return  activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void loadPreviewWithGlide(Context context, int drawable, ImageView view) {
        Glide.with(context)
                .load(drawable)
                .crossFade()
                .error(R.drawable.no_image_600x600)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void loadPreviewWithGlide(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .error(R.drawable.no_image_600x600)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static OkHttpClient getsOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(new StethoInterceptor()) // enable network inspection via chrome
                .build();
    }


    public static String dateConverter(String dateTimeString) {
        String formattedDate = null;
        Date date = null;
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss'Z'", Locale.ENGLISH);
        try {
            date = parser.parse(dateTimeString);
            if (date != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                formattedDate = cal.getTime().toString();
            }
        } catch (ParseException e) {
            Timber.e("%s Error parsing date-time string, %s", Constants.LOG_TAG, e.getMessage());
        }
        return formattedDate;
    }


    public static String htmlToStringParser(String input) {
        return Jsoup.parse(input).text();
    }

}
