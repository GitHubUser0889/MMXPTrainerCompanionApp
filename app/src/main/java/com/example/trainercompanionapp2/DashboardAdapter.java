package com.example.trainercompanionapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<ScheduleViewHolder>{


    private Context context;
    private List<ScheduleData> scheduleDataList;

  

    public DashboardAdapter(Context context, List<ScheduleData> scheduleDataList) {
        this.context = context;
        this.scheduleDataList = scheduleDataList;

    }



    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_recycle_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {


        holder.cdrecDay.setText(scheduleDataList.get(position).getDay());
        holder.cdrecMonth.setText(trimMonthName(scheduleDataList.get(position).getMonth()));
        holder.cdrecYear.setText(scheduleDataList.get(position).getYear());
        holder.cdrecClientName.setText(scheduleDataList.get(position).getClientName());
        holder.cdrecClientWorkout.setText(scheduleDataList.get(position).getClientWorkout());
        holder.cdrecTime.setText(scheduleDataList.get(position).getTime());




    }




    @Override
    public int getItemCount() {
        return scheduleDataList.size();
    }

    private String trimMonthName(String month){
        if (month.length() > 3){
            return month.substring(0,3).toUpperCase();
        }else{return month.toUpperCase();}

    }
}

class ScheduleViewHolder extends RecyclerView.ViewHolder{

    TextView cdrecDay, cdrecMonth, cdrecYear, cdrecClientName, cdrecClientWorkout, cdrecTime;
    CardView cdDashboardItem;
    public ScheduleViewHolder(@NonNull View itemView){
        super(itemView);

        cdrecDay = itemView.findViewById(R.id.rec_sched_day);
        cdrecMonth = itemView.findViewById(R.id.rec_sched_month);
        cdrecYear = itemView.findViewById(R.id.rec_sched_year);
        cdrecClientName = itemView.findViewById(R.id.rec_sched_name);
        cdrecClientWorkout = itemView.findViewById(R.id.rec_sched_workout);
        cdrecTime = itemView.findViewById(R.id.rec_sched_time);
        cdDashboardItem = itemView.findViewById(R.id.dashboard_item);
    }


}
