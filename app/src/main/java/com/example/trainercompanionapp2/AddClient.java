package com.example.trainercompanionapp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class AddClient extends Fragment {


    View view;
    EditText cdClientName, cdClientAge, cdClientWeight, cdClientHeight, cdClientNotes, cdClientContact;
    Button cdSave, cdCancel;
    ProgressBar cdSessionCounter;
    FirebaseAuth mAuth;
    Spinner cdClientWorkout;
    Spinner cdClientGuideline, cdClientGender;
    String userID;

    int SessionCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_client, container, false);
        cdSessionCounter = view.findViewById(R.id.SessionCounter);
        cdClientName = view.findViewById(R.id.clientname);
        cdClientAge = view.findViewById(R.id.clientage);
        cdClientWeight = view.findViewById(R.id.clientweight);
        cdClientHeight = view.findViewById(R.id.clientheight);
        cdClientNotes = view.findViewById(R.id.clientnotes);
        cdClientContact = view.findViewById(R.id.clientcontact);
        cdSave = view.findViewById(R.id.clientsave);
        cdCancel = view.findViewById(R.id.clientcancel);
        populategenderSpinner();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user!=null){
            userID = user.getUid();

            DatabaseReference workoutRef = FirebaseDatabase.getInstance().getReference("Trainers")
                    .child(userID).child("Workout");
            workoutRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String>workoutNames = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                WorkoutData workoutData = dataSnapshot.getValue(WorkoutData.class);
                                workoutNames.add(workoutData.getTitle());
                            }
                            ArrayAdapter<String> workoutAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, workoutNames);
                            workoutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cdClientWorkout = view.findViewById(R.id.clientworkout);
                            cdClientWorkout.setAdapter(workoutAdapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            DatabaseReference guideRef = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Guidelines");
            guideRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> guideNames = new ArrayList<String>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        GuidelineData guidelineData = dataSnapshot.getValue(GuidelineData.class);
                        guideNames.add(guidelineData.getTitle());
                    }
                    ArrayAdapter<String> workoutAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, guideNames);
                    workoutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cdClientGuideline = view.findViewById(R.id.clientguideline);
                    cdClientGuideline.setAdapter(workoutAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        cdSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveclientData();
            }
        });

        cdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtoFragment(new Client());
            }
        });

       




        return view;
    }

    private void backtoFragment(Fragment client) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, client);
        fragmentTransaction.commit();
    }

    private void saveclientData() {

        String name = cdClientName.getText().toString().trim();
        String age = cdClientAge.getText().toString().trim();
        String contact = cdClientContact.getText().toString().trim();
        String gender = cdClientGender.getSelectedItem().toString().trim();
        String weight = cdClientWeight.getText().toString().trim();
        String height = cdClientHeight.getText().toString().trim();
        String workout = cdClientWorkout.getSelectedItem().toString();
        String guideline = cdClientGuideline.getSelectedItem().toString();
        String session = String.valueOf(SessionCount);
        String notes = cdClientNotes.getText().toString().trim();

        if (!name.isEmpty() | !age.isEmpty() | !workout.isEmpty()){
            DatabaseReference clientRef = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Clients").child(name);
            ClientData clientData = new ClientData(name, age, contact, gender, weight, height, workout, guideline, session, notes);
            clientRef.setValue(clientData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getActivity(), "Client Added", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Failed to Add Client", Toast.LENGTH_SHORT).show();
                    }

                    cdClientName.setText("");
                    cdClientAge.setText("");
                    cdClientContact.setText("");
                    cdClientNotes.setText("");
                    cdClientWeight.setText("");
                    cdClientHeight.setText("");

                }
            });

        }else{
            Toast.makeText(getActivity(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
        }
    }


    private void populategenderSpinner() {
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cdClientGender = view.findViewById(R.id.clientgender);
        cdClientGender.setAdapter(genderAdapter);
    }
}