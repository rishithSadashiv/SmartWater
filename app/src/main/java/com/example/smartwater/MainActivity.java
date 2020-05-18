// Written By: Rishith Sadashiv T N
package com.example.smartwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button login;
    Button signup;
    EditText email, password;
    ProgressBar progressbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.buttonSignupLogin);
        login = findViewById(R.id.buttonLogin);
        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPasswordLogin);
        progressbar = findViewById(R.id.progressBarLogin);
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();

                if (e.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                } else if (p.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                    email.setError("Please enter valid email");
                    email.requestFocus();
                } else if (p.length() < 6) {
                    password.setError("Minimum password length should be 6");
                    password.requestFocus();
                } else {
                    progressbar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressbar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                finish();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        //make another activity to update Nodes, the code is same as addNode but the id must be the same. If id is same, then the value which is present will be overridden. Else, new id is generated and the values will be assigned to that id
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}