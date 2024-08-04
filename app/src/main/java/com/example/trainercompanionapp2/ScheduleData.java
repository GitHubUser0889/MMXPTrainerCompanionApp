package com.example.trainercompanionapp2;

public class ScheduleData {
    public ScheduleData() {
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }
    public String getClientWorkout() {
        return ClientWorkout;
    }

    public void setClientWorkout(String clientWorkout) {
        ClientWorkout = clientWorkout;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


    public ScheduleData(String year, String month, String day, String clientName, String clientWorkout,String time) {
        Year = year;
        Month = month;
        Day = day;
        ClientName = clientName;
        ClientWorkout = clientWorkout;
        Time = time;
    }

    private String Year;
    private String Month;
    private String Day;
    private String ClientName;



    private String ClientWorkout;
    private String Time;
}
