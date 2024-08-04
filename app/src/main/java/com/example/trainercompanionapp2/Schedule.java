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
import android.widget.CalendarView;


public class Schedule extends Fragment {


    CalendarView cdCalendar;
    String str_month;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_schedule, container, false);
        cdCalendar = view.findViewById(R.id.Calendar);


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


                String str_year = Integer.toString(year);

                String str_day = Integer.toString(dayOfMonth);

                gotoAddSchedule(str_year, str_month, str_day);

            }
        });
        return view;
    }

    private void gotoAddSchedule(String strYear, String strMonth, String strDay) {
        AddSchedule addSchedule = AddSchedule.newInstance(strYear, strMonth, strDay);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, addSchedule);
        fragmentTransaction.commit();
    }
}