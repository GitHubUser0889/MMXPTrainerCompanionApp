package com.example.trainercompanionapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button cdLogin;

    FirebaseAuth mAuth;
    EditText cdEmail, cdPassword;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cdEmail = findViewById(R.id.logemail);
        cdPassword = findViewById(R.id.logpassword);
        cdLogin = findViewById(R.id.login);



        mAuth = FirebaseAuth.getInstance();



        cdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = cdEmail.getText().toString().trim();
                String password = cdPassword.getText().toString().trim();



                if(checkErors()){


                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, task -> {
                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Login.this, "Log in Successful", Toast.LENGTH_SHORT).show();
                                    updateUI(user);
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Login.this, "Unsuccessful Attempt, try Again.", Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            });

                }




            }
        });
    }

    private boolean checkErors() {
        String emailcheck = cdEmail.getText().toString().trim();
        String passwordcheck = cdPassword.getText().toString().trim();
        boolean validate = true;
        if(emailcheck.isEmpty()){
            cdEmail.setError("Please enter an Email address.");
            validate =false;
        }
        if(passwordcheck.isEmpty()){
            cdPassword.setError("Please enter your Password.");
            validate = false;
        }
        return validate;

    }


    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(Login.this, "Unsuccessful Attempt, try Again.", Toast.LENGTH_SHORT).show();
        }
    }
}