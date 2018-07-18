package com.trip.colleaguesexpmanager.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by my on 12/Apr/2018.
 */

public class Utility {

    public static void fillSpinner(Activity activity, ArrayList<String> stringArrayList, SearchableSpinner spinnerMainProfileType, int selectedPosition) {
        ArrayAdapter<String> dataAdapterProfileTypes = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, stringArrayList);
        dataAdapterProfileTypes.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerMainProfileType.setAdapter(dataAdapterProfileTypes);
        spinnerMainProfileType.setSelection(selectedPosition);

    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String androidDatePickerDateToMySqlDate(String dateInput) {
        String dateString = "";
        try {
            // Parse the input date
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date inputDate = fmt.parse(dateInput);

            // Create the MySQL datetime string
            fmt = new SimpleDateFormat("yyyy-MM-dd");
            dateString = fmt.format(inputDate);
        } catch (Exception ex) {
            Crashlytics.logException(ex);
        }
        return dateString;
    }

    public static String androidMySqlDateToDatePickerDate(String dateInput) {
        String dateString = "";
        try {
            // Parse the input date
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = fmt.parse(dateInput);

            // Create the MySQL datetime string
            fmt = new SimpleDateFormat("dd/MM/yyyy");
            dateString = fmt.format(inputDate);
        } catch (Exception ex) {
            Crashlytics.logException(ex);
        }
        return dateString;
    }

    public static String getTodayDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }

    public static void setErrorOnSpinner(SearchableSpinner spinner, String ErrorMessage) {
        ((TextView) spinner.getSelectedView()).setError(ErrorMessage);
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static void reloadActivityAgain(Activity activity) {
        activity.finish();
        activity.startActivity(activity.getIntent());
    }

    public static boolean checkInternetConnectionExists(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (con_manager.getActiveNetworkInfo() != null && con_manager.getActiveNetworkInfo().isAvailable() && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getVersionNameOfApp(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            return version;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String readJsonFromFile(String fileName, Context context) {
        return readFile(fileName, context);
    }

    public static String readFile(String filename, Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream json = context.getAssets().open(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            in.close();
        } catch (Exception e) {
            //Crashlytics.logException(e);
        }
        return sb.toString();
    }

    public static String getAndroidDeviceIdOfHardwareDevice(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static String getPackageNameOfApplication(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String packageName = pInfo.packageName;
            return packageName;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void callWebserviceForResponse(StringRequest stringRequest, Activity activity) {
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    public static void callWebserviceForResponse(StringRequest stringRequest, Context context) {
        int socketTimeout = 10000;//10 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    // slide the view from below itself to the current position
    public static void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public static void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
}
