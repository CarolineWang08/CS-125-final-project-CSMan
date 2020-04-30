package com.example.csman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.CSMan.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button exit = findViewById(R.id.exitGame);
        exit.setVisibility(View.VISIBLE);
        exit.setOnClickListener(v -> {
            Intent backToGame = new Intent(this, LaunchActivity.class);
            startActivity(backToGame);
        });

        Button enterAnswer = findViewById(R.id.go);
        enterAnswer.setOnClickListener(v -> {
            // use helper function
            //testMatch(); // get variables from library
            // if entered letter matches one of the letters in the answer, then the letter will become
            // visible in the answer text box
            // otherwise, one of geoff's head disappears

            EditText input = findViewById(R.id.playerGuess);
            String userInput = input.getText().toString();

            String[] wordBank = {"Pineapple", "Apple", "Car", "Jet", "Kite", "Champaign",
            "Facebook", "Friend", "Terminal", "Routine", "Recursion", "Squirrel", "Mosque", "Pet",
            "Janitor", "Complete", "Success", "Adjective", "Calculate", "Task", "Ticket", "Map",
            "Easter", "Zoom", "Xylophone", "Network", "Web", "Shrine", "Date", "Eloquent", "Emperor",
            "Beta", "Google", "Highlight", "Intuitive", "Joker", "Kind", "November", "Object", "Quarantine",
            "Remnant", "Sly", "Titan", "Uranus", "Velocity", "Plane", "Wonderful", "Computer", "Binary",
            "Jacket", "Potato"}; // word bank
            TextView answer = findViewById(R.id.answer);
            // convert user's input into a string
            //randomly get a string array from our software library
            String[] temp;
            for (int wbIndex = 0; wbIndex < wordBank.length; wbIndex++) {
                temp = testMatch(userInput, wordBank[wbIndex]);
                String output = "";
                for (String str : temp) {
                    output = output + str;
                }
                answer.setText(output);
            }
            //String output = tmp.toString()
        });
    }

    /**
     * Test to see if player's letter guess matches the letters in the word provided.
     * @param userInput user's input.
     * @param answer default answer.
     * @return the result.
     */
    public String[] testMatch(String userInput, String answer) {
        int answerLength = answer.length();
        // this string array is made up of one-character strings
        String[] initial = new String[answerLength]; // initial string array has no answer filled in yet
        for (int index = 0; index < initial.length; index++) {
            initial[index] = "_ ";
        } // now the value at every index in the array is "_ "
        if (userInput.length() != 1) {
            return initial;
        }
        char input = userInput.charAt(0);
        String[] newAnswer = initial;
        for (int answerIndex = 0; answerIndex < answer.length(); answerIndex++) {
            /* if the user's input matches one of the letters in the answer string, then it replaces
            the blank underline */
            if (input == answer.charAt(answerIndex)) {
                newAnswer[answerIndex] = userInput;
                return newAnswer;
            }
        }
        return newAnswer;
    }

    /**
     *
     */
}