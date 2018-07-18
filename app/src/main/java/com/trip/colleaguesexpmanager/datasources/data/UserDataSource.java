package com.trip.colleaguesexpmanager.datasources.data;

import android.content.Context;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.trip.colleaguesexpmanager.Utility.Utility;
import com.trip.colleaguesexpmanager.classes.APIURLS;
import com.trip.colleaguesexpmanager.datasources.listeners.WebOperationListener;
import com.trip.colleaguesexpmanager.datasources.models.User.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDataSource {

    public static void getSearchUsers(final Map<String, String> params,final Context context, WebOperationListener<ArrayList<User>, String> listener) {
        final WebOperationListener<ArrayList<User>, String> callback = listener;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIURLS.api_searchUsers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            result = Utility.readJsonFromFile("add_users.json", context);
                            JSONObject objectJson = new JSONObject(result);
                            if (objectJson.has("status")) {
                                if (objectJson.get("status").toString().equalsIgnoreCase("success")) {
                                    User[] userData = new Gson().fromJson(objectJson.get("users").toString(), User[].class);
                                    ArrayList<User> userDataArrayList = new ArrayList<>();
                                    for (User item : userData) {
                                        userDataArrayList.add(item);
                                    }
                                    callback.onReady(userDataArrayList);
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
                            callback.onError("Some error occurs in get data. Please try again.");
                        }

                    }


                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        Utility.callWebserviceForResponse(stringRequest, context);
    }



}
