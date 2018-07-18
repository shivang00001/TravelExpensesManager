package com.trip.colleaguesexpmanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.Utility.SquareImageView;
import com.trip.colleaguesexpmanager.activities.TripDetailsActivity;
import com.trip.colleaguesexpmanager.datasources.models.trips.Trip;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

;

public class TripsRecyclerViewAdapter extends RecyclerView.Adapter<TripsRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Trip> tripArrayList;
    private Activity activity;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linearLayoutOuter) LinearLayout linearLayoutOuter;
        @BindView(R.id.squareImageView) SquareImageView squareImageView;
        @BindView(R.id.textViewTitle) TextView textViewTitle;
        @BindView(R.id.textViewAmount) TextView textViewAmount;
        @BindView(R.id.textViewStartDate) TextView textViewStartDate;
        @BindView(R.id.frameLayoutView) FrameLayout frameLayoutView;



        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TripsRecyclerViewAdapter(Context mContext, ArrayList<Trip> tripArrayList, Activity activity) {
        this.mContext = mContext;
        this.tripArrayList = tripArrayList;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_trip, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Trip trip = this.tripArrayList.get(position);
        holder.textViewTitle.setText(trip.getTitle());
        holder.textViewAmount.setText(trip.getTotal_amount());
        holder.textViewStartDate.setText(trip.getStart_date());

        holder.frameLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TripDetailsActivity.class);
                intent.putExtra("trip_title",trip.getTitle());
                intent.putExtra("trip_start_date",trip.getStart_date());
                intent.putExtra("trip_id",trip.getId());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tripArrayList.size();
    }

}