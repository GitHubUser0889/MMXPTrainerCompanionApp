package com.example.trainercompanionapp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Guideline extends Fragment {

    View view;
    Button cdAddGuideline;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    RecyclerView guidelinerecyclerview;
    ValueEventListener eventListener;
    List<GuidelineData> guideList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_guideline, container, false);

        cdAddGuideline = view.findViewById(R.id.AddGuideline);

        cdAddGuideline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(new AddGuideline());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user !=null ){
            String userID = user.getUid();
            guidelinerecyclerview = view.findViewById(R.id.recyclerview_guideline);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            guidelinerecyclerview.setLayoutManager(gridLayoutManager);

            guideList = new ArrayList<>();

            GuidelineAdapter gdAdapter = new GuidelineAdapter(getActivity(), guideList, new GuidelineAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(GuidelineData guidelineData) {
                    gotoGuidelineDetailFragment(guidelineData.getTitle(), guidelineData.getInstructions());
                }
            });

            guidelinerecyclerview.setAdapter(gdAdapter);

            databaseReference = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Guidelines");
            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    guideList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        GuidelineData guidelineData = dataSnapshot.getValue(GuidelineData.class);
                        guideList.add(guidelineData);
                    }
                    gdAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }



    }

    private void gotoGuidelineDetailFragment(String title, String instructions) {
        GuidelineDetailFragment fragment = GuidelineDetailFragment.newInstance(title, instructions);
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