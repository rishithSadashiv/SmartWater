// written by Rishith Sadashiv T N
package com.example.smartwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;


public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseNodes = database.getReference("Nodes");
    final Map<String, String> map = new HashMap<>();
    Button next;
    Spinner spinner;
    ProgressBar progressBar;
    Button addNodeButton;

    Button refresh;

    public static final int RequestPermissionCode = 7;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addNodeButton = findViewById(R.id.buttonAddNode);
        progressBar = findViewById(R.id.progressBarHomeActivity);
        next = findViewById(R.id.buttonNext);

        refresh = findViewById(R.id.btnloginhttp);

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
                try {
                    String location = spinner.getSelectedItem().toString();
                    String uid = map.get(location);
                    Intent intent = new Intent(HomeActivity.this, NodeControlActivity.class);
                    intent.putExtra("UID", uid);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

                // checking for SMS permission
                // If All permission is enabled successfully then this block will execute.

                if (CheckingPermissionIsEnabledOrNot()) {
                    //Toast.makeText(HomeActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
                }
                // If permission is not enabled then else condition will execute.
                else {
                    //Calling method to enable permission.
                    RequestMultiplePermission();
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            //Your code goes here
                            String response = HttpRequest.login();
                            //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

            }
        });

    }

    private void getSpinnerValues() {

        progressBar.setVisibility(View.VISIBLE);
        databaseNodes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    List<String> list = new ArrayList();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String id = snapshot.child("id").getValue(String.class);
                        String location = snapshot.child("location").getValue(String.class);
                        map.put(location, id);
                        list.add(location);
                    }

                    progressBar.setVisibility(View.GONE);
                    spinner = findViewById(R.id.spinnerHomeActivity);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                    spinner.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // link used for requesting multiple permissions
    //https://androidjson.com/android-request-multiple-runtime-permissions/

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{
                SEND_SMS,
                RECEIVE_SMS,
                READ_SMS
        }, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean sendSMSPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean receiveSMSPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readSMSPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (sendSMSPermission && readSMSPermission && receiveSMSPermission) {
                        Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }
                break;
        }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {
        int firstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int secondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        int thirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);

        return firstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                secondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                thirdPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
}
