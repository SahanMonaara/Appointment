package com.monaara.sahan.testappointment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monaara.sahan.testappointment.adapter.AppointmentAdapter;
import com.monaara.sahan.testappointment.adapter.SearchAdapter;
import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;

public class SearchActivity extends AppCompatActivity {
    TextView dateView,noAppointments;
    LinearLayout errorView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private AppointmentDbHelper dbHelper;
    private SearchAdapter adapter;
    EditText searchThis;
    Button searchThisButton;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final String selectedDate = getIntent().getStringExtra("date");

        dateView =(TextView) findViewById(R.id.dateSelected);
        noAppointments = findViewById(R.id.noAppointmentsAvailable);
        errorView = findViewById(R.id.errorView);
        dateView.setText(selectedDate);
        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.searchList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        searchThis = findViewById(R.id.searchThis);
        searchThisButton = findViewById(R.id.searchThisBtn);
        searchThisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchThis.getText().toString().trim();
                //populate recyclerview
                populaterecyclerView(searchText);
            }
        });


    }
    private void populaterecyclerView(String searchText){

        dbHelper = new AppointmentDbHelper(this);
     /*   if (dbHelper.appointmentList(searchText).isEmpty()){
            noAppointments.setText("No appointments available for \n "+ searchText);
            errorView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

        }
        else {
            errorView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);*/
            adapter = new SearchAdapter(dbHelper.searchAppointments(searchText), this, mRecyclerView);
            mRecyclerView.setAdapter(adapter);
     /*   }
*/
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
