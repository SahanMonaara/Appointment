package com.monaara.sahan.testappointment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monaara.sahan.testappointment.adapter.DeleteAppointmentAdapter;
import com.monaara.sahan.testappointment.adapter.MoveAdapter;
import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;

public class MoveActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppointmentDbHelper dbHelper;
    private MoveAdapter adapter;
    LinearLayout errorView;
    TextView dateView,noAppointments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        final String selectedDate = getIntent().getStringExtra("date");

        dateView =(TextView) findViewById(R.id.dateSelected);
        noAppointments = findViewById(R.id.noAppointmentsAvailable);
        errorView = findViewById(R.id.errorView);
        dateView.setText(selectedDate);
        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerAppointments);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        populaterecyclerView(selectedDate);
    }
    private void populaterecyclerView(String selectedDate){

        dbHelper = new AppointmentDbHelper(this);
        if (dbHelper.appointmentList(selectedDate).isEmpty()){
            noAppointments.setText("No appointments available for \n "+ selectedDate);
            errorView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

        }
        else {
            errorView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            adapter = new MoveAdapter(dbHelper.appointmentList(selectedDate), this, mRecyclerView);

            mRecyclerView.setAdapter(adapter);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
//        adapter.notifyDataSetChanged();
    }
}
