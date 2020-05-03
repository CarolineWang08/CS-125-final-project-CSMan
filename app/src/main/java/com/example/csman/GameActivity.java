package com.example.csman;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.CSMan.MESSAGE";
    DatabaseHelper gameDb;
    Button exitButton;
    TextView answerLabel;
    TextView hintLabel;
    EditText textBoxUserInput;
    Button goButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameDb = new DatabaseHelper(this);

        exitButton = findViewById(R.id.exitGame);
        exitButton.setVisibility(View.VISIBLE);
        exitButton.setOnClickListener(v -> {
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


        String[] wordBank = {"JAKE", "CAROLINE", "KITETSU", "ALICE"};

        /*String[] wordBank = {"Pineapple", "Apple", "Car", "Jet", "Kite", "Champaign",
             "Facebook", "Friend", "Terminal", "Routine", "Recursion", "Squirrel", "Mosque", "Pet",
             "Janitor", "Complete", "Success", "Adjective", "Calculate", "Task", "Ticket", "Map",
             "Easter", "Zoom", "Xylophone", "Network", "Web", "Shrine", "Date", "Eloquent", "Emperor",
             "Beta", "Google", "Highlight", "Intuitive", "Joker", "Kind", "November", "Object", "Quarantine",
             "Remnant", "Sly", "Titan", "Uranus", "Velocity", "Plane", "Wonderful", "Computer", "Binary",
             "Jacket", "Potato"};
         */

        answerLabel = findViewById(R.id.answer);
        hintLabel = findViewById(R.id.hint);
        hintLabel.setText("Enter a letter.");

        // randomly get a word from wordBank
        Random random = new Random();
        int randomIndex = random.nextInt(wordBank.length);
        String answerWord = wordBank[randomIndex];

        int answerWordLength = answerWord.length();

        String[] initial = new String[answerWordLength];
        for (int initialIndex = 0; initialIndex < answerWordLength; initialIndex++) {
            initial[initialIndex] = "_ ";
        }
        String answerString = stringBuffer(initial);

        answerLabel.setText(answerString);

        textBoxUserInput = findViewById(R.id.playerGuess);

        goButton = findViewById(R.id.go);
        goButton.setOnClickListener(v -> {
            String userInputStr = textBoxUserInput.getText().toString();

            if (userInputStr.length() != 1) {
                hintLabel.setText("Only one-character input is allowed!");
                return;
            }

            for (int i = 0; i < answerWordLength; i++) {
                char eachAnswerCharacter = answerWord.charAt(i);
                String eachAnswerString = Character.toString(eachAnswerCharacter);
                if (userInputStr.equals(eachAnswerString)) {
                    String[] answerLabelStrArray = answerLabel.getText().toString().split("");
                    answerLabelStrArray[i * 2] = userInputStr;
                    answerLabel.setText(stringBuffer(answerLabelStrArray));
                    hintLabel.setText("Good job! Try another letter!");
                    return;
                } else {
                    hintLabel.setText("No matching character was found! Try again!");
                    AddData();
                }
            }

        });


    }
    public void AddData() {
        goButton.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      boolean isInserted = gameDb.insertData(textBoxUserInput.getText().toString());
                      if (isInserted == true) {
                          Toast.makeText(GameActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                      } else {
                          Toast.makeText(GameActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                      }

                  }

              }
        );
    }

    /**
     * Helps transform a string array to a string.
     * @param stringArray input string array.
     * @return string with exact same characters.
     */
    public String stringBuffer(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            stringBuilder.append(stringArray[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Changes string in the wordBnak to all caps.
     * @param array word bank
     * @return new word bank with all caps
     */
    public String[] changeToUpperCase(String[] array) {
        String[] newArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].toUpperCase();
        }
        return newArray;
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
            // if the user's input matches one of the letters in the answer string, then it replaces
            // the blank underline
            if (input == answer.charAt(answerIndex)) {
                newAnswer[answerIndex] = userInput;
                return newAnswer;
            }
        }
        return newAnswer;
    } */
}