package com.trip.colleaguesexpmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.Utility.Utility;
import com.trip.colleaguesexpmanager.adapters.DrawerNavigationMenuListAdapter;
import com.trip.colleaguesexpmanager.adapters.TripsRecyclerViewAdapter;
import com.trip.colleaguesexpmanager.classes.Constants;
import com.trip.colleaguesexpmanager.classes.NavItem;
import com.trip.colleaguesexpmanager.datasources.data.TripsDataSource;
import com.trip.colleaguesexpmanager.datasources.listeners.WebOperationListener;
import com.trip.colleaguesexpmanager.datasources.models.trips.Trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {



    //region "Binding Controls"
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.linearLayoutFooterLoader) LinearLayout linearLayoutFooterLoader;
    @BindView(R.id.linearLayoutProgressBar) LinearLayout linearLayoutProgressBar;
    @BindView(R.id.linearLayoutNoDataFound) LinearLayout linearLayoutNoDataFound;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.relativeLayoutData) RelativeLayout relativeLayoutData;
    //endregion

    //region "Biding Controls of Navigation Drawer"
    @BindView(R.id.imageViewUserImage) CircleImageView imageViewUserImage;
    @BindView(R.id.textViewUserName) TextView textViewUserName;
    @BindView(R.id.textViewEditProfile) TextView textViewEditProfile;
    //endregion

    //region "Binding Variables"
    Context context;
    private int currentPage = 0;
    private String search_term = "";
    //endregion

    //region "Overrides"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        context = getApplicationContext();

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //make drawer open on the home button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer_from_left, R.string.close_drawer_to_left);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        fillBottomListViewDrawer();
        onClickFab();
        getDetailsFromWeb("0");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region "Drawer Items"
    @BindView(R.id.navList) ListView navList;
    ArrayList<NavItem> navItems = new ArrayList<>();
    DrawerNavigationMenuListAdapter drawerNavigationMenuListAdapter;

    private void fillBottomListViewDrawer() {

        navItems.add(new NavItem(Constants.homeItemsShareApp, "", "hide", R.drawable.share));
        navItems.add(new NavItem(Constants.homeItemsRateUs, "", "hide", R.drawable.star));
        navItems.add(new NavItem(Constants.homeItemsContactUs, "", "hide", R.drawable.email));
        navItems.add(new NavItem(Constants.homeItemsLogut, "", "hide", R.drawable.logout));

        this.drawerNavigationMenuListAdapter = new DrawerNavigationMenuListAdapter(getApplicationContext(), navItems);
        navList.setAdapter(drawerNavigationMenuListAdapter);

        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });


    }

    private void selectItemFromDrawer(int position) {

       /* if (navItems.get(position).mTitle == Constants.homeItemsMenuProfile) {
            drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        }*/

    }

    //endregion

    //region "Custom Events"
    private void onClickFab()
    {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTripActivity.class);
                startActivity(intent);
            }
        });
    }
    //endregion

    //region "Webservice to get doctors data"
    ArrayList<Trip> trips_dataArrayList_local;
    private void getDetailsFromWeb(final String requestType) {
        mIsLoading = true;
        Map<String, String> params = new HashMap<String, String>();
        params.put("search_term", search_term);
        params.put("page_no", Integer.toString(currentPage));
        params.put("app_version", Utility.getVersionNameOfApp(context));
        params.put("device_unique_id", Utility.getAndroidDeviceIdOfHardwareDevice(context));

        TripsDataSource.getTrips(params,MainActivity.this,new WebOperationListener<ArrayList<Trip>, String>() {
            @Override
            public void onReady(final ArrayList<Trip> doctors_dataArrayList) {
                linearLayoutFooterLoader.setVisibility(View.GONE);

                if (requestType.equalsIgnoreCase("0")) {
                    trips_dataArrayList_local = doctors_dataArrayList;
                    if (doctors_dataArrayList.size() > 0) {
                        linearLayoutNoDataFound.setVisibility(View.GONE);
                        relativeLayoutData.setVisibility(View.VISIBLE);
                        linearLayoutProgressBar.setVisibility(View.GONE);
                        fillRecyclerView(trips_dataArrayList_local);

                    } else {
                        linearLayoutProgressBar.setVisibility(View.GONE);
                        linearLayoutNoDataFound.setVisibility(View.VISIBLE);
                        relativeLayoutData.setVisibility(View.GONE);
                    }
                } else {
                    for (Trip item : doctors_dataArrayList) {
                        trips_dataArrayList_local.add(item);
                    }
                    tripsRecyclerViewAdapter.notifyDataSetChanged();
                }
                mIsLoading = false;
                currentPage++;
            }

            @Override
            public void onError(String error) {
                mIsLoading = false;
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    //endregion

    //region "Custom Methods"
    TripsRecyclerViewAdapter tripsRecyclerViewAdapter;
    boolean mIsLoading = true;
    private void fillRecyclerView( ArrayList<Trip> doctors_dataArrayList) {

        tripsRecyclerViewAdapter = new TripsRecyclerViewAdapter(context, doctors_dataArrayList,MainActivity.this);
        final GridLayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tripsRecyclerViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading) {
                    return;
                }
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    getDetailsFromWeb("1");
                    linearLayoutFooterLoader.setVisibility(View.VISIBLE);
                }

            }
        });
    }
    //endregion

}
