package com.monaara.sahan.testappointment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.monaara.sahan.testappointment.MoveDateActivity;
import com.monaara.sahan.testappointment.R;
import com.monaara.sahan.testappointment.model.Appointment;

import java.util.List;

/**
 * Created by Sahan on 4/28/2018.
 */

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MoveViewHolder> {
     Button moveBtn;
    private List<Appointment> mAppointmentList;
    private Context mContext;
    private RecyclerView mRecyclerV;


    public class MoveViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTxtV;
        public TextView timeTxtV;
        public TextView detailTxtV;
        public Button moveBtn;


        public View layout;

        public MoveViewHolder(View v) {
            super(v);
            layout = v;
            titleTxtV = (TextView) v.findViewById(R.id.titleItem);
            timeTxtV = (TextView) v.findViewById(R.id.timeItem);
            detailTxtV = (TextView) v.findViewById(R.id.detailsItem);
            moveBtn = (Button) v.findViewById(R.id.moveButton);


        }
    }


    public MoveAdapter(List<Appointment> myDataset, Context context, RecyclerView recyclerView) {
        mAppointmentList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    @Override
    public MoveAdapter.MoveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.move_appointment_item, parent, false);
        MoveViewHolder vh = new MoveViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MoveAdapter.MoveViewHolder holder, final int position) {

        final Appointment appointment = mAppointmentList.get(position);
        holder.titleTxtV.setText(appointment.getTitle());
        holder.timeTxtV.setText(appointment.getTime());
        holder.detailTxtV.setText(appointment.getDetails());
        holder.moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUpdateCalender(appointment.getId(),appointment.getDate());
            }
        });


    }
//going to update the date
    private void gotoUpdateCalender(int id, String date) {
        Intent intent = new Intent(mContext,MoveDateActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("date",date);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }
}
