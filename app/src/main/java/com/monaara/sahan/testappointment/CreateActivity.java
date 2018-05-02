package com.monaara.sahan.testappointment;

import android.app.TimePickerDialog;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;
import com.monaara.sahan.testappointment.model.Appointment;
import com.monaara.sahan.testappointment.model.Feed;

import java.util.ArrayList;
import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "CreateAvtivity";
    EditText titleValue, detailsValue;
    EditText timeValue, synonyms;
    Button saveBtn, thesaurus,thesarus2;
    TextView pickedDate;
    AppointmentDbHelper appointmentDbHelper;
    private int mHour;
    private int mMinute;
    private String mtime;
    String BASE_URL = "http://thesaurus.altervista.org/thesaurus/";
    private String enteredWord;
    final String key = "yfqgYXC2CXZFxHQFrEEG";
    final String language = "en_US";
    final String output = "json";
    public ListView listview;
    public String array = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        titleValue = findViewById(R.id.titleValue);
        timeValue = findViewById(R.id.timeValue);
        detailsValue = findViewById(R.id.detailsValue);
        saveBtn = findViewById(R.id.saveBtn);
        pickedDate = findViewById(R.id.pickedDate);
        thesaurus = findViewById(R.id.thesaurusButton);
        synonyms = findViewById(R.id.synonyms);
        listview = findViewById(R.id.listView);

        thesarus2 = findViewById(R.id.thesaurusButton2);
        //selected text synonyms in synonyms list
        thesarus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startSelection=detailsValue.getSelectionStart();
                int endSelection=detailsValue.getSelectionEnd();
                String selectedText =  detailsValue.getText().toString().substring(startSelection,endSelection);
                if (!selectedText.isEmpty()){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Api.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Api api = retrofit.create(Api.class);
                    Call<Feed> call = api.getData(selectedText, key, language, output);

                    call.enqueue(new Callback<Feed>() {
                        @Override
                        public void onResponse(Call<Feed> call, Response<Feed> response) {
                            Log.d(TAG, "onResponse: server response " + response.toString());
                            ArrayList<com.monaara.sahan.testappointment.model.Response.Response> responseArrayList = response.body().getResponse();
                            for (int i = 0; i < responseArrayList.size(); i++) {
                                array = array + responseArrayList.get(i).getList().getSynonyms();
                            }
                            String[] splitedSynonyms = array.split("\\|");
                            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(splitedSynonyms));
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_list_item_1, arrayList);
                            listview.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                            listview.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<Feed> call, Throwable t) {

                            new SweetAlertDialog(CreateActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Word Not found!!")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                            onResume();

                        }
                    });
                }
                else {
                    new SweetAlertDialog(CreateActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Error")
                            .setContentText("Select a Word")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }


            }
        });
        //synonyms to entered word
        thesaurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredWord = synonyms.getText().toString().trim();
                if (!enteredWord.isEmpty())
                {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Api.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Api api = retrofit.create(Api.class);
                    Call<Feed> call = api.getData(enteredWord, key, language, output);

                    call.enqueue(new Callback<Feed>() {
                        @Override
                        public void onResponse(Call<Feed> call, Response<Feed> response) {
                            Log.d(TAG, "onResponse: server response " + response.toString());
                            ArrayList<com.monaara.sahan.testappointment.model.Response.Response> responseArrayList = response.body().getResponse();
                            for (int i = 0; i < responseArrayList.size(); i++) {
                                array = array + responseArrayList.get(i).getList().getSynonyms();
                            }
                            //spliting the string to pass to listview
                            String[] splitedSynonyms = array.split("\\|");
                            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(splitedSynonyms));
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_list_item_1, arrayList);
                            listview.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                            listview.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<Feed> call, Throwable t) {

                            new SweetAlertDialog(CreateActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Word Not found!!")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                            onResume();

                        }
                    });

                }
                else{
                    new SweetAlertDialog(CreateActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Error")
                            .setContentText("Enter a Word")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }

            }
        });




     /*   timeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
                timeValue.setText(mtime);
            }
        });*/


        final String mdate = getIntent().getStringExtra("date");
        pickedDate.setText(mdate);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveAppointment(mdate);
            }
        });

    }

    private void saveAppointment(String mdate) {
        String title = titleValue.getText().toString().trim();
        String time = timeValue.getText().toString().trim();
        String details = detailsValue.getText().toString().trim();


        appointmentDbHelper = new AppointmentDbHelper(this);

        if (title.isEmpty()) {
            //error title is empty
            titleValue.setError("Enter a Title");
        }

        if (time.isEmpty()) {
            //error time is empty
            timeValue.setError("Enter a Time");
        }


        //create new person
        Appointment appointment = new Appointment(title, time, mdate, details);
        if (appointmentDbHelper.titlecheck(appointment).size() == 0) {
            appointmentDbHelper.saveAppointment(appointment);
             new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                     .setTitleText("Success")
                     .setContentText("Event Successfully Added!")
                     .setConfirmText("OK")
                     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                         @Override
                         public void onClick(SweetAlertDialog sweetAlertDialog) {
                             sweetAlertDialog.dismissWithAnimation();
                         }
                     }).show();
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Title Unavailable")
                    .setContentText("Appointment " + title + " already exists,please choose a different event title")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }
   /*private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    mtime = String.valueOf(mHour)+":"+ String.valueOf(mMinute);
                }
            };*/

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String AM_PM;
        if (hourOfDay < 12) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }
        mtime = String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + " " + AM_PM;
    }
}
