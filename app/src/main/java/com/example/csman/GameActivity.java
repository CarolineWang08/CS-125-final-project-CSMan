package com.example.csman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.CSMan.MESSAGE";
    DatabaseHelper gameDb;
    Button exitButton;
    TextView answerLabel;
    TextView hintLabel;
    EditText textBoxUserInput;
    Button goButton;
    Button viewAll;
    Button clearDatabase;
    ImageView chance1;
    ImageView chance2;
    ImageView chance3;
    ImageView chance4;
    ImageView chance5;
    List<ImageView> chanceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameDb = new DatabaseHelper(this);

        exitButton = findViewById(R.id.exitGame);
        exitButton.setVisibility(View.VISIBLE);
        exitButton.setOnClickListener(v -> {
            Intent backToGame = new Intent(this, LaunchActivity.class);
            startActivity(backToGame);
        });

        viewAll = findViewById(R.id.viewPastAnswers);
        clearDatabase = findViewById(R.id.clearDatabase);

        chance1 = findViewById(R.id.chance1);
        chanceList.add(chance1);
        chance2 = findViewById(R.id.chance2);
        chanceList.add(chance2);
        chance3 = findViewById(R.id.chance3);
        chanceList.add(chance3);
        chance4 = findViewById(R.id.chance4);
        chanceList.add(chance4);
        chance5 = findViewById(R.id.chance5);
        chanceList.add(chance5);
        chanceSetVisibility(chanceList);
        // store the chance images into a list and set visibility

        String[] bank = {"Water", "Melon"};

        /* String[] bank = {"Pineapple", "Apple", "Car", "Jet", "Kite", "Champaign",
             "Facebook", "Friend", "Terminal", "Routine", "Recursion", "Squirrel", "Mosque", "Pet",
             "Janitor", "Complete", "Success", "Adjective", "Calculate", "Task", "Ticket", "Map",
             "Easter", "Zoom", "Xylophone", "Network", "Web", "Shrine", "Date", "Eloquent", "Emperor",
             "Beta", "Google", "Highlight", "Intuitive", "Joker", "Kind", "November", "Object", "Quarantine",
             "Remnant", "Sly", "Titan", "Uranus", "Velocity", "Plane", "Wonderful", "Computer", "Binary",
             "Jacket", "Potato", "Head", "Flamingo", "Water", "Melon"}; */
        String[] wordBank = changeToUpperCase(bank);

        answerLabel = findViewById(R.id.answer);
        hintLabel = findViewById(R.id.hint);
        hintLabel.setText("Enter a letter.");

        // randomly get a word from wordBank
        Random random = new Random();
        int randIndex = random.nextInt(wordBank.length);
        String answerWord = wordBank[randIndex];
        int answerWordLength = answerWord.length();

        String[] initial = new String[answerWordLength];
        for (int initialIndex = 0; initialIndex < answerWordLength; initialIndex++) {
            initial[initialIndex] = "_ ";
        }
        String answerString = stringBuffer(initial);
        answerLabel.setText(answerString);

        textBoxUserInput = findViewById(R.id.playerGuess); // user input from textbox

        goButton = findViewById(R.id.go);
        goButton.setOnClickListener(v -> {
            String userInputStr = textBoxUserInput.getText().toString(); // user input converted to string

            if (userInputStr.length() != 1) {
                hintLabel.setText("Only one-character input is allowed!");
                return;
            } // if the user's input is more than one character, then change hint message

            if (chanceList.size() == 0) {
                hintLabel.setText("You have run out of tries. Exit game and try again!");
            } // if the user runs out of chances, then change hint message

            if(answerString.equals(answerWord)) {
                hintLabel.setText("Congratulations! You have won the game!");
            } // if the user finds the word, then change hint message

            for (int answerWordIndex = 0; answerWordIndex < answerWordLength; answerWordIndex++) {
                char eachAnswerCharacter = answerWord.charAt(answerWordIndex);
                System.out.println("Input: " + userInputStr + ", eachChar: " + eachAnswerCharacter);
                char userInputChar = userInputStr.charAt(0);
                String newInitial = stringBuffer(initial);
                if (userInputChar == newInitial.charAt(answerWordIndex)) {
                    hintLabel.setText("You've already tried this letter! Choose another one!");
                    return;
                }
                if (userInputChar == eachAnswerCharacter) {
                    initial[answerWordIndex] = userInputStr;
                    answerLabel.setText(stringBuffer(initial));
                    hintLabel.setText("Good job! Try another letter!");
                    return;
                }
                // if the user has already tried the letter, change hint message
                System.out.println(stringBuffer(initial));
            }
            ImageView removedElement = chanceList.remove(chanceList.size() - 1);
            removedElement.setVisibility(View.GONE);
            hintLabel.setText("No matching character was found! Try again!");
            AddData();
        });

        viewAll();
        clearData();
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

    /**
     * Sets all ImageView objects inside a list to be visible.
     * @param imageList list containing imageView objects.
     */
    public void chanceSetVisibility(List<ImageView> imageList) {
        for (int listIndex = 0; listIndex < imageList.size(); listIndex++) {
            ImageView imageElement = imageList.get(listIndex);
            imageElement.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Save wrong letters into database
     */
    public void AddData() {
        boolean isInserted = gameDb.insertData(textBoxUserInput.getText().toString());
        if (isInserted == true) {
            Toast.makeText(GameActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Record past wrong letters
     */
    public void viewAll() {
        viewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = gameDb.getAllData();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Record", "No wrong letter found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("wrong_letter :" + res.getString(0) + "\n");
                        }
                        // show all data
                        showMessage("Record", buffer.toString());
                    }
                }
        );
    }

    /**
     * help viewAll() function to show wrong letter record
     * @param title title
     * @param Message message showed in viewAll() function
     */
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    /**
     * clear past records
     */
    public void clearData() {
        clearDatabase.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameDb.clearDatabase();
                    }
                }
        );
    }
}
