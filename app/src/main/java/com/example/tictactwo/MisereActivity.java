package com.example.tictactwo;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MisereActivity extends Activity implements View.OnClickListener {


    //-----------------------------------------------Member Variables
    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private SoundPool soundPool;
    private int sound1;
    private int sound2;
    private int sound3;

   //-------------------------------------------------On Create method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misere_game);

        //-------------Getting references to the text views.

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);


//--------------------------------To play sounds from a sound pool start
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        sound1 = soundPool.load(this, R.raw.tic, 1);
        sound2 = soundPool.load(this, R.raw.laugh, 1);
        sound3 = soundPool.load(this, R.raw.win, 1);


//------------Assigned the button array to button references from xml layout. Used a nested loop instead of getting them one by one.

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }
//-------------anonymous inner class (reset button)
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sound1, 1, 1, 0, 0, 1);
                resetGame();


            }
        });
    }


    //-------On click method used to call any of the buttons created that are clicked.
    //----Checks if button that was clicked contain an empty string. And if that is not the case then it was all ready used. Ans if that's the case then leave it alone with "RETURN".
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("x");
            ((Button) v).setTextColor(Color.RED);


        } else {
            ((Button) v).setText("o");
            ((Button) v).setTextColor(Color.GREEN);
        }

        roundCount++;

        if (checkForLoser()) {
            if (player1Turn) {
                player1Loose();
            } else {
                player2Loose();
            }
        } else if (roundCount == 9) {
            draw();

        } else if (player1Points == 3) {
            finalWinner1();
            soundPool.play(sound3, 1, 1, 0, 0, 1);

        } else if (player2Points == 3) {
            finalWinner2();
            soundPool.play(sound3, 1, 1, 0, 0, 1);


        } else {
            player1Turn = !player1Turn;
        }

    }

    //-------Saving the text of buttons in a 2 dimensional array. Goes through all the buttons and saves them in a string array.


    private boolean checkForLoser() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
//-----------Using a string array to go through all the ROWS and then comparing 3 fields next to each other.
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {       //--------Checking to make sure their ain't not empty fields. If their are empty fields then there is not winner.
                return true;
            }
        }
//------------Using a string array to go through all the COLUMNS and then comparing 3 fields next to each other.
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {      //--------Checking to make sure their ain't not empty fields. If their are empty fields then there is not winner.
                return true;
            }
        }

        //---Checking for diagonal.
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }



    //------Methods created that are called.


    private void player1Loose() {
        player2Points++;
        LayoutInflater inflater=getLayoutInflater();
        View toastLayout=inflater.inflate(R.layout.custom_toast,(ViewGroup)findViewById(R.id.showCustom));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
        soundPool.play(sound2, 1, 1, 0, 0, 1);
        updatePointsText();
        resetBoard();
    }

    private void player2Loose() {
        player1Points++;
        LayoutInflater inflater=getLayoutInflater();
        View toastLayout=inflater.inflate(R.layout.custom_toast2,(ViewGroup)findViewById(R.id.showCustom));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
        soundPool.play(sound2, 1, 1, 0, 0, 1);
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        LayoutInflater inflater=getLayoutInflater();
        View toastLayout=inflater.inflate(R.layout.custom_toast3,(ViewGroup)findViewById(R.id.showCustom));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
        soundPool.play(sound2, 1, 1, 0, 0, 1);
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Loser You = " + player1Points);
        textViewPlayer2.setText("Loser Friend = " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();


    }

    private void finalWinner1(){
        resetGame();
        LayoutInflater inflater=getLayoutInflater();
        View toastLayout=inflater.inflate(R.layout.custom_toast4,(ViewGroup)findViewById(R.id.showCustom));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }


    private void finalWinner2(){
        resetGame();
        LayoutInflater inflater=getLayoutInflater();
        View toastLayout=inflater.inflate(R.layout.custom_toast5,(ViewGroup)findViewById(R.id.showCustom));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }




    //-------------for rotational purposes on phone. saves data when orientation is changed
    //-------------When devices is rotated the method is called and values will be saved in the outState Bundle.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerPoints", player1Points);
        outState.putInt("playerPoints", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    //------------Restoring game after a orientation change.
    //------------Here we used the savedInstanceState to read the outState values back into the app.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1points");
        player2Points = savedInstanceState.getInt("player2points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }
}

