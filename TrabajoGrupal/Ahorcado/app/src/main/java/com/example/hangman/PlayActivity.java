package com.example.hangman;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    public static String file = "";
    public static String character = "";
    public static StringBuilder hidden = new StringBuilder();
    public static StringBuilder guess = new StringBuilder();
    public static int attempts = 6;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.play);

        hideNavigationBar();

        Button btn1 = (Button) findViewById(R.id.new_character);
        Button btn2 = (Button) findViewById(R.id.go_back);
        Button q = (Button) findViewById(R.id.Q);
        Button w = (Button) findViewById(R.id.W);
        Button e = (Button) findViewById(R.id.E);
        Button r = (Button) findViewById(R.id.R);
        Button t = (Button) findViewById(R.id.T);
        Button y = (Button) findViewById(R.id.Y);
        Button u = (Button) findViewById(R.id.U);
        Button i = (Button) findViewById(R.id.I);
        Button o = (Button) findViewById(R.id.O);
        Button p = (Button) findViewById(R.id.P);
        Button a = (Button) findViewById(R.id.A);
        Button s = (Button) findViewById(R.id.S);
        Button d = (Button) findViewById(R.id.D);
        Button f = (Button) findViewById(R.id.F);
        Button g = (Button) findViewById(R.id.G);
        Button h = (Button) findViewById(R.id.H);
        Button j = (Button) findViewById(R.id.J);
        Button k = (Button) findViewById(R.id.K);
        Button l = (Button) findViewById(R.id.L);
        Button z = (Button) findViewById(R.id.Z);
        Button x = (Button) findViewById(R.id.X);
        Button c = (Button) findViewById(R.id.C);
        Button v = (Button) findViewById(R.id.V);
        Button b = (Button) findViewById(R.id.B);
        Button n = (Button) findViewById(R.id.N);
        Button m = (Button) findViewById(R.id.M);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(PlayActivity.this, R.raw.button_click);
                buttonClick.start();
                getCharacter(file);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer buttonClick = MediaPlayer.create(PlayActivity.this, R.raw.button_click);
                buttonClick.start();
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("q");
            }
        });

        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("w");
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("e");
            }
        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("r");
            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("t");
            }
        });

        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("y");
            }
        });

        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("u");
            }
        });

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("i");
            }
        });

        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("o");
            }
        });

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("p");
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("a");
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("s");
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("d");
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("f");
            }
        });

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("g");
            }
        });

        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("h");
            }
        });

        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("j");
            }
        });

        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("k");
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("l");
            }
        });

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("z");
            }
        });

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("x");
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("c");
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("v");
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("b");
            }
        });

        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("n");
            }
        });

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLetter("m");
            }
        });

        getCharacter(file);
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

    void getCharacter(String file) {
        String line = "";
        List characters = new ArrayList();
        Random rand = new Random();
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        if (file == "disney") {
            is = this.getResources().openRawResource(R.raw.disney);
        } else if (file == "dragon_ball"){
            is = this.getResources().openRawResource(R.raw.dragon_ball);
        } else if (file == "fairy_tail"){
            is = this.getResources().openRawResource(R.raw.fairy_tail);
        } else if (file == "friends"){
            is = this.getResources().openRawResource(R.raw.friends);
        } else if (file == "harry_potter"){
            is = this.getResources().openRawResource(R.raw.harry_potter);
        } else if (file == "himym"){
            is = this.getResources().openRawResource(R.raw.himym);
        } else if (file == "mcu"){
            is = this.getResources().openRawResource(R.raw.mcu);
        } else if (file == "naruto"){
            is = this.getResources().openRawResource(R.raw.naruto);
        } else {
            is = this.getResources().openRawResource(R.raw.tbbt);
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                characters.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        character = characters.get(rand.nextInt(characters.size())).toString();
        character = character.replaceAll(" ", "_");

        hidden = new StringBuilder();
        guess = new StringBuilder();

        for (int i = 0; i < character.length(); i++) {
            if (Character.isLetter(character.charAt(i))) {
                hidden.append("*");
            } else {
                hidden.append(character.charAt(i));
            }
        }

        for (int i = 0; i < character.length(); i++) {
            guess.append(character.charAt(i));
        }

        TextView hc = (TextView) findViewById(R.id.hc);
        hc.setText(hidden);

        attempts = 6;
        TextView attemptsT = (TextView) findViewById(R.id.attemptsT);
        attemptsT.setText("Attempts: " + attempts);

        ImageView drawing = (ImageView) findViewById(R.id.drawing);
        drawing.setImageResource(R.drawable.play_6);
    }

    void getLetter(String letter) {
        MediaPlayer buttonClick = MediaPlayer.create(PlayActivity.this, R.raw.button_click);
        buttonClick.start();
        if (guess.toString().contains(letter.toUpperCase())) {
            int i = guess.indexOf(letter.toUpperCase());
            hidden.setCharAt(i, letter.toUpperCase().charAt(0));
            guess.setCharAt(i, '*');
            TextView hc = (TextView) findViewById(R.id.hc);
            hc.setText(hidden);
        } else if (guess.toString().contains(letter)) {
            int i = guess.indexOf(letter);
            hidden.setCharAt(i, letter.charAt(0));
            guess.setCharAt(i, '*');
            TextView hc = (TextView) findViewById(R.id.hc);
            hc.setText(hidden);
        } else {
            --attempts;
            MediaPlayer drawingSound = MediaPlayer.create(PlayActivity.this,
                    R.raw.drawing_sound);
            drawingSound.start();
            TextView attemptsT = (TextView) findViewById(R.id.attemptsT);
            attemptsT.setText("Attempts: " + attempts);
            ImageView drawing = (ImageView) findViewById(R.id.drawing);
            if (attempts == 5) {
                drawing.setImageResource(R.drawable.play_5);
            } else if (attempts == 4) {
                drawing.setImageResource(R.drawable.play_4);
            } else if (attempts == 3) {
                drawing.setImageResource(R.drawable.play_3);
            } else if (attempts == 2) {
                drawing.setImageResource(R.drawable.play_2);
            } else if (attempts == 1) {
                drawing.setImageResource(R.drawable.play_1);
            } else if (attempts == 0) {
                drawing.setImageResource(R.drawable.play_0);
            }
        }
        if (hidden.toString().equals(character)) {
            MediaPlayer winSound = MediaPlayer.create(PlayActivity.this, R.raw.you_win);
            winSound.start();
            getResult("win");
            ImageView drawing = (ImageView) findViewById(R.id.drawing);
            drawing.setImageResource(R.drawable.play_alive);
        } else if (attempts == 0) {
            MediaPlayer loseSound = MediaPlayer.create(PlayActivity.this, R.raw.you_lose);
            loseSound.start();
            getResult("lose");
            ImageView drawing = (ImageView) findViewById(R.id.drawing);
            drawing.setImageResource(R.drawable.play_dead);
        }
    }

    void getResult(String win) {
        if (win == "win") {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popresult, null);

            TextView result = (TextView)popupView.findViewById(R.id.result);
            result.setText("You win!");

            TextView result2 = (TextView)popupView.findViewById(R.id.result2);
            String answer = "The character was: " + character.replaceAll("_", " ");
            result2.setText(answer);

            int width = 800;
            int height = 800;
            final PopupWindow popResult = new PopupWindow(popupView, width, height);

            Button playAgain = (Button)popupView.findViewById(R.id.playAgain);
            Button newCategory = (Button)popupView.findViewById(R.id.newCategory);

            playAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer buttonClick = MediaPlayer.create(PlayActivity.this, R.raw.button_click);
                    buttonClick.start();
                    getCharacter(file);
                    popResult.dismiss();
                }
            });

            newCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer buttonClick = MediaPlayer.create(PlayActivity.this, R.raw.button_click);
                    buttonClick.start();
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                    popResult.dismiss();
                }
            });

            View v = popResult.getContentView();
            popResult.showAtLocation(v, Gravity.CENTER, 0, 150);

        } else {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popresult, null);

            TextView result = (TextView)popupView.findViewById(R.id.result);
            result.setText("You lose!");

            TextView result2 = (TextView)popupView.findViewById(R.id.result2);
            String answer = "The character was: " + character.replaceAll("_", " ");
            result2.setText(answer);

            int width = 800;
            int height = 800;
            final PopupWindow popResult = new PopupWindow(popupView, width, height);

            Button playAgain = (Button)popupView.findViewById(R.id.playAgain);
            Button newCategory = (Button)popupView.findViewById(R.id.newCategory);

            playAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer buttonClick = MediaPlayer.create(PlayActivity.this, R.raw.button_click);
                    buttonClick.start();
                    getCharacter(file);
                    popResult.dismiss();
                }
            });

            newCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer buttonClick = MediaPlayer.create(PlayActivity.this, R.raw.button_click);
                    buttonClick.start();
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                    popResult.dismiss();
                }
            });

            View v = popResult.getContentView();
            popResult.showAtLocation(v, Gravity.CENTER, 0, 150);
        }
    }
}
