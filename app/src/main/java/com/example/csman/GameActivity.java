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

        initialize();

        // randomly get a word from wordBank
        String answerWord = getAnswerWord();
        int answerWordLength = answerWord.length();

        String[] initial = new String[answerWordLength];
        for (int initialIndex = 0; initialIndex < answerWordLength; initialIndex++) {
            initial[initialIndex] = "_ ";
        }
        String answerString = stringBuffer(initial);
        answerLabel.setText(answerString);

        playGame(answerString, answerWord, answerWordLength, initial);

    }

    /**
     * Initialize necessary objects.
     */
    public void initialize() {
        gameDb = new DatabaseHelper(this);
        exitButton = findViewById(R.id.exitGame);
        exitButton.setVisibility(View.VISIBLE);
        goButton = findViewById(R.id.go);

        textBoxUserInput = findViewById(R.id.playerGuess); // user input from textbox

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

        answerLabel = findViewById(R.id.answer);
        hintLabel = findViewById(R.id.hint);
        hintLabel.setText("Enter a letter.");
    }

    /**
     * Get the answer key from the word bank.
     * @return the answer in String
     */
    public String getAnswerWord() {
        String[] bank = {"Pineapple", "Apple", "Car", "Jet", "Kite", "Champaign",
                "Facebook", "Friend", "Terminal", "Routine", "Recursion", "Squirrel", "Mosque", "Pet",
                "Janitor", "Complete", "Success", "Adjective", "Calculate", "Task", "Ticket", "Map",
                "Easter", "Zoom", "Xylophone", "Network", "Web", "Shrine", "Date", "Eloquent", "Emperor",
                "Beta", "Google", "Highlight", "Intuitive", "Joker", "Kind", "November", "Object", "Quarantine",
                "Remnant", "Sly", "Titan", "Uranus", "Velocity", "Plane", "Wonderful", "Computer", "Binary",
                "Jacket", "Potato", "Head", "Flamingo", "Water", "Melon"};
        Random random = new Random();
        int randIndex = random.nextInt(bank.length);
        return bank[randIndex].toUpperCase();
    }

    /**
     * Handle the user playing the game.
     * @param answerString answer from the answerLabel in String
     * @param answerWord answer word in String
     * @param answerWordLength answer word length in int
     * @param initial array of String that represents the answerLabel
     */
    public void playGame(String answerString, String answerWord, int answerWordLength, String[] initial) {

        exitButton.setOnClickListener(v -> {
            clearData();
            Intent backToGame = new Intent(this, LaunchActivity.class);
            startActivity(backToGame);
        });

        goButton.setOnClickListener(v -> {
            String userInputStr = textBoxUserInput.getText().toString().toUpperCase(); // user input converted to string

            if (userInputStr.length() != 1) {
                hintLabel.setText("Only one-character input is allowed!");
            } else if (chanceList.size() == 0) {
                hintLabel.setText("You have run out of tries. Exit game and try again!" + "\n" +
                                    "The correct word is: " + answerWord);
            } else if (!answerWord.contains(userInputStr)) {
                ImageView removedElement = chanceList.remove(chanceList.size() - 1);
                removedElement.setVisibility(View.GONE);
                hintLabel.setText("No matching character was found! Try again!");
                AddData();
            } else {
                for (int answerWordIndex = 0; answerWordIndex < answerWordLength; answerWordIndex++) {
                    char eachAnswerCharacter = answerWord.charAt(answerWordIndex);
                    char userInputChar = userInputStr.charAt(0);
                    String newInitial = stringBuffer(initial);
                    if (userInputChar == newInitial.charAt(answerWordIndex)) {
                        hintLabel.setText("You've already tried this letter! Choose another one!");
                    } else if (userInputChar == eachAnswerCharacter) {
                        initial[answerWordIndex] = userInputStr;
                        answerLabel.setText(stringBuffer(initial));
                        hintLabel.setText("Good job! Try another letter!");
                    }
                }
            }
            // User has won
            if (answerLabel.getText().toString().equals(answerWord)) {
                hintLabel.setText("Congratulations! You have won the game!");
            }
        });

        clearDatabase.setOnClickListener(v -> {
            clearData();
        });

        viewAll.setOnClickListener(v -> {
            showMessage("Record", gameDb.getDataAsString());
        });

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
        if (isInserted) {
            Toast.makeText(GameActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
        }
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
     * clear past records.
     */
    public void clearData() {
        gameDb.clearDatabase();
    }
}
