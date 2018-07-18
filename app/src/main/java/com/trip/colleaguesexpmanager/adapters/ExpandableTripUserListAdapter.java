package com.trip.colleaguesexpmanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.datasources.data.TripsDataSource;
import com.trip.colleaguesexpmanager.datasources.models.trip_details.Trip_user;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableTripUserListAdapter extends ArrayAdapter<Trip_user> {
    private Context mContext;
    private ArrayList<Trip_user> mList;
    private LayoutInflater mInflater;
    Activity activity;


    public ExpandableTripUserListAdapter(Context context, int resourceId, ArrayList<Trip_user> top_specialization_dataArrayList, Activity activity) {
        super(context,resourceId,top_specialization_dataArrayList);
        mContext = context;
        this.mList = top_specialization_dataArrayList;
        this.activity = activity;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    /*private view holder class*/
    public class ViewHolder {

        @BindView(R.id.linearLayoutOuter) LinearLayout linearLayoutOuter;
        @BindView(R.id.textViewUserName) TextView textViewUserName;
        @BindView(R.id.textViewAmount) TextView textViewAmount;
        @BindView(R.id.textViewShareAmountLeft) TextView textViewShareAmountLeft;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }


    ViewHolder holder = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.recycler_view_trip_user, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewUserName.setText(mList.get(position).getName());
        holder.textViewAmount.setText(mList.get(position).getTotal_amount_spent_by_user());
        if(mList.get(position).getIs_give_take().equalsIgnoreCase(TripsDataSource.is_take_give_for_give))
        {
            holder.textViewShareAmountLeft.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.textViewShareAmountLeft.setText("( -"+mList.get(position).getAmount_left_or_take_trip()+")");
        }
        else if(mList.get(position).getIs_give_take().equalsIgnoreCase(TripsDataSource.is_take_give_for_take))
        {
            holder.textViewShareAmountLeft.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.textViewShareAmountLeft.setText("( +"+mList.get(position).getAmount_left_or_take_trip()+")");
        }

        return convertView;
    }

}
