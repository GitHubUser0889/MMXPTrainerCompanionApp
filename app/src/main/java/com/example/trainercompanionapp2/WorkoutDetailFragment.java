package com.example.trainercompanionapp2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class WorkoutDetailFragment extends Fragment {

    private static final String ARG_WORKOUT_TITLE = "title";
    private static final String ARG_WORKOUT_INSTRUCTIONS = "instructions";

    private String argsworkoutTitle;
    private String argsworkoutInstructions;

    private WorkoutData workout;

    public static WorkoutDetailFragment newInstance(String wdfworkTitle, String wdfworkInstructions) {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORKOUT_TITLE, (Serializable) wdfworkTitle);
        args.putSerializable(ARG_WORKOUT_INSTRUCTIONS, (Serializable) wdfworkInstructions);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            argsworkoutTitle = getArguments().getString(ARG_WORKOUT_TITLE);
            argsworkoutInstructions = getArguments().getString(ARG_WORKOUT_INSTRUCTIONS);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);
        EditText workoutTitle = view.findViewById(R.id.workoutTitle);
        EditText workoutInstructions = view.findViewById(R.id.workoutInstr);

        workoutTitle.setText(argsworkoutTitle);
        workoutInstructions.setText(argsworkoutInstructions);
        return view;
    }
}
