package com.example.csman;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.CSMan.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button exit = findViewById(R.id.exitGame);
        exit.setVisibility(View.VISIBLE);
        exit.setOnClickListener(v -> {
            Intent backToGame = new Intent(this, MainActivity.class);
            startActivity(backToGame);
        });

        //Button enterAnswer = findViewById(R.id.go);
        //enterAnswer.setVisibility(View.VISIBLE);
        //enterAnswer.setOnClickListener(v -> {
            // use helper function
            //testMatch(); // get variables from library
            // if entered letter matches one of the letters in the answer, then the letter will become
            // visible in the answer text box
            // otherwise, one of geoff's head disappear


        String[] wordBank = {"Jake", "Caroline"};

        /*String[] wordBank = {"Pineapple", "Apple", "Car", "Jet", "Kite", "Champaign",
             "Facebook", "Friend", "Terminal", "Routine", "Recursion", "Squirrel", "Mosque", "Pet",
             "Janitor", "Complete", "Success", "Adjective", "Calculate", "Task", "Ticket", "Map",
             "Easter", "Zoom", "Xylophone", "Network", "Web", "Shrine", "Date", "Eloquent", "Emperor",
             "Beta", "Google", "Highlight", "Intuitive", "Joker", "Kind", "November", "Object", "Quarantine",
             "Remnant", "Sly", "Titan", "Uranus", "Velocity", "Plane", "Wonderful", "Computer", "Binary",
             "Jacket", "Potato"};
         */
        TextView answer = findViewById(R.id.answer);

        // randomly get a word from wordBank
        Random random = new Random();
        int randomIndex = random.nextInt(wordBank.length);
        String word = wordBank[randomIndex];

        int wordLength = word.length();
        String[] initial = new String[wordLength];
        String newInitial = StringBuffer(initial);


        char[] wordChar = word.toCharArray(); // java -> j,a,v,a

        int amountOfGuesses = wordChar.length; //total tries to guess a word.
        char[] playerGuess = new char[amountOfGuesses];

        // "_ _ _ _ _ _ _ _"
        for (int i = 0; i < playerGuess.length; i++) {
                playerGuess[i] = '_';
        }
        answer.setText(new String(playerGuess));

        Button enterAnswer = findViewById(R.id.go);
        enterAnswer.setVisibility(View.VISIBLE);
        enterAnswer.setOnClickListener(v -> {
            EditText input = findViewById(R.id.playerGuess);
            String userInput = input.getText().toString();
            //TextView newAnswer = findViewById(R.id.answer);
            char[] chars = userOutput(userInput, wordChar);
            answer.setText(new String(chars));

        });
    }

    /**
     *
     * @param userInput
     * @param guess
     * @return
     */
    public char[] userOutput(String userInput, char[] guess){

        char[] emptyChar = new char[guess.length];
        if (userInput.length() != 1) {
            return emptyChar;
        }
        char input = userInput.charAt(0);
        for (int i = 0; i < guess.length; i++) {
            if (input == guess[i]) {
                emptyChar[i] = input;
            }
        }
        return emptyChar;
    }

    /**
     *
     * @param stringArray
     * @return
     */
    public String StringBuffer(String[] stringArray) {
        for (int i = 0; i < stringArray.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringArray[i]);
            return stringBuilder.toString();
        }
    }

    ///**
     //* Test to see if player's letter guess matches the letters in the word provided.
     //* @param userInput user's input.
     //* @param answer default answer.
     //* @return the result.
     //*/
    /*public String[] testMatch(String userInput, String answer) {
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
            /*if (input == answer.charAt(answerIndex)) {
                newAnswer[answerIndex] = userInput;
                return newAnswer;
            }
        }
        return newAnswer;
    }

             */
}