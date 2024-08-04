package com.example.trainercompanionapp2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ClientBeginSession extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    private String mParam1;
    private String mParam2;
    private String mParam3;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String userID;


    public ClientBeginSession() {

    }



    public static ClientBeginSession newInstance(String param1, String param2, String param3) {
        ClientBeginSession fragment = new ClientBeginSession();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EditText bgsTitle, bgsInstr;
        Button bgsSave, bgsCancel;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_begin_session, container, false);

        bgsTitle = view.findViewById(R.id.bgsworkoutTitle);
        bgsInstr = view.findViewById(R.id.bgsworkoutInstr);
        bgsSave = view.findViewById(R.id.SaveSession);
        bgsTitle.setText(mParam1);
        bgsInstr.setText(mParam2);

        bgsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSession();
            }
        });
        return view;
    }

    private void saveSession() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user !=null){
            String clientName = mParam3;
            userID = user.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Clients").child(clientName);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        ClientData clientData = snapshot.getValue(ClientData.class);
                        if (clientData != null){
                            int currentSessions = Integer.parseInt(clientData.getSessions());
                            int updatedSessions = currentSessions + 10;
                            databaseReference.child("Sessions").setValue(String.valueOf(updatedSessions));
                            Toast.makeText(getActivity(), "Session saved successfully", Toast.LENGTH_SHORT).show();
                            gobacktoFragment(new Client());
                        }else {
                            Toast.makeText(getActivity(), "Client not found", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to save session", Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

    private void gobacktoFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }
}