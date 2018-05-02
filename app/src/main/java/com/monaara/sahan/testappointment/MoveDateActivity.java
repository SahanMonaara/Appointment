package com.monaara.sahan.testappointment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;
import com.monaara.sahan.testappointment.model.Appointment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MoveDateActivity extends AppCompatActivity {

    private int receivedAppointmentId;
    private String selectedDate;
    private DatePicker datePicker;
    public AppointmentDbHelper dbHelper;
    private String formatedCurrentDate;
    TextView pickedDate;
    Button moveUpBtn;
    private String formattedDate;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_date);
         selectedDate = getIntent().getStringExtra("date");
        receivedAppointmentId = getIntent().getIntExtra("id", 1);

        datePicker = findViewById(R.id.newDate);
        pickedDate = findViewById(R.id.pickedDate);
        moveUpBtn = findViewById(R.id.moveUpBtn);
        pickedDate.setText(selectedDate);
        datePicker.setSpinnersShown(false);
        datePicker.setCalendarViewShown(true);
        dbHelper = new AppointmentDbHelper(this);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 0, 0, 0);
        Date chosenDate = cal.getTime();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        formatedCurrentDate = df.format(chosenDate);

        moveUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = String.valueOf(datePicker.getDayOfMonth());
                String m = String.valueOf(datePicker.getMonth()+1);
                String y = String.valueOf(datePicker.getYear());
                formattedDate = y+" - "+m+" - "+d;
                if (formatedCurrentDate==selectedDate){
                    new SweetAlertDialog(MoveDateActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("This is the same date.")
                            .setConfirmText("YES")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    moveDateUp(formatedCurrentDate);
                                }
                            })
                            .setCancelText("NO")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                    }
                    else {
                    moveDateUp(formattedDate);
                    new SweetAlertDialog(MoveDateActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success")
                            .setContentText("Event Successfully Updated!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                    Toast.makeText(MoveDateActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void moveDateUp(String receivedDate) {
        dbHelper.updateDateOnly(receivedAppointmentId,receivedDate);

    }
}
