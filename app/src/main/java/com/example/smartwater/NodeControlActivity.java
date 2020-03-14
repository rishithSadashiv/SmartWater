package com.example.smartwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NodeControlActivity extends AppCompatActivity {

    Intent intent;
    TextView id, latitude, longitude, location, boreDrillDate, motorInstallationDate, borewellDepth, casingLength, pipeInLength, cableLength, phoneNum;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseNodes = database.getReference("Nodes");
    static TextView commandGivenTextView, receivedInfoTextView;
    static String phoneNumber = "9035633154";
    static String receivedMessage = "Received Info : ";
    Button btn1, btn2, btn3, btn4, btn5;
    Button changeNumber;

    // https://www.wikihow.com/Execute-HTTP-POST-Requests-in-Android   try to put switch to select sms or http

    public static void updateReceivedMessage(String ob) {
        receivedMessage = ob;
        receivedInfoTextView.setText("Received Info : " + ob);
    }

    public static void updatePhoneNumber(String ob) {
        phoneNumber = ob;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_control);

        intent = getIntent();
        final String uid = intent.getStringExtra("UID");
        id = findViewById(R.id.textViewId);
        id.setText(uid);
        latitude = findViewById(R.id.textViewlatitude);
        longitude = findViewById(R.id.textViewLongitude);
        location = findViewById(R.id.textViewLocation);
        boreDrillDate = findViewById(R.id.textViewBoreDrillDate);
        motorInstallationDate = findViewById(R.id.textViewMotorInstallationDate);
        borewellDepth = findViewById(R.id.textViewBorewellDepth);
        casingLength = findViewById(R.id.textViewCasingLength);
        pipeInLength = findViewById(R.id.textViewPipeInLength);
        cableLength = findViewById(R.id.textViewCableLength);
        phoneNum = findViewById(R.id.textViewPhoneNUmber);
        commandGivenTextView = findViewById(R.id.textViewCommandGiven);
        receivedInfoTextView = findViewById(R.id.textViewReceivedInfo);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        changeNumber = findViewById(R.id.buttonChangeNumber);

        databaseNodes.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String latitud = dataSnapshot.child("lattitude").getValue().toString();
                    String longitud = dataSnapshot.child("longitude").getValue().toString();
                    String locatio = dataSnapshot.child("location").getValue().toString();
                    String boreDrillDat = dataSnapshot.child("drillDate").getValue().toString();
                    String motorInstallationDat = dataSnapshot.child("motorInstallationDate").getValue().toString();
                    String borewellDept = dataSnapshot.child("depth").getValue().toString();
                    String casingLengt = dataSnapshot.child("casingLength").getValue().toString();
                    String pipeInLengt = dataSnapshot.child("pipeInLength").getValue().toString();
                    String cableLengt = dataSnapshot.child("cableLength").getValue().toString();
                    String phoneNumbe = dataSnapshot.child("phoneNumber").getValue().toString();

                    latitude.setText(latitud);
                    longitude.setText(longitud);
                    location.setText(locatio);
                    boreDrillDate.setText(boreDrillDat);
                    motorInstallationDate.setText(motorInstallationDat);
                    borewellDepth.setText(borewellDept);
                    casingLength.setText(casingLengt);
                    pipeInLength.setText(pipeInLengt);
                    cableLength.setText(cableLengt);
                    phoneNum.setText(phoneNumbe);
                    phoneNumber = phoneNumbe;
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(phoneNumber, "1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(phoneNumber, "2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(phoneNumber, "3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(phoneNumber, "4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(phoneNumber, "5");
            }
        });

        receivedInfoTextView.setMovementMethod(new ScrollingMovementMethod());

        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NodeControlActivity.this, ChangeNumber.class);
                intent.putExtra("UID", uid);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        });


        //write code to send sms with current command, decide whether the phone number of user is enough or email uid is required.

    }

    private void sendSMS(String phoneNumber1, String message) {

        //https://developer.android.com/training/permissions/requesting.html

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber1, null, message, null, null);

        } catch (Exception e) {
            Toast.makeText(NodeControlActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        commandGivenTextView.setText("Sent Text : " + message);
        receivedInfoTextView.setText("Received Info:");
    }
}
