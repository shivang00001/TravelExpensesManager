package com.trip.colleaguesexpmanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.datasources.data.TripsDataSource;
import com.trip.colleaguesexpmanager.datasources.models.User.User;
import com.trip.colleaguesexpmanager.datasources.models.trip_details.Trip_user;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchUserListRecyclerViewAdapter extends RecyclerView.Adapter<SearchUserListRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<User> search_resultsArrayList;
    Activity activity;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewName) TextView textViewName;
        @BindView(R.id.textViewContactNumber) TextView textViewContactNumber;
        @BindView(R.id.textViewEmail) TextView textViewEmail;



        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public SearchUserListRecyclerViewAdapter(Context mContext, ArrayList<User> search_resultsArrayList, Activity activity) {
        this.mContext = mContext;
        this.search_resultsArrayList = search_resultsArrayList;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_search_user_reult_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final User search_results = search_resultsArrayList.get(position);

        holder.textViewName.setText(search_results.getName());
        holder.textViewContactNumber.setText(search_results.getMobile_number());
        holder.textViewEmail.setText(search_results.getEmail());


    }


    @Override
    public int getItemCount() {
        return search_resultsArrayList.size();
    }


}