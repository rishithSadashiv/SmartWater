package com.example.smartwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseNodes = database.getReference("Nodes");
    final Map<String, String> map = new HashMap<>();
    Button next;

    Spinner spinner;
    ProgressBar progressBar;

    Button addNodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addNodeButton = findViewById(R.id.buttonAddNode);
        progressBar = findViewById(R.id.progressBarHomeActivity);
        next = findViewById(R.id.buttonNext);


        addNodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddNode.class);
                startActivity(intent);
            }
        });

        getSpinnerValues();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = spinner.getSelectedItem().toString();
                String uid = map.get(location);
                Intent intent = new Intent(HomeActivity.this, NodeControlActivity.class);
                intent.putExtra("UID", uid);
                startActivity(intent);
            }
        });


    }


    private void getSpinnerValues() {

        progressBar.setVisibility(View.VISIBLE);
        databaseNodes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> list = new ArrayList();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);
                    map.put(location, id);
                    list.add(location);
                }

                progressBar.setVisibility(View.GONE);
                spinner = findViewById(R.id.spinnerHomeActivity);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item, list);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
