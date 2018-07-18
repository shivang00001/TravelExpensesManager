package com.trip.colleaguesexpmanager.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.Utility.Utility;
import com.trip.colleaguesexpmanager.adapters.TripsExpensesRecyclerViewAdapter;
import com.trip.colleaguesexpmanager.datasources.data.TripsDataSource;
import com.trip.colleaguesexpmanager.datasources.listeners.WebOperationListener;
import com.trip.colleaguesexpmanager.datasources.models.trip_expense.TripExpense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllExpensesActivity extends AppCompatActivity {

    //region "Variables"
    Context context;
    private int currentPage = 0;
    private String search_term = "";
    private String trip_id;
    private String trip_title;
    private String trip_total_amount;
    //endregion

    //region "Binding Controls"
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.linearLayoutFooterLoader) LinearLayout linearLayoutFooterLoader;
    @BindView(R.id.linearLayoutProgressBar) LinearLayout linearLayoutProgressBar;
    @BindView(R.id.linearLayoutProgressContent) LinearLayout linearLayoutProgressContent;
    @BindView(R.id.linearLayoutNoDataFound) LinearLayout linearLayoutNoDataFound;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.relativeLayoutData) RelativeLayout relativeLayoutData;
    //endregion

    //region "Overrides"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expenses);

        context = getApplicationContext();
        ButterKnife.bind(this);

        trip_id = getIntent().getStringExtra("trip_id");
        trip_title = getIntent().getStringExtra("trip_title");
        trip_total_amount = getIntent().getStringExtra("trip_total_amount");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Expense Details");
        getSupportActionBar().setSubtitle(trip_title);
        search_term = "";

        getDetailsFromWeb("0");

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

    //region "Webservice to get doctors data"
    ArrayList<TripExpense> tripExpenses_dataArrayList_local;
    private void getDetailsFromWeb(final String requestType) {
        mIsLoading = true;
        Map<String, String> params = new HashMap<String, String>();
        params.put("trip_id", trip_id);
        params.put("search_term", search_term);
        params.put("page_no", Integer.toString(currentPage));
        params.put("app_version", Utility.getVersionNameOfApp(context));
        params.put("device_unique_id", Utility.getAndroidDeviceIdOfHardwareDevice(context));

        TripsDataSource.getTripExpenses(params,AllExpensesActivity.this,new WebOperationListener<ArrayList<TripExpense>, String>() {
            @Override
            public void onReady(final ArrayList<TripExpense> tripExpenseArrayList) {
                linearLayoutFooterLoader.setVisibility(View.GONE);

                if (requestType.equalsIgnoreCase("0")) {
                    tripExpenses_dataArrayList_local = tripExpenseArrayList;
                    if (tripExpenseArrayList.size() > 0) {
                        linearLayoutNoDataFound.setVisibility(View.GONE);
                        relativeLayoutData.setVisibility(View.VISIBLE);
                        linearLayoutProgressBar.setVisibility(View.GONE);
                        fillRecyclerView(tripExpenses_dataArrayList_local);

                    } else {
                        linearLayoutProgressBar.setVisibility(View.GONE);
                        linearLayoutNoDataFound.setVisibility(View.VISIBLE);
                        relativeLayoutData.setVisibility(View.GONE);
                    }
                } else {
                    for (TripExpense item : tripExpenseArrayList) {
                        tripExpenses_dataArrayList_local.add(item);
                    }
                    tripsExpensesRecyclerViewAdapter.notifyDataSetChanged();
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
    TripsExpensesRecyclerViewAdapter tripsExpensesRecyclerViewAdapter;
    boolean mIsLoading = true;
    private void fillRecyclerView( ArrayList<TripExpense> tripExpenseArrayList) {

        tripsExpensesRecyclerViewAdapter = new TripsExpensesRecyclerViewAdapter(context, tripExpenseArrayList,AllExpensesActivity.this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tripsExpensesRecyclerViewAdapter);
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
