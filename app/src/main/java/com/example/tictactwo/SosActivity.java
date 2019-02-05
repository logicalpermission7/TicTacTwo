package com.example.tictactwo;

import android.view.View;
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

    private Button[][] buttons = new Button[6][6];

    private boolean playerOneTurn = true;

    private int roundCount;
    private int playerOnePoints;
    private int playerTwoPoints;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    //private TextView textViewTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sos_game);
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        //textViewTurn = findViewById(R.id.tv_turn);


        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button reset = findViewById(R.id.button_reset);
        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (playerOneTurn) {
            //textViewTurn.setText("Player 1's Turn");
            //textViewTurn.setTextColor(Color.parseColor("#00FF00));
            //((Button)v).setText("S");
        } else {
            //textViewTurn.setText("Player 2's Turn");
            //textViewTurn.setTextColor(Color.parseColor("#FF0000));
            //((Button)v).setText("O");
        }

        roundCount++;
        if (checkForWin()) {
            if (playerOneTurn) {
                playerOneWins();
            } else {
                playerTwoWins();
            }
        }
        if (roundCount == 36) {
            //toast draw
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[6][6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        return false;
    }

        private void playerOneWins() {
            playerOnePoints++;
            //textViewTurn.setText("Player 1 Wins");
            updatePointsText();
            resetBoard();
        }

        private void playerTwoWins() {
            playerTwoPoints++;
            //textViewTurn.setText("Player 2 Wins");
            updatePointsText();
            resetBoard();
        }

        private void updatePointsText() {
            textViewPlayer1.setText("Player 1: " + playerOnePoints);
            textViewPlayer2.setText("Player 2: " + playerTwoPoints);
        }


        private void resetBoard() {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    buttons[i][j].setText("");
                }
            }
            roundCount = 0;
            playerOneTurn = true;
        }
    }

