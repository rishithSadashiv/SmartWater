package com.example.smartwater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, AddNode.class);
        MainActivity.this.startActivity(intent);




        //make it into a login screen and later add a home activity
        //make another activity to update Nodes, the code is same as addNode but the id must be the same. If id is same, then the value which is present will be overridden. Else, ne id is generated and the values will be assigned to that id

    }
}
