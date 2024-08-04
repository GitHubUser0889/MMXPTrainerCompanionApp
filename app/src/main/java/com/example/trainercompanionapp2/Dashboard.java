package com.example.trainercompanionapp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Dashboard extends Fragment {

    View view;
    RecyclerView cdRecyclerView;
    FirebaseAuth mAuth;
    List<ScheduleData> scheduleDataList;
    DatabaseReference databaseReference;
    String userID;
    ValueEventListener eventListener;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            userID = user.getUid();

            cdRecyclerView = view.findViewById(R.id.dashboardrecyclerview);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
            cdRecyclerView.setLayoutManager(gridLayoutManager);

            scheduleDataList = new ArrayList<>();

            DashboardAdapter dbAdapter = new DashboardAdapter(getActivity(), scheduleDataList);

            cdRecyclerView.setAdapter(dbAdapter);

            databaseReference = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Schedule");
            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    scheduleDataList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ScheduleData scheduleData = dataSnapshot.getValue(ScheduleData.class);
                        scheduleDataList.add(scheduleData);

                    }dbAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

    private void gotoClientFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }
}