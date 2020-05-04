package com.example.csman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class LaunchActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.CSMan.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Button startGame = findViewById(R.id.start);
        startGame.setOnClickListener(v -> {
            Intent Game = new Intent(this, GameActivity.class);
            startActivity(Game);
        });

        TextView welcomeMessage = findViewById(R.id.welcome);
        Intent intent = getIntent();
        String nameInput = intent.getStringExtra("name");
        welcomeMessage.setText("Welcome to CSMAN2020," + " " + nameInput);
    }
}