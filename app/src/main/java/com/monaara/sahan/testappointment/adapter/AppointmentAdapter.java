package com.monaara.sahan.testappointment.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.monaara.sahan.testappointment.CreateActivity;
import com.monaara.sahan.testappointment.R;
import com.monaara.sahan.testappointment.UpdateActivity;
import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;
import com.monaara.sahan.testappointment.model.Appointment;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sahan on 4/25/2018.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> mAppointmentList;
    private Context mContext;
    private RecyclerView mRecyclerV;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTxtV;
        public TextView timeTxtV;
        public TextView detailTxtV;
        public Button editBtn;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            titleTxtV = (TextView) v.findViewById(R.id.titleItem);
            timeTxtV = (TextView) v.findViewById(R.id.timeItem);
            detailTxtV = (TextView) v.findViewById(R.id.detailsItem);
           editBtn = (Button) v.findViewById(R.id.btnEdit);


        }
    }

    public void add(int position, Appointment appointment) {
        mAppointmentList.add(position, appointment);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mAppointmentList.remove(position);
        notifyItemRemoved(position);
    }

    public AppointmentAdapter(List<Appointment> myDataset, Context context, RecyclerView recyclerView) {
        mAppointmentList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_appointment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AppointmentAdapter.ViewHolder holder, final int position) {
//adding data to holder
        final Appointment appointment = mAppointmentList.get(position);
        holder.titleTxtV.setText(appointment.getTitle());
        holder.timeTxtV.setText(appointment.getTime());
        holder.detailTxtV.setText(appointment.getDetails());
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goToUpdateActivity(appointment.getId(),appointment.getDate());
            }
        });

    }
    //going to update activity with date and id
    private void goToUpdateActivity(int id,String mdate){
        Intent goToUpdate = new Intent(mContext, UpdateActivity.class);
        goToUpdate.putExtra("id", id);
        goToUpdate.putExtra("date",mdate);
        mContext.startActivity(goToUpdate);
    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }
}
