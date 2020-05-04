package com.example.csman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.CSMan.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.nameInput);
        String nameInput = name.getText().toString();

        Button start = findViewById(R.id.setGame);
        start.setOnClickListener(v -> {
            Intent launchGame = new Intent(this, LaunchActivity.class);
            if ()
            launchGame.putExtra("name", nameInput);
            startActivity(launchGame);
        });
    }

}
