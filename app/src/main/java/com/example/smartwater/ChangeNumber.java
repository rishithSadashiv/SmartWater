// Written By: Rishith Sadashiv T N
package com.example.smartwater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.smartwater.NodeControlActivity.updatePhoneNumber;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeNumber extends AppCompatActivity {

    TextView currentNumber;
    EditText newNumber;
    Button submitButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseNodes = database.getReference("Nodes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);

        final String uid = getIntent().getStringExtra("UID");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        currentNumber = findViewById(R.id.currentNumberTextField);
        String current = "Current Number : " + phoneNumber;
        currentNumber.setText(current);
        newNumber = findViewById(R.id.NewNumberEditText);
        submitButton = findViewById(R.id.submitButtonSecondActivity);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num = "Current Number : " + newNumber.getText().toString();
                currentNumber.setText(num);
                if (newNumber.getText().toString().equals("") || newNumber.getText().toString().length() < 10 || newNumber.getText().toString().length() > 10) {
                    Toast.makeText(ChangeNumber.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                } else {
                    updatePhoneNumber(newNumber.getText().toString().trim());
                    databaseNodes.child(uid).child("phoneNumber").setValue(newNumber.getText().toString().trim());
                }
            }
        });
    }
}
