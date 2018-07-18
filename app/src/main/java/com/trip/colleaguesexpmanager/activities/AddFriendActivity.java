package com.trip.colleaguesexpmanager.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.Utility.Utility;
import com.trip.colleaguesexpmanager.adapters.SearchUserListRecyclerViewAdapter;
import com.trip.colleaguesexpmanager.datasources.data.UserDataSource;
import com.trip.colleaguesexpmanager.datasources.listeners.WebOperationListener;
import com.trip.colleaguesexpmanager.datasources.models.User.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends AppCompatActivity {

    //region "Binding Controls"
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.linearLayoutFooterLoader) LinearLayout linearLayoutFooterLoader;
    @BindView(R.id.linearLayoutProgressBar) LinearLayout linearLayoutProgressBar;
    @BindView(R.id.linearLayoutProgressContent) LinearLayout linearLayoutProgressContent;
    @BindView(R.id.linearLayoutNoDataFound) LinearLayout linearLayoutNoDataFound;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.relativeLayoutData) RelativeLayout relativeLayoutData;
    //endregion

    //region "Binding Controls of the toolbar"
    @BindView(R.id.editTextSearch) EditText editTextSearch;
    //endregion

    //region "Binding Variables"
    Context context;
    private int currentPage = 0;
    private String search_term = "";
    private String trip_id;
    private String trip_title;
    //endregion

    //region "Overrides"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        context = getApplicationContext();
        ButterKnife.bind(this);

        trip_id = getIntent().getStringExtra("trip_id");
        trip_title = getIntent().getStringExtra("trip_title");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextSearch.setText(search_term);
        editTextChangeListener();
        getSearchResultFromWeb(search_term, "0");


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

    //region "Custom Methods"
    SearchUserListRecyclerViewAdapter searchUserListRecyclerViewAdapter;
    boolean mIsLoading = true;

    private void fillRecyclerView(ArrayList<User> search_resultsArrayList) {

        searchUserListRecyclerViewAdapter = new SearchUserListRecyclerViewAdapter(context, search_resultsArrayList, AddFriendActivity.this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(searchUserListRecyclerViewAdapter);
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
                    getSearchResultFromWeb(search_term.toString(), "1");
                    linearLayoutFooterLoader.setVisibility(View.VISIBLE);
                }

            }
        });
    }
    //endregion

    //region "Custom Events"
    private void editTextChangeListener() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getSearchResultFromWeb(charSequence.toString(), "0");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    //endregion

    //region "Get Data from web"
    ArrayList<User> userDataArrayListLocal = new ArrayList<>();

    private void getSearchResultFromWeb(final String search_term, final String requestType) {
        mIsLoading = true;
        Map<String, String> params = new HashMap<String, String>();
        params.put("page_number", Integer.toString(currentPage));
        params.put("search_term", search_term);
        params.put("app_version", Utility.getVersionNameOfApp(context));
        params.put("device_unique_id", Utility.getAndroidDeviceIdOfHardwareDevice(context));
        UserDataSource.getSearchUsers(params, context, new WebOperationListener<ArrayList<User>, String>() {
            @Override
            public void onReady(final ArrayList<User> userDataArrayList) {
                linearLayoutFooterLoader.setVisibility(View.GONE);

                if (requestType.equalsIgnoreCase("0")) {
                    userDataArrayListLocal = userDataArrayList;
                    if (userDataArrayListLocal.size() > 0) {
                        linearLayoutProgressBar.setVisibility(View.GONE);
                        fillRecyclerView(userDataArrayListLocal);

                    } else {
                        linearLayoutNoDataFound.setVisibility(View.VISIBLE);
                        relativeLayoutData.setVisibility(View.GONE);
                    }
                } else {
                    for (User item : userDataArrayList) {
                        userDataArrayListLocal.add(item);
                    }
                    searchUserListRecyclerViewAdapter.notifyDataSetChanged();
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
}
