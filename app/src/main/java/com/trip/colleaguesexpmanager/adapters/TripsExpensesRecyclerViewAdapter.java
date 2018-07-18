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
import com.trip.colleaguesexpmanager.datasources.models.trip_expense.TripExpense;
import com.trip.colleaguesexpmanager.datasources.models.trips.Trip;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

;

public class TripsExpensesRecyclerViewAdapter extends RecyclerView.Adapter<TripsExpensesRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TripExpense> tripExpenseArrayList;
    private Activity activity;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linearLayoutOuter) LinearLayout linearLayoutOuter;
        @BindView(R.id.textViewTitle) TextView textViewTitle;
        @BindView(R.id.textViewTripAmount) TextView textViewTripAmount;
        @BindView(R.id.textViewStartDate) TextView textViewStartDate;
        @BindView(R.id.textViewExpenseBy) TextView textViewExpenseBy;



        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TripsExpensesRecyclerViewAdapter(Context mContext, ArrayList<TripExpense> tripExpenseArrayList, Activity activity) {
        this.mContext = mContext;
        this.tripExpenseArrayList = tripExpenseArrayList;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_trip_expense, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TripExpense trip = this.tripExpenseArrayList.get(position);
        holder.textViewTitle.setText(trip.getTitle());
        holder.textViewTripAmount.setText(trip.getAmount());
        holder.textViewStartDate.setText(trip.getExpense_date());
        holder.textViewExpenseBy.setText(trip.getExpense_by_name());
    }

    @Override
    public int getItemCount() {
        return tripExpenseArrayList.size();
    }

}