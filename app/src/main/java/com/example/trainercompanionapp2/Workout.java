package com.example.trainercompanionapp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Workout extends Fragment {

    View view;
    Button cdAddWorkout;
    RecyclerView workoutrecyclerView;

    List<WorkoutData> workoutList;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    ValueEventListener eventListener;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_workout, container, false);

        cdAddWorkout = view.findViewById(R.id.AddWorkout);
        workoutList = new ArrayList<>();



        cdAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(new AddWorkout());
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user!=null){
            String userID = user.getUid();
            workoutrecyclerView = view.findViewById(R.id.recyclerview_workout);


            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            workoutrecyclerView.setLayoutManager(gridLayoutManager);

            workoutList = new ArrayList<>();
            WorkoutAdapter wkAdapter = new WorkoutAdapter(getActivity(), workoutList, new WorkoutAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(WorkoutData workoutData) {
                    gotoWorkoutDetailFragment(workoutData.getTitle(), workoutData.getInstructions());
                }
            });
            workoutrecyclerView.setAdapter(wkAdapter);

            databaseReference = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Workout");
            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    workoutList.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        WorkoutData workoutData = dataSnapshot.getValue(WorkoutData.class);
                        workoutList.add(workoutData);
                    }wkAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

    }

    private void gotoWorkoutDetailFragment(String title, String instructions) {
        WorkoutDetailFragment fragment = WorkoutDetailFragment.newInstance(title, instructions);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.mainframe, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void gotoFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }
}