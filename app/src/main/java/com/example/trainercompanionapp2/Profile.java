package com.example.trainercompanionapp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends Fragment {

    View view;
    TextView cdtrainerName, cdtrainerEmail, cdtrainerContact;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String userID;
    ValueEventListener eventListener;
    String name, email, contactnum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        cdtrainerName = view.findViewById(R.id.view_trainername);
        cdtrainerEmail = view.findViewById(R.id.view_traineremail);
        cdtrainerContact = view.findViewById(R.id.view_trainercontact);





        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            userID = user.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Profile");

            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                        RegisteredData registeredData = snapshot.getValue(RegisteredData.class);
                        name = registeredData.getDatabaseUsername();
                        email = registeredData.getDatabaseEmail();
                        contactnum = registeredData.getDatabaseContact();

                    cdtrainerName.setText(name);
                    cdtrainerEmail.setText(email);
                    cdtrainerContact.setText(contactnum);



                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getActivity(), "Profile Data Not Found", Toast.LENGTH_SHORT).show();

                }
            });
        }
        return view;
    }
}

