package com.example.trainercompanionapp2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    TextView loginredirect;
    EditText cdEmail, cdPassword, cdUsername, cdContact;
    Button cdRegister;


    AlertDialog.Builder builder;

    String userID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginredirect = findViewById(R.id.logredirect);
        cdEmail = findViewById(R.id.email);
        cdPassword = findViewById(R.id.password);
        cdUsername = findViewById(R.id.name);
        cdContact = findViewById(R.id.contact);
        cdRegister = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(this);


        loginredirect.setPaintFlags(loginredirect.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



        cdRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkErrors()){
                    String email = cdEmail.getText().toString().trim();
                    String password = cdPassword.getText().toString().trim();


                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    builder.setCancelable(false);
                    builder.setView(R.layout.loading_dialog);
                    AlertDialog dialog = builder.create();
                    dialog.show();



                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, task -> {
                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(user != null){
                                        userID = user.getUid();
                                        saveprofile();
                                        Toast.makeText(Register.this, "Registration Succesful", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(Register.this, "Registration unsuccesful", Toast.LENGTH_SHORT).show();
                                }

                                dialog.dismiss();




                            });
                }
            }
        });



        loginredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }







    private void saveprofile() {
        String dbEmail = cdEmail.getText().toString().trim();
        String dbUsername = cdUsername.getText().toString().trim();
        String dbContact = cdContact.getText().toString().trim();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().getDatabase();

        RegisteredData registeredData = new RegisteredData(userID, dbEmail, dbUsername, dbContact);
        firebaseDatabase.getReference().child("Trainers").child(userID).child("Profile").setValue(registeredData)
                .addOnCompleteListener(Register.this, task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(Register.this, "Saved Data", Toast.LENGTH_SHORT).show();
                        cdEmail.setText("");
                        cdPassword.setText("");
                        cdUsername.setText("");
                        cdContact.setText("");
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(Register.this, "Registration unsuccesful", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private boolean checkErrors() {
        String emailcheck = cdEmail.getText().toString().trim();
        String passwordcheck =cdPassword.getText().toString().trim();
        boolean validate = true;

        if(emailcheck.isEmpty()){
            cdEmail.setError("Email field must be filled.");
            validate = false;
        }

        if(passwordcheck.isEmpty()){
            cdEmail.setError("Password field cannot be empty.");
            validate = false;
        }

        if(!emailcheck.contains("@mmxp.com")){
            cdEmail.setError("Use @mmxp.com for your custom email.");
            validate = false;

        }
        if(passwordcheck.length() < 6){
            cdPassword.setError("Your password must be 6 characters or more");
            validate = false;
        }

            return validate;


    }




}