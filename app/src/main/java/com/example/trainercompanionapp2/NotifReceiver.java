package com.example.trainercompanionapp2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotifReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if (intent != null) {
            String clientName = intent.getStringExtra("ClientName");
            String clientWorkout = intent.getStringExtra("ClientWorkout");

            String title = "Workout Reminder";
            String message = "Client: " + clientName + ", Workout: " + clientWorkout;

            NotificationHelper.createnotificationChannel(context);
            NotificationHelper.showNotification(context, title, message);
        }
    }
}
