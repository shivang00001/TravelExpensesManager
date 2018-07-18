package com.trip.colleaguesexpmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.trip.colleaguesexpmanager.FragmentActionList;
import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.Utility.ExpandableHeightListView;
import com.trip.colleaguesexpmanager.adapters.ExpandableTripUserListAdapter;
import com.trip.colleaguesexpmanager.datasources.data.TripsDataSource;
import com.trip.colleaguesexpmanager.datasources.listeners.WebOperationListener;
import com.trip.colleaguesexpmanager.datasources.models.trip_details.TripDetails;
import com.trip.colleaguesexpmanager.datasources.models.trip_details.Trip_user;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity {

    //region "Binding Controls"
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.linearLayoutProgressBar) LinearLayout linearLayoutProgressBar;
    @BindView(R.id.linearLayoutContent) LinearLayout linearLayoutContent;
    @BindView(R.id.expandableHeightListUsers) ExpandableHeightListView expandableHeightListUsers;
    @BindView(R.id.buttonAddUser) Button buttonAddUser;

    //endregion

    //region "Binding Variables"
    private Context context;
    FragmentActionList fragment_action_list;
    private String trip_title;
    private String trip_start_date;
    private String trip_id;
    //endregion

    //region "Overrides"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        context = getApplicationContext();
        ButterKnife.bind(this);

        trip_title = getIntent().getStringExtra("trip_title");
        trip_start_date = getIntent().getStringExtra("trip_start_date");
        trip_id = getIntent().getStringExtra("trip_id");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(trip_title);
        getSupportActionBar().setSubtitle(trip_start_date);
        fragment_action_list = (FragmentActionList) getSupportFragmentManager().findFragmentById(R.id.fragment_action_list);
        getDetailsFromWeb();
        onClickButtonAddUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region "Webservice to get model profile data"
    private void getDetailsFromWeb() {

        TripsDataSource.getTripDetails(trip_id, TripDetailsActivity.this, new WebOperationListener<TripDetails, String>() {
            @Override
            public void onReady(TripDetails tripDetails) {
                linearLayoutProgressBar.setVisibility(View.GONE);
                fillTripUsers(tripDetails.getTrip_user());
                fillActionFragment(tripDetails);

            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region "Fill Users"
    ArrayList<Trip_user> trip_userArrayList;
    ExpandableTripUserListAdapter expandableTripUserListAdapter;

    private void fillTripUsers(Trip_user[] trip_users) {

        trip_userArrayList = new ArrayList<>(Arrays.asList(trip_users));
        if (trip_users.length > 0) {

            expandableTripUserListAdapter = new ExpandableTripUserListAdapter(context, R.layout.recycler_view_trip_user, trip_userArrayList, TripDetailsActivity.this);
            expandableHeightListUsers.setExpanded(true);
            expandableHeightListUsers.setAdapter(expandableTripUserListAdapter);


        }
    }
    //endregion

    //region "Fill Options"
    private void fillActionFragment(final TripDetails tripDetails) {
        fragment_action_list.linearLayoutHeading.setVisibility(View.GONE);

        fragment_action_list.linearLayoutItem1.setVisibility(View.VISIBLE);
        fragment_action_list.textViewFieldItem1.setText("Total(" + tripDetails.getTotal_amount() + ")");
        fragment_action_list.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.total));
        fragment_action_list.linearLayoutSeprator1.setVisibility(View.VISIBLE);

        fragment_action_list.linearLayoutItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripDetailsActivity.this, AllExpensesActivity.class);
                intent.putExtra("trip_id", tripDetails.getId());
                intent.putExtra("trip_title", tripDetails.getTitle());
                intent.putExtra("trip_total_amount", tripDetails.getTotal_amount());
                startActivity(intent);
            }
        });

        /*fragment_action_list.linearLayoutItem2.setVisibility(View.VISIBLE);
        fragment_action_list.textViewFieldItem2.setText("Expense Summary");
        fragment_action_list.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.expense_summary));
        fragment_action_list.linearLayoutSeprator2.setVisibility(View.VISIBLE);*/

        fragment_action_list.linearLayoutItem3.setVisibility(View.VISIBLE);
        fragment_action_list.textViewFieldItem3.setText("Edit Details");
        fragment_action_list.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.edit));
        fragment_action_list.linearLayoutItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripDetailsActivity.this, AddTripActivity.class);
                intent.putExtra("trip_id", tripDetails.getId());
                startActivity(intent);
            }
        });

    }
    //endregion

    //region "Custom Events"
    private void onClickButtonAddUser() {
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripDetailsActivity.this,AddFriendActivity.class);
                startActivity(intent);
            }
        });
    }
    //endregion
}
