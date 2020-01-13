package com.example.smartwater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNode extends AppCompatActivity {

    Button addNodeButton;
    EditText lattitude, longitude, location, drillDate, motorInstallationDate, depth, casingLength, pipeInLength, cableLength;

    DatabaseReference databaseNode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node);

        addNodeButton = findViewById(R.id.buttonAddNode);
        lattitude = findViewById(R.id.editTextLattitude);
        longitude = findViewById(R.id.editTextLongitude);
        location = findViewById(R.id.editTextLocationDetails);
        drillDate = findViewById(R.id.editTextDrillDate);
        motorInstallationDate = findViewById(R.id.editText6MotorInstallationDate);
        depth = findViewById(R.id.editTextDepth);
        casingLength = findViewById(R.id.editTextLength);
        pipeInLength = findViewById(R.id.editTextPipeInLength);
        cableLength = findViewById(R.id.editTextCableLength);

        databaseNode = FirebaseDatabase.getInstance().getReference("Nodes"); //create Nodes table in database


        addNodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNode();
            }
        });


    }

    private void addNode() {
        String parameter1 = lattitude.getText().toString().trim();
        String parameter2 = longitude.getText().toString().trim();
        String parameter3 = location.getText().toString().trim();
        String parameter4 = drillDate.getText().toString().trim();
        String parameter5 = motorInstallationDate.getText().toString().trim();
        String parameter6 = depth.getText().toString().trim();
        String parameter7 = casingLength.getText().toString().trim();
        String parameter8 = pipeInLength.getText().toString().trim();
        String parameter9 = cableLength.getText().toString().trim();

        if (!TextUtils.isEmpty(parameter1) && !TextUtils.isEmpty(parameter2) && !TextUtils.isEmpty(parameter3) && !TextUtils.isEmpty(parameter4) && !TextUtils.isEmpty(parameter5) && !TextUtils.isEmpty(parameter6) && !TextUtils.isEmpty(parameter7) && !TextUtils.isEmpty(parameter8) && !TextUtils.isEmpty(parameter9)) {
            String id = databaseNode.push().getKey();

            Node node = new Node(id, parameter1, parameter2, parameter3, parameter4, parameter5, parameter6, parameter7, parameter8, parameter9);

            databaseNode.child(id).setValue(node);

            Toast.makeText(this, "Node added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Fill all inputs", Toast.LENGTH_LONG).show();
        }


    }
}
