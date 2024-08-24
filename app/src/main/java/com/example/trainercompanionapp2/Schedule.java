package com.example.trainercompanionapp2;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Schedule extends Fragment {


    CalendarView cdCalendar;
    String str_month, str_year, str_day;
    EditText cdDay, cdMonth, cdYear, cdScheduleWorkout;
    Button cdAddSched, cdsetTime;
    FirebaseAuth mAuth;
    Spinner cdScheduleClient;
    String userID;
    List<ClientData> clientDataList;
    TextView cdTime;
    Calendar calendar;
    String timeText, Hours, Minutes;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_schedule, container, false);
        cdCalendar = view.findViewById(R.id.Calendar);
        cdDay = view.findViewById(R.id.day);
        cdMonth = view.findViewById(R.id.month);
        cdYear = view.findViewById(R.id.year);
        cdTime = view.findViewById(R.id.texttime);
        cdsetTime = view.findViewById(R.id.setTime);
        cdAddSched = view.findViewById(R.id.AddSchedule);
        cdScheduleWorkout = view.findViewById(R.id.scheduleworkout);


        NotificationHelper.createnotificationChannel(getActivity());

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


        cdCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                switch(month){
                    case 0:
                        str_month = "January";
                        break;

                    case 1:
                        str_month = "February";
                        break;

                    case 2:
                        str_month = "March";
                        break;

                    case 3:
                        str_month = "April";
                        break;

                    case 4:
                        str_month = "May";
                        break;

                    case 5:
                        str_month = "June";
                        break;

                    case 6:
                        str_month = "July";
                        break;

                    case 7:
                        str_month = "August";
                        break;

                    case 8:
                        str_month = "September";
                        break;

                    case 9:
                        str_month = "October";
                        break;

                    case 10:
                        str_month = "November";
                        break;

                    case 11:
                        str_month = "December";
                        break;

                }
                str_year = Integer.toString(year);
                str_day = Integer.toString(dayOfMonth);
                cdDay.setText(str_day);
                cdMonth.setText(str_month);
                cdYear.setText(str_year);



            }
        });

        cdsetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Hours = String.valueOf(hourOfDay);
                        Minutes = String.valueOf(minute);
                        calendar = Calendar.getInstance();
                        calendar.set(0,0,0,hourOfDay,minute);
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                        timeText = ("" +sdf.format(calendar.getTime()));
                        cdTime.setText(timeText);
                    }
                }, 12, 00, false);
                timePickerDialog.show();

            }
        });

        cdAddSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkifTrue()){
                    saveSchedule();
                }else{
                }

                cdDay.setText("");
                cdMonth.setText("");
                cdYear.setText("");
                cdTime.setText("");


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
            String Time = timeText;
            String hours = Hours;
            String minutes = Minutes;

            ScheduleData scheduleData = new ScheduleData(Year, Month, Day, ClientName, ClientWorkout,Time);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Trainers").child(userID).child("Schedule").child(ClientName)
                    .setValue(scheduleData).addOnCompleteListener(getActivity(), task ->{
                        if (task.isSuccessful()){

                            Toast.makeText(getActivity(), "Schedule Added", Toast.LENGTH_SHORT).show();
                            setNotification(ClientName, ClientWorkout, Year, Month, Day, hours, minutes);


                        }else {
                            Toast.makeText(getActivity(), "Failed to Add Schedule", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void setNotification(String clientName, String clientWorkout, String year,
                                 String month, String day, String hours, String minutes) {


        Calendar calendaria = Calendar.getInstance();
        int hour = Integer.parseInt(hours);
        int minute = Integer.parseInt(minutes);
        calendaria.set(Calendar.YEAR, Integer.parseInt(year));
        calendaria.set(Calendar.MONTH, getMonthNumber(month));
        calendaria.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        calendaria.set(Calendar.HOUR_OF_DAY, hour);
        calendaria.set(Calendar.MINUTE, minute);
        calendaria.set(Calendar.SECOND, 0);

        Intent intent = new Intent(getContext(), NotifReceiver.class);
        intent.putExtra("ClientName", clientName);
        intent.putExtra("ClientWorkout", clientWorkout);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendaria.getTimeInMillis(), pendingIntent);
        } else {
            Toast.makeText(getContext(), "Alarm Manager not available", Toast.LENGTH_SHORT).show();
        }
    }

    private int getMonthNumber(String monthName) {
        switch (monthName) {
            case "January":
                return Calendar.JANUARY;
            case "February":
                return Calendar.FEBRUARY;
            case "March":
                return Calendar.MARCH;
            case "April":
                return Calendar.APRIL;
            case "May":
                return Calendar.MAY;
            case "June":
                return Calendar.JUNE;
            case "July":
                return Calendar.JULY;
            case "August":
                return Calendar.AUGUST;
            case "September":
                return Calendar.SEPTEMBER;
            case "October":
                return Calendar.OCTOBER;
            case "November":
                return Calendar.NOVEMBER;
            case "December":
                return Calendar.DECEMBER;
            default:
                return -1;}
    }

    private boolean checkifTrue(){
        String cdTimeCheck = cdTime.getText().toString();
        boolean valid = true;
        if (cdTimeCheck.isEmpty()){
            Toast.makeText(getActivity(), "SET A TIME FOR THE CLIENT", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }


}