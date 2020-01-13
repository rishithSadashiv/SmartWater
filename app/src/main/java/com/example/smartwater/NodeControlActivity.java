package com.example.smartwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NodeControlActivity extends AppCompatActivity {

    Intent intent;
    TextView id, latitude, longitude, location, boreDrillDate, motorInstallationDate, borewellDepth, casingLength, pipeInLength, cableLength;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseNodes = database.getReference("Nodes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_control);

        intent = getIntent();
        String uid = intent.getStringExtra("UID");

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

        databaseNodes.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String latitud = dataSnapshot.child("lattitude").getValue().toString();
                String longitud = dataSnapshot.child("longitude").getValue().toString();
                String locatio = dataSnapshot.child("location").getValue().toString();
                String boreDrillDat = dataSnapshot.child("drillDate").getValue().toString();
                String motorInstallationDat = dataSnapshot.child("motorInstallationDate").getValue().toString();
                String borewellDept = dataSnapshot.child("depth").getValue().toString();
                String casingLengt = dataSnapshot.child("casingLength").getValue().toString();
                String pipeInLengt = dataSnapshot.child("pipeInLength").getValue().toString();
                String cableLengt = dataSnapshot.child("cableLength").getValue().toString();

                latitude.setText(latitud);
                longitude.setText(longitud);
                location.setText(locatio);
                boreDrillDate.setText(boreDrillDat);
                motorInstallationDate.setText(motorInstallationDat);
                borewellDepth.setText(borewellDept);
                casingLength.setText(casingLengt);
                pipeInLength.setText(pipeInLengt);
                cableLength.setText(cableLengt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
