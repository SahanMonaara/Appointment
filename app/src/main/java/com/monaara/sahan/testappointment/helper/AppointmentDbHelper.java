package com.monaara.sahan.testappointment.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.monaara.sahan.testappointment.model.Appointment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sahan on 4/25/2018.
 */

public class AppointmentDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "appoinment.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "appointment";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DETAILS = "details";

    public AppointmentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_TIME + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_DETAILS + " TEXT );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
//insert a appointment
    public void saveAppointment(Appointment appointment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, appointment.getTitle());
        values.put(COLUMN_TIME, appointment.getTime());
        values.put(COLUMN_DATE, appointment.getDate());
        values.put(COLUMN_DETAILS, appointment.getDetails());

        // insert
        db.insertOrThrow(TABLE_NAME, null, values);
        db.close();
    }
    //checking title before insert
    public List<String> titlecheck(Appointment appointment){
        String query = "SELECT * FROM appointment WHERE date= '" + appointment.getDate() + "' " + "AND title LIKE '%"+appointment.getTitle()+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<String> titles = new LinkedList<>();
        if (cursor.moveToNext()){
            titles.add(cursor.getString(cursor.getColumnIndex("title")));
        }
        return titles;
    }
//take all appointments
    public List<Appointment> appointmentList(String selectedDate) {
        String query;
        if (selectedDate.equals("")) {
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        } else {
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " WHERE date = '" + selectedDate + "'";
        }

        List<Appointment> appointmentLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Appointment appointment;

        if (cursor.moveToFirst()) {
            do {
                appointment = new Appointment();

                appointment.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                appointment.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                appointment.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                appointment.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                appointment.setDetails(cursor.getString(cursor.getColumnIndex(COLUMN_DETAILS)));
                appointmentLinkedList.add(appointment);
            } while (cursor.moveToNext());
        }


        return appointmentLinkedList;
    }
// retrieving appointments
    public Appointment getAppointment(int id, String selectedDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE id=" + id + " AND date='" + selectedDate + "'";
        Cursor cursor = db.rawQuery(query, null);

        Appointment receivedAppointment = new Appointment();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedAppointment.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            receivedAppointment.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
            receivedAppointment.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            receivedAppointment.setDetails(cursor.getString(cursor.getColumnIndex(COLUMN_DETAILS)));
        }
        return receivedAppointment;
    }

    public void deleteAppointmentRecord(int id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE id='" + id + "'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }
//update appointments
    public void updateAppointmentRecord(int id, Context context, Appointment updatedAppointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  " + TABLE_NAME + " SET title ='" + updatedAppointment.getTitle() + "', time ='" + updatedAppointment.getTime() + "', date ='" + updatedAppointment.getDate() + "', details ='" + updatedAppointment.getDetails() + "'  WHERE id='" + id + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


    }
    public void updateDateOnly(int id, String updateDate){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  " + TABLE_NAME + " SET date ='" + updateDate  + "'  WHERE id='" + id + "'");
    }

    public void deleteAllAppointmentRecords(String selectedDate) {
        String deleteAllQuery = "DELETE FROM " + TABLE_NAME + " WHERE date='" + selectedDate + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteAllQuery);
        db.execSQL("vacuum");
        db.close();
    }

//delete one appointment
    public void delete(int id) {
        String deleteQuery = "DELETE FROM "+TABLE_NAME+" WHERE id= '" + id + "'";
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();
    }
//search appointments
    public List<Appointment> searchAppointments(String text){
        String searchQuery = " SELECT * FROM "+TABLE_NAME+ " WHERE (title LIKE '%"+text+"%'"+ " OR  details LIKE '%"+text+"%')";
        List<Appointment> appointmentLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);
        Appointment appointment;

        if (cursor.moveToFirst()) {
            do {
                appointment = new Appointment();

                appointment.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                appointment.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                appointment.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                appointment.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                appointment.setDetails(cursor.getString(cursor.getColumnIndex(COLUMN_DETAILS)));
                appointmentLinkedList.add(appointment);
            } while (cursor.moveToNext());
        }


        return appointmentLinkedList;
    }
}
