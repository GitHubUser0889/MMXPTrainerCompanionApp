package com.example.trainercompanionapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GuidelineAdapter extends RecyclerView.Adapter<GuidelineViewHolder>{

    private Context context;
    private List<GuidelineData> Guidelist;
    private OnItemClickListener listener;

    public GuidelineAdapter(Context context, List<GuidelineData> guidelist, OnItemClickListener listener) {
        this.context = context;
        Guidelist = guidelist;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(GuidelineData guidelineData);
    }


    @NonNull
    @Override
    public GuidelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guideline_recycler_item, parent, false);
        return new GuidelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuidelineViewHolder holder, int position) {
        GuidelineData guidelineData = Guidelist.get(position);

        holder.recGuidelineTitle.setText(Guidelist.get(position).getTitle());

        holder.recGuidelineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(guidelineData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Guidelist.size();
    }



}

class GuidelineViewHolder extends RecyclerView.ViewHolder{

    TextView recGuidelineTitle;
    CardView recGuidelineCard;

    public GuidelineViewHolder(@NonNull View itemView){
        super(itemView);

        recGuidelineCard = itemView.findViewById(R.id.guideline_item);
        recGuidelineTitle = itemView.findViewById(R.id.rec_guideline_title);


    }

}
