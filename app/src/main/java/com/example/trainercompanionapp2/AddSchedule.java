package com.example.trainercompanionapp2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSchedule extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    public AddSchedule() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddSchedule newInstance(String param1, String param2, String param3) {
        AddSchedule fragment = new AddSchedule();
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

    EditText cdDay, cdMonth, cdYear, cdHours, cdMinutes, cdScheduleWorkout;
    Spinner cdAMPPM;
    Button cdAddSched;
    FirebaseAuth mAuth;
    Spinner cdScheduleClient;
    String userID;
    List<ClientData> clientDataList;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_schedule, container, false);

        cdDay = view.findViewById(R.id.day);
        cdMonth = view.findViewById(R.id.month);
        cdYear = view.findViewById(R.id.year);
        cdHours = view.findViewById(R.id.hour);
        cdMinutes = view.findViewById(R.id.minutes);
        cdAMPPM = view.findViewById(R.id.ampm);
        cdAddSched = view.findViewById(R.id.AddSchedule);
        cdScheduleWorkout = view.findViewById(R.id.scheduleworkout);
        cdDay.setText(mParam3);
        cdMonth.setText(mParam2);
        cdYear.setText(mParam1);


        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.time_array, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cdAMPPM.setAdapter(timeAdapter);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            userID = user.getUid();
            clientDataList = new ArrayList<>();
            DatabaseReference clientref = FirebaseDatabase.getInstance().getReference("Trainers").child(userID).child("Clients");
            clientref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> ClientNames = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ClientData clientData = dataSnapshot.getValue(ClientData.class);
                        clientDataList.add(clientData);
                        ClientNames.add(clientData.getName());
                    }
                    ArrayAdapter<String> clientAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ClientNames);
                    clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cdScheduleClient = view.findViewById(R.id.scheduleclient);
                    cdScheduleClient.setAdapter(clientAdapter);

                    cdScheduleClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ClientData clientData = clientDataList.get(position);
                            cdScheduleWorkout.setText(clientData.getWorkout());

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



        cdAddSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSchedule();
            }
        });






        return view;
    }

    private void saveSchedule() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            String Year = cdYear.getText().toString();
            String Month = cdMonth.getText().toString();
            String Day = cdDay.getText().toString();
            String ClientName = cdScheduleClient.getSelectedItem().toString();
            String ClientWorkout = cdScheduleWorkout.getText().toString();
            String Hours = cdHours.getText().toString();
            String Minutes = cdMinutes.getText().toString();
            String AMPM = cdAMPPM.getSelectedItem().toString();

            String Time = Hours + " : " +  Minutes + " " + AMPM;

            ScheduleData scheduleData = new ScheduleData(Year, Month, Day, ClientName, ClientWorkout,Time);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Trainers").child(userID).child("Schedule").child(ClientName)
                    .setValue(scheduleData).addOnCompleteListener(getActivity(), task ->{
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Schedule Added", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "Failed to Add Schedule", Toast.LENGTH_SHORT).show();
                        }
            });

        }
    }




}