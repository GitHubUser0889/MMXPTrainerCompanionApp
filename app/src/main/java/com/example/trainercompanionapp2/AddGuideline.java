package com.example.trainercompanionapp2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddGuideline extends Fragment {

    View view;
    EditText cdTitle, cdInstr;
    Button cdSave, cdCancel;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_guideline, container, false);

        cdTitle = view.findViewById(R.id.GuidelineTitle);
        cdInstr = view.findViewById(R.id.GuidelineInstr);
        cdSave = view.findViewById(R.id.SaveGuideline);
        cdCancel = view.findViewById(R.id.CancelGuideline);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        cdSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGuideline();
            }
        });

        cdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtoFragment(new Guideline());
            }
        });


        return view;
    }

    private void backtoFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }

    private void saveGuideline() {
        String TitleGuideline = cdTitle.getText().toString().trim();
        String InstrGuideline = cdInstr.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userID = user.getUid();
            WorkoutData workoutData = new WorkoutData(TitleGuideline, InstrGuideline);
            databaseReference.child("Trainers").child(userID).child("Guidelines").child(TitleGuideline).setValue(workoutData)
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Guideline Added", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Failed to add Guideline.", Toast.LENGTH_SHORT).show();
                        }
                        cdTitle.setText("");
                        cdInstr.setText("");
                    });
        }

    }
}