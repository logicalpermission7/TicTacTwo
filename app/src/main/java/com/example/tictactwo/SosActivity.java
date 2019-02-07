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


public class SosActivity extends Activity implements View.OnClickListener {

    private Button[][] buttons = new Button[4][4];

    private boolean playerTurn = true;


    private int roundCount;

    private int playerRounds;


    private TextView textViewPlayer1;
    private SoundPool soundPool;
    private int sound1;
    private int sound2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sos_game);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
//-----------------------------------------------------------------------To play sounds from a sound pool start
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
        sound2 = soundPool.load(this, R.raw.yes, 1);



//-----------------------------------------------------------------------To play sounds from a sound pool end
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sound1, 1, 1, 0, 0, 1);
                resetGame();


            }
        });
    }



    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (playerTurn) {
            ((Button) v).setText("S");
            ((Button) v).setTextColor(Color.RED);

        }else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.WHITE);
        }

        roundCount++;

        if (checkForWin()) {
            if (playerTurn) {
                playerWin();
            }
        } else if (roundCount == 16) {
            draw();

        } else if (playerRounds == 20) {
            finalWinner1();
            soundPool.play(sound2, 1, 1, 0, 0, 1);


        } else {
            playerTurn = !playerTurn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 4; i++) {
            if (field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[2][1])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        if (field[0][3].equals(field[2][3])
                && !field[0][3].equals("")) {
            return true;
        }

        if (field[2][3].equals(field[0][1])
                && !field[2][3].equals("")) {
            return true;
        }

        if (field[3][3].equals(field[3][1])
                && !field[3][3].equals("")) {
            return true;
        }

        if (field[3][0].equals(field[1][2])
                && !field[3][0].equals("")) {
            return true;
        }

        if (field[0][3].equals(field[2][1])
                && !field[0][3].equals("")) {
            return true;
        }

        if (field[1][3].equals(field[1][1])
                && !field[1][3].equals("")) {
            return true;
        }

        if (field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[3][3].equals(field[1][1])
                && !field[3][3].equals("")) {
            return true;
        }

        if (field[0][3].equals(field[0][1])
                && !field[0][3].equals("")) {
            return true;
        }

        if (field[3][1].equals(field[1][1])
                && !field[3][1].equals("")) {
            return true;
        }

        if (field[2][3].equals(field[2][1])
                && !field[2][3].equals("")) {
            return true;
        }

        if (field[3][0].equals(field[1][0])
                && !field[3][0].equals("")) {
            return true;
        }

        if (field[3][2].equals(field[1][2])
                && !field[3][2].equals("")) {
            return true;
        }

        if (field[3][3].equals(field[1][3])
                && !field[3][3].equals("")) {
            return true;
        }

        return false;
    }




    private void playerWin() {
        playerRounds++;
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
        textViewPlayer1.setText( playerRounds + " Out of 20 Rounds ");

    }

    private void resetBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        playerTurn = true;
    }





    private void resetGame() {
        playerRounds = 0;
        updatePointsText();
        resetBoard();


    }

    private void finalWinner1(){
        resetBoard();
        LayoutInflater inflater=getLayoutInflater();
        View toastLayout=inflater.inflate(R.layout.custom_toast4,(ViewGroup)findViewById(R.id.showCustom));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }




    // for rotational purposes on phone. saves data when orientation is changed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerPoints", playerRounds);
        outState.putBoolean("player1Turn", playerTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerRounds = savedInstanceState.getInt("player1points");
        playerTurn = savedInstanceState.getBoolean("player1Turn");

    }
}

