package com.monaara.sahan.testappointment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monaara.sahan.testappointment.R;
import com.monaara.sahan.testappointment.SearchActivity;
import com.monaara.sahan.testappointment.helper.AppointmentDbHelper;
import com.monaara.sahan.testappointment.model.Appointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahan on 4/29/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    public List<Appointment> mAppointmentList = new ArrayList<>();
    private Context mContext;
    private RecyclerView mRecyclerV;


    public SearchAdapter(List<Appointment> appointments, Context context, RecyclerView mRecyclerView) {
        mAppointmentList = appointments;
        mContext = context;
        mRecyclerV = mRecyclerView;
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTxtV;
        public TextView timeTxtV;
        public TextView detailTxtV;
        public TextView appointmentNo;




        public View layout;

        public SearchViewHolder(View v) {
            super(v);
            layout = v;
            titleTxtV = (TextView) v.findViewById(R.id.titleItem);
            timeTxtV = (TextView) v.findViewById(R.id.timeItem);
            detailTxtV = (TextView) v.findViewById(R.id.detailsItem);
            appointmentNo = (TextView) v.findViewById(R.id.appointmentNumber);





        }
    }



    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.search_item, parent, false);
        SearchViewHolder vh = new SearchViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder,final int position) {
        final Appointment appointment = mAppointmentList.get(position);
        holder.titleTxtV.setText(appointment.getTitle());
        holder.timeTxtV.setText(appointment.getTime());
        holder.detailTxtV.setText(appointment.getDetails());
        holder.appointmentNo.setText(String.valueOf(position+1));


    }




    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }
}
