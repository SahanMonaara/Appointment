package com.monaara.sahan.testappointment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monaara.sahan.testappointment.adapter.AppointmentAdapter;
import com.monaara.sahan.testappointment.adapter.DeleteAppointmentAdapter;
import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;
import com.monaara.sahan.testappointment.model.Appointment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DeleteActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppointmentDbHelper dbHelper;
    private DeleteAppointmentAdapter adapter;

    LinearLayout errorView;
    EditText deleteAppointment;
    Button deleteThisBtn;
    public int deleteThis;


    TextView dateView,noAppointments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        final String selectedDate = getIntent().getStringExtra("date");

        dateView =(TextView) findViewById(R.id.dateSelected);
        noAppointments = findViewById(R.id.noAppointmentsAvailable);
        errorView = findViewById(R.id.errorView);
        deleteAppointment =(EditText) findViewById(R.id.deletePosition);
        deleteThisBtn = (Button)findViewById(R.id.deleteThisBtn);


        dateView.setText(selectedDate);
        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.deleteRecyclerAppointments);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        deleteThisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteThis = Integer.parseInt(deleteAppointment.getText().toString().trim());

         /*       new SweetAlertDialog(DeleteActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Delete!")
                        .setContentText("Would you like to delete this event ")
                        .setConfirmText("YES")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                populaterecyclerView(selectedDate , deleteThis);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("NO")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();

                            }
                        })
                        .show();*/
                populaterecyclerView(selectedDate , deleteThis);
               /* adapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(adapter);*/

            }
        });

         //populate recyclerview
        populaterecyclerView(selectedDate , deleteThis);
    }


    private void populaterecyclerView(final String selectedDate, int deleteThis){

        dbHelper = new AppointmentDbHelper(this);
        if (dbHelper.appointmentList(selectedDate).isEmpty()){
            noAppointments.setText("No appointments available for \n "+ selectedDate);
            errorView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            deleteThisBtn.setVisibility(View.GONE);
            deleteAppointment.setVisibility(View.GONE);

        }
        else {
            errorView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            deleteThisBtn.setVisibility(View.VISIBLE);
            deleteAppointment.setVisibility(View.VISIBLE);
            //calling adapter to view all the available appointments to delete
            adapter = new DeleteAppointmentAdapter(dbHelper.appointmentList(selectedDate), this, mRecyclerView, deleteThis);
            mRecyclerView.setAdapter(adapter);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
//       adapter.notifyDataSetChanged();
    }
}
