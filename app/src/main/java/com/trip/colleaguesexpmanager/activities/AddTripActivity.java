package com.trip.colleaguesexpmanager.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trip.colleaguesexpmanager.R;
import com.trip.colleaguesexpmanager.Utility.Utility;
import com.trip.colleaguesexpmanager.datasources.data.TripsDataSource;
import com.trip.colleaguesexpmanager.datasources.listeners.WebOperationListener;
import com.trip.colleaguesexpmanager.datasources.models.trip_details.TripDetails;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTripActivity extends AppCompatActivity {

    //region "Variables"
    Context context;
    DatePickerDialog.OnDateSetListener date_of_trip_dateListener;
    private String trip_id = "";
    //endregion

    //region "Binding Controls"
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editTextTripTitle) EditText editTextTripTitle;
    @BindView(R.id.editTextDateOfTrip) EditText editTextDateOfTrip;
    @BindView(R.id.editTextTripDescription) EditText editTextTripDescription;
    @BindView(R.id.buttonSaveTrip) Button buttonSaveTrip;
    //endregion

    //region "Overrides"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        context = getApplicationContext();
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().hasExtra("trip_id")) {
            //means Edit Form
            trip_id = getIntent().getStringExtra("trip_id");
            toolbar.setTitle("Edit Trip");
            fillFormDetails();
        } else {
            //means add new form
            toolbar.setTitle("Add Trip");
        }

        onClickEditTextDateOfTrip();
        date_of_trip_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                String dateCalendar = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                String date = Utility.androidDatePickerDateToMySqlDate(dateCalendar);
                editTextDateOfTrip.setText(date);
            }
        };

        onClickButtonSaveTrip();
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

    //region "Custom Events"
    private void onClickButtonSaveTrip() {
        buttonSaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {

                }
            }
        });
    }

    private void onClickEditTextDateOfTrip() {
        editTextDateOfTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(date_of_trip_dateListener,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }
    //endregion

    //region "Custom Methods"
    public boolean validateForm() {
        //required Edit text
        boolean returnValue = true;
        if (editTextTripTitle.getText().toString().trim().length() == 0) {
            returnValue = false;
            editTextTripTitle.setError("required!");
        }

        if (editTextDateOfTrip.getText().toString().trim().length() < 10) {
            returnValue = false;
            editTextDateOfTrip.setError("Must be at least 10 digit!");
        }

        return returnValue;
    }

    private void fillFormDetails()
    {
        TripsDataSource.getTripDetails(trip_id, AddTripActivity.this, new WebOperationListener<TripDetails, String>() {
            @Override
            public void onReady(TripDetails tripDetails) {
                editTextTripTitle.setText(tripDetails.getTitle());
                editTextDateOfTrip.setText(tripDetails.getStart_date());
                editTextTripDescription.setText(tripDetails.getDescription());
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion
}
