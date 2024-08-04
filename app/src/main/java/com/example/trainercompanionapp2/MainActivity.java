package com.example.trainercompanionapp2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    Button cdWorkout, cdClient, cdGuideline, cdSchedule, cdDashboard;
    TextView cdffragmentTitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cdDashboard = findViewById(R.id.Dashboard);
        cdWorkout = findViewById(R.id.Workout);
        cdClient = findViewById(R.id.Client);
        cdGuideline = findViewById(R.id.Guideline);
        cdSchedule = findViewById(R.id.Schedule);
        cdffragmentTitle = findViewById(R.id.fragmentTitle);

        startFragment(new Dashboard());
        cdffragmentTitle.setText("DASHBOARD");

        cdWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new Workout());
                cdffragmentTitle.setText("WORKOUT");
            }
        });

        cdClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new Client());
                cdffragmentTitle.setText("CLIENTS");
            }
        });

        cdGuideline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new Guideline());
                cdffragmentTitle.setText("GUIDELINES");
            }
        });

        cdSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new Schedule());
                cdffragmentTitle.setText("SCHEDULE");
            }
        });

        cdDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new Dashboard());
                cdffragmentTitle.setText("DASHBOARD");
            }
        });


    }

    private void startFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();

    }
}