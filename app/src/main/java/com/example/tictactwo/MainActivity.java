package com.example.tictactwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.net.Uri;

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


        Button notaktoButton = findViewById(R.id.button_notakto);
        notaktoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                soundPool.play(Tic_sound, 1, 1, 0, 0, 1);
                notaktoGame();

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
                // what happens when user presses More
                soundPool.play(Tic_sound, 1, 1, 0, 0, 1);
                final String URL = "https://en.wikipedia.org/wiki/Tic-tac-toe_variants";
                Uri uri = Uri.parse(URL);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });


    }

    private void notaktoGame(){
        Intent intent = new Intent(getApplicationContext(),
                NotaktoActivity.class);
        startActivity(intent);

    }

    private void sosGame(){
        Intent intent = new Intent(getApplicationContext(),
                SosActivity.class);
        startActivity(intent);

    }
}


