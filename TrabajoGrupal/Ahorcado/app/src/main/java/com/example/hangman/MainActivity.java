package com.example.hangman;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        hideNavigationBar();

        Button btn1 = (Button) findViewById(R.id.disney);
        Button btn2 = (Button) findViewById(R.id.dragon_ball);
        Button btn3 = (Button) findViewById(R.id.fairy_tail);
        Button btn4 = (Button) findViewById(R.id.friends);
        Button btn5 = (Button) findViewById(R.id.harry_potter);
        Button btn6 = (Button) findViewById(R.id.himym);
        Button btn7 = (Button) findViewById(R.id.mcu);
        Button btn8 = (Button) findViewById(R.id.naruto);
        Button btn9 = (Button) findViewById(R.id.tbbt);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "disney";
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "dragon_ball";
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "fairy_tail";
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "friends";
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "harry_potter";
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "himym";
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "mcu";
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "naruto";
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(MainActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                PlayActivity.file = "tbbt";
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideNavigationBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }
}
