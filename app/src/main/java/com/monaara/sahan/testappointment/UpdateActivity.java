package com.monaara.sahan.testappointment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;
import com.monaara.sahan.testappointment.model.Appointment;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateActivity extends AppCompatActivity {
    private EditText mTitle;
    private EditText mTime;
    private EditText mDetails;
    private Button updateBtn;
    TextView pickedDate;

    private AppointmentDbHelper dbHelper;
    private int receivedAppointmentId;
    private String receivedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mTitle = (EditText)findViewById(R.id.titleValue);
        mTime = (EditText)findViewById(R.id.timeValue);
        mDetails = (EditText)findViewById(R.id.detailsValue);
        pickedDate = findViewById(R.id.pickedDate);

        updateBtn = (Button)findViewById(R.id.updateBtn);

        dbHelper = new AppointmentDbHelper(this);

            //get intent to get person id
            receivedAppointmentId = getIntent().getIntExtra("id", 1);
            receivedDate = getIntent().getStringExtra("date");
            pickedDate.setText(receivedDate.trim());

        Appointment queriedAppoinment = dbHelper.getAppointment(receivedAppointmentId , receivedDate);
        //set field to this user data
        mTitle.setText(queriedAppoinment.getTitle());
        mTime.setText(queriedAppoinment.getTime());
        mDetails.setText(queriedAppoinment.getDetails());




        //listen to add button click to update
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updatePerson(receivedDate);
                new SweetAlertDialog(UpdateActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success")
                        .setContentText("Event Successfully Updated!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).show();
            }
        });
    }

    private void updatePerson(String receivedDate) {
        String title = mTitle.getText().toString().trim();
        String time = mTime.getText().toString().trim();
        String details = mDetails.getText().toString().trim();



        if(title.isEmpty()){
            //error name is empty
           mTitle.setError("Enter the new event title");
        }

        if(time.isEmpty()){
            //error name is empty
           mTime.setError("Pick a time for the event");
        }



        //create updated appointment
        Appointment updatedAppointment = new Appointment(title, time, receivedDate, details);

        //call dbhelper update
        dbHelper.updateAppointmentRecord(receivedAppointmentId, this, updatedAppointment);

    }
}
