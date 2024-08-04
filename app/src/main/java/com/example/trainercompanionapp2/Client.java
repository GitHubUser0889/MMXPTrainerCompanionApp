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


public class Client extends Fragment {

   View view;
   Button cdAddClient;
   FirebaseAuth mAuth;
   DatabaseReference databaseReference;
   String userID;
   RecyclerView clientrecyclerview;
   List<ClientData> clientDataList;
   List<WorkoutData> workoutList;
   ValueEventListener eventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_client, container, false);

        cdAddClient = view.findViewById(R.id.AddClient);

        cdAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(new AddClient());
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
            userID = user.getUid();

            clientrecyclerview = view.findViewById(R.id.clientrecyclerview);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
            clientrecyclerview.setLayoutManager(gridLayoutManager);

            clientDataList = new ArrayList<>();
            workoutList = new ArrayList<>();


            ClientAdapter cdAdapter = new ClientAdapter(getActivity(), clientDataList, workoutList,new ClientAdapter.OnItemClickListener(){
                @Override
                public void clienItemClick(ClientData clientData){
                    gotoClientDetailFragment(clientData.getName(), clientData.getAge(), clientData.getContact(), clientData.getGender(),
                            clientData.getWeight(), clientData.getHeight(), clientData.getWorkout(), clientData.getGuideline(), clientData.getSessions(),
                            clientData.getNotes());
                    
                }
                @Override
                public void beginsessionItemClick(String workoutTitle, String instructions, String clientName){
                    gotoBeginSessionFragment(workoutTitle, instructions, clientName);

                }
            });



            clientrecyclerview.setAdapter(cdAdapter);

            databaseReference = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Clients");
            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ClientData client = dataSnapshot.getValue(ClientData.class);
                        clientDataList.add(client);
                    }cdAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference workoutRef = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Workout");
            workoutRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    workoutList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        WorkoutData workoutData = dataSnapshot.getValue(WorkoutData.class);
                        workoutList.add(workoutData);
                    }
                    cdAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }


    }

    private void gotoBeginSessionFragment(String Title, String Instructions, String Name) {
        ClientBeginSession clientBeginSession = ClientBeginSession.newInstance(Title,Instructions, Name);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, clientBeginSession);
        fragmentTransaction.commit();
    }

    private void gotoClientDetailFragment(String name, String age, String contact, String gender, String weight, String height, String workout, String guideline, String sessions, String notes) {
        ClientDetailFragment clientDetailFragment = ClientDetailFragment.newInstance(name, age, contact, gender, weight, height, workout, guideline, sessions, notes);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.mainframe, clientDetailFragment);
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