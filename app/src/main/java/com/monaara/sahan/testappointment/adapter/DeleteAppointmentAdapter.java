package com.monaara.sahan.testappointment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.monaara.sahan.testappointment.R;
import com.monaara.sahan.testappointment.UpdateActivity;
import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;
import com.monaara.sahan.testappointment.model.Appointment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahan on 4/28/2018.
 */

public class DeleteAppointmentAdapter extends RecyclerView.Adapter<DeleteAppointmentAdapter.DeleteViewHolder> {
    public List<Appointment> mAppointmentList = new ArrayList<>();
    private Context mContext;
    private RecyclerView mRecyclerV;
    private AppointmentDbHelper appointmentDbHelper;
    Appointment mappointment;
    int mDeleteThis;


    public class DeleteViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTxtV;
        public TextView timeTxtV;
        public TextView detailTxtV;
        public TextView appointmentNo;


        public View layout;

        public DeleteViewHolder(View v) {
            super(v);
            layout = v;
            titleTxtV = (TextView) v.findViewById(R.id.titleItem);
            timeTxtV = (TextView) v.findViewById(R.id.timeItem);
            detailTxtV = (TextView) v.findViewById(R.id.detailsItem);
            appointmentNo = (TextView) v.findViewById(R.id.appointmentNumber);
            appointmentDbHelper = new AppointmentDbHelper(mContext);


        }
    }


    public DeleteAppointmentAdapter(List<Appointment> myDataset, Context context, RecyclerView recyclerView, int deletePosition) {
        mAppointmentList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
        mDeleteThis = deletePosition;

    }


    @Override
    public DeleteAppointmentAdapter.DeleteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.delete_appointment_item, parent, false);
        DeleteViewHolder vh = new DeleteViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final DeleteAppointmentAdapter.DeleteViewHolder holder, final int position) {
        final Appointment appointment = mAppointmentList.get(position);
        holder.titleTxtV.setText(appointment.getTitle());
        holder.timeTxtV.setText(appointment.getTime());
        holder.detailTxtV.setText(appointment.getDetails());
        holder.appointmentNo.setText(String.valueOf(position + 1));
        if (!(mDeleteThis == 0)) {
            deleteAppointment(appointment.getId(), mAppointmentList.get(mDeleteThis - 1));



        }


    }

    //deleteing one appointment
    private void deleteAppointment(int id, Appointment appointment) {
        int deletethisId = mAppointmentList.get(mDeleteThis - 1).getId();
        int position  = mAppointmentList.indexOf(appointment);
        appointmentDbHelper.delete(deletethisId);
        mAppointmentList.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }
}
