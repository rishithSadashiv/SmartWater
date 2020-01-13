package com.example.smartwater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NodeControlActivity extends AppCompatActivity {

    Intent intent;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_control);

        intent = getIntent();
        String uid = intent.getStringExtra("UID");

        textView = findViewById(R.id.textViewNodeControlActivity);
        textView.setText(uid);


    }
}
