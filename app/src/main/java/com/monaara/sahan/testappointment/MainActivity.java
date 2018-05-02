package com.monaara.sahan.testappointment;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.Toast;

import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private Button createButton;
    private Button moveButton;
    private Button deleteButton;
    private Button viewButton;
    private Button searchButton;
    private AppointmentDbHelper dbHelper;

    DatePicker datePicker;
    public String formattedDate;
    public String formatedCurrentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = (Button) findViewById(R.id.createBtn);
        moveButton = (Button) findViewById(R.id.moveBtn);
        deleteButton = (Button) findViewById(R.id.deleteBtn);
        viewButton = (Button) findViewById(R.id.viewBtn);
        searchButton = findViewById(R.id.searchtn);
        datePicker = findViewById(R.id.datepicker);
        datePicker.setSpinnersShown(false);
        datePicker.setCalendarViewShown(true);
        dbHelper = new AppointmentDbHelper(this);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 0, 0, 0);
        Date chosenDate = cal.getTime();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        formatedCurrentDate = df.format(chosenDate);


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String d = String.valueOf(datePicker.getDayOfMonth());
               String m = String.valueOf(datePicker.getMonth()+1);
               String y = String.valueOf(datePicker.getYear());
               formattedDate = y+" - "+m+" - "+d;

                Toast.makeText(MainActivity.this, formattedDate, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                if (formattedDate == null) {
                    intent.putExtra("date", formatedCurrentDate);
                } else {
                    intent.putExtra("date", formattedDate);
                }
                startActivity(intent);
            }
        });
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = String.valueOf(datePicker.getDayOfMonth());
                String m = String.valueOf(datePicker.getMonth()+1);
                String y = String.valueOf(datePicker.getYear());
                formattedDate = y+" - "+m+" - "+d;

                Intent intent = new Intent(MainActivity.this, MoveActivity.class);
                if (formattedDate == null) {
                    intent.putExtra("date", formatedCurrentDate);
                } else {
                    intent.putExtra("date", formattedDate);
                }
                startActivity(intent);
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = String.valueOf(datePicker.getDayOfMonth());
                String m = String.valueOf(datePicker.getMonth()+1);
                String y = String.valueOf(datePicker.getYear());
                formattedDate = y+" - "+m+" - "+d;

                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                if (formattedDate == null) {
                    intent.putExtra("date", formatedCurrentDate);
                } else {
                    intent.putExtra("date", formattedDate);
                }
                startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = String.valueOf(datePicker.getDayOfMonth());
                String m = String.valueOf(datePicker.getMonth()+1);
                String y = String.valueOf(datePicker.getYear());
                formattedDate = y+" - "+m+" - "+d;

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this appointments")
                        .setConfirmText("Yes,delete all")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                if (formattedDate == null) {
                                    dbHelper.deleteAllAppointmentRecords(formatedCurrentDate);
                                    sDialog.setTitleText("Deleted!")
                                            .setContentText("Your appointments has been deleted!")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                } else {
                                    dbHelper.deleteAllAppointmentRecords(formattedDate);
                                    sDialog.setTitleText("Deleted!")
                                            .setContentText("Your appointments has been deleted!")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            }
                        })
                        .setCancelText("Select")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                                if (formattedDate == null) {
                                    intent.putExtra("date", formatedCurrentDate);
                                } else {
                                    intent.putExtra("date", formattedDate);
                                }
                                startActivity(intent);
                            }
                        })
                        .show();


            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = String.valueOf(datePicker.getDayOfMonth());
                String m = String.valueOf(datePicker.getMonth()+1);
                String y = String.valueOf(datePicker.getYear());
                formattedDate = y+" - "+m+" - "+d;

                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                if (formattedDate == null) {
                    intent.putExtra("date", formatedCurrentDate);
                } else {
                    intent.putExtra("date", formattedDate);
                }
                startActivity(intent);
            }
        });
        /*Calendar calendar = Calendar.getInstance();

        datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(0);
                        cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                        Date chosenDate = cal.getTime();

                        // Format the date
                        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
                        formattedDate = df.format(chosenDate);

                        Toast.makeText(MainActivity.this, formattedDate, Toast.LENGTH_SHORT).show();


                    }
                }
                );*/

    }


}
