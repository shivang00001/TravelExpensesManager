package com.trip.colleaguesexpmanager.datasources.data;

import android.app.Activity;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.trip.colleaguesexpmanager.Utility.Utility;
import com.trip.colleaguesexpmanager.classes.APIURLS;
import com.trip.colleaguesexpmanager.datasources.listeners.WebOperationListener;
import com.trip.colleaguesexpmanager.datasources.models.trip_details.TripDetails;
import com.trip.colleaguesexpmanager.datasources.models.trip_expense.TripExpense;
import com.trip.colleaguesexpmanager.datasources.models.trips.Trip;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TripsDataSource {

    public static String is_take_give_for_take = "take";
    public static String is_take_give_for_give = "give";

    public static void getTrips(final Map<String, String> params,final Activity activity, WebOperationListener<ArrayList<Trip>, String> listener) {
        final WebOperationListener<ArrayList<Trip>, String> callback = listener;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIURLS.api_getAllTrips,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            result = Utility.readJsonFromFile("trips_data.json", activity);
                            JSONObject objectJson = new JSONObject(result);
                            if (objectJson.has("status")) {
                                if (objectJson.get("status").toString().equalsIgnoreCase("success")) {
                                    Trip[] doctors_data = new Gson().fromJson(objectJson.getJSONArray("trips").toString(), Trip[].class);
                                    ArrayList<Trip> doctors_dataArrayList = new ArrayList<>(Arrays.asList(doctors_data));
                                    callback.onReady(doctors_dataArrayList);
                                } else {
                                    String message = objectJson.get("message").toString();
                                    callback.onError(message);
                                }
                            }
                        } catch (Exception ex) {
                            callback.onError(ex.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError instanceof NoConnectionError) {
                            callback.onError("Internet not found!");
                        } else {
                            String errorMessage = volleyError.getMessage();
                            callback.onError("Some error occurs in get data. Please try again.");
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        Utility.callWebserviceForResponse(stringRequest, activity);
    }

    public static void getTripDetails(final String trip_id,final Activity activity, WebOperationListener<TripDetails, String> listener) {
        final WebOperationListener<TripDetails, String> callback = listener;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIURLS.api_getTripDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            result = Utility.readJsonFromFile("trips_details.json", activity);
                            JSONObject objectJson = new JSONObject(result);
                            if (objectJson.has("status")) {
                                if (objectJson.get("status").toString().equalsIgnoreCase("success")) {
                                    TripDetails tripDetails = new Gson().fromJson(objectJson.getJSONObject("data").toString(), TripDetails.class);
                                    callback.onReady(tripDetails);
                                } else {
                                    String message = objectJson.get("message").toString();
                                    callback.onError(message);
                                }
                            }
                        } catch (Exception ex) {
                            callback.onError(ex.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError instanceof NoConnectionError) {
                            callback.onError("Internet not found!");
                        } else {
                            String errorMessage = volleyError.getMessage();
                            callback.onError("Some error occurs in get data. Please try again.");
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("trip_id", trip_id);
                params.put("app_version", Utility.getVersionNameOfApp(activity.getApplicationContext()));
                params.put("device_unique_id", Utility.getAndroidDeviceIdOfHardwareDevice(activity.getApplicationContext()));
                return params;
            }
        };
        Utility.callWebserviceForResponse(stringRequest, activity);
    }

    public static void getTripExpenses(final Map<String, String> params,final Activity activity, WebOperationListener<ArrayList<TripExpense>, String> listener) {
        final WebOperationListener<ArrayList<TripExpense>, String> callback = listener;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIURLS.api_getTripExpenses,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            result = Utility.readJsonFromFile("trips_expenses.json", activity);
                            JSONObject objectJson = new JSONObject(result);
                            if (objectJson.has("status")) {
                                if (objectJson.get("status").toString().equalsIgnoreCase("success")) {
                                    TripExpense[] tripExpenses = new Gson().fromJson(objectJson.getJSONArray("data").toString(), TripExpense[].class);
                                    ArrayList<TripExpense> tripExpenseArrayList = new ArrayList<>(Arrays.asList(tripExpenses));
                                    callback.onReady(tripExpenseArrayList);
                                } else {
                                    String message = objectJson.get("message").toString();
                                    callback.onError(message);
                                }
                            }
                        } catch (Exception ex) {
                            callback.onError(ex.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError instanceof NoConnectionError) {
                            callback.onError("Internet not found!");
                        } else {
                            String errorMessage = volleyError.getMessage();
                            callback.onError("Some error occurs in get data. Please try again.");
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                return params;
            }
        };
        Utility.callWebserviceForResponse(stringRequest, activity);
    }

}
