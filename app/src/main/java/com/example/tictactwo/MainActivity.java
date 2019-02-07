package com.example.tictactwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int Tic_sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //-----------------------------------------------------------------------sounds START
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
        Tic_sound = soundPool.load(this, R.raw.tic, 1);

        //-------------------------------------------------------------------------sounds END

        //---------------Methods used for buttons------------------------

        Button misereButton = findViewById(R.id.button_misere);
        misereButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                soundPool.play(Tic_sound, 1, 1, 0, 0, 1);
                misereGame();

            }
        });


        final Button sosButton = findViewById(R.id.button_sos);
        sosButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                soundPool.play(Tic_sound, 1, 1, 0, 0, 1);
                sosGame();

            }
        });


        Button webButton = findViewById(R.id.button_how);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    soundPool.play(Tic_sound, 1, 1, 0, 0, 1);
                    instructionsPage();
            }
        });


    }
    //------------------End of Methods------------------------------------------


//----------------Functions created to allow buttons to work and show additional pages---------

    private void misereGame(){
        Intent intent = new Intent(getApplicationContext(),
                MisereActivity.class);
        startActivity(intent);

    }

    private void sosGame(){
        Intent intent = new Intent(getApplicationContext(),
                SosActivity.class);
        startActivity(intent);

    }

    private void instructionsPage(){
        Intent intent = new Intent(getApplicationContext(),
                InstructionsActivity.class);
        startActivity(intent);

    }


}


