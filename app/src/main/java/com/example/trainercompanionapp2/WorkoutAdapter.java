package com.example.trainercompanionapp2;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutViewHolder>{

    private Context context;
    private List<WorkoutData> workoutList;
    private OnItemClickListener listener;

    public WorkoutAdapter(Context context, List<WorkoutData> workoutList, OnItemClickListener listener) {
        this.context = context;
        this.workoutList = workoutList;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(WorkoutData workoutData);
    }


    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_recycler_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {

        WorkoutData workoutData = workoutList.get(position);

        holder.recworkoutTitle.setText(workoutList.get(position).getTitle());

        holder.recworkoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(workoutData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}

class WorkoutViewHolder extends RecyclerView.ViewHolder{

    TextView recworkoutTitle;

    CardView recworkoutCard;

    public WorkoutViewHolder(@NonNull View itemView){
        super(itemView);

        recworkoutTitle = itemView.findViewById(R.id.rec_workout);
        recworkoutCard = itemView.findViewById(R.id.workout_item);

    }

}
