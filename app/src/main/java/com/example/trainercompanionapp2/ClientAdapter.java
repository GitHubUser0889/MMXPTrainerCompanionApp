package com.example.trainercompanionapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.ReturnThis;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder> {

    private Context context;
    List<ClientData> clientList;
    List<WorkoutData> workoutDataList;
    private OnItemClickListener clientitemClickListener;

    public ClientAdapter(Context context, List<ClientData> clientList, List<WorkoutData> workoutDataList ,OnItemClickListener clientitemClickListener) {
        this.context = context;
        this.clientList = clientList;
        this.workoutDataList = workoutDataList;
        this.clientitemClickListener = clientitemClickListener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_recycler_item, parent, false);
       return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        ClientData clientData = clientList.get(position);


        holder.recClientName.setText(clientList.get(position).getName());
        holder.recClientWorkout.setText(clientList.get(position).getWorkout());

        holder.recClientItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clientitemClickListener.clienItemClick(clientData);
            }
        });

        holder.recBeginSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String clientName = clientData.getName();
                String workoutTitle = clientData.getWorkout();
                String instructions = workoutInstructions(workoutTitle);

                clientitemClickListener.beginsessionItemClick(workoutTitle, instructions, clientName);
            }
        });


    }


    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public interface OnItemClickListener{
        void clienItemClick(ClientData clientData);
        void beginsessionItemClick(String workoutTitle, String instructions, String clientName);
    }
    private String workoutInstructions(String Title) {
        for (WorkoutData workoutData : workoutDataList){
            if(workoutData.getTitle().equals(Title)){
                return workoutData.getInstructions();
            }
        }
        return null;
    }




}

class ClientViewHolder extends RecyclerView.ViewHolder{
    TextView recClientName, recClientWorkout;
    CardView recClientItem;
    Button recBeginSession;

    public ClientViewHolder(@NonNull View itemView) {
        super(itemView);

        recClientName = itemView.findViewById(R.id.rec_client_name);
        recClientWorkout = itemView.findViewById(R.id.rec_client_workout);
        recClientItem = itemView.findViewById(R.id.client_item);
        recBeginSession = itemView.findViewById(R.id.beginsession);
    }

}


