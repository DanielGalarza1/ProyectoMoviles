package uta.fisei.ej5tresencalle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OptionsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);
    }

    public void jugarEntre2Jugadores(View view) {
        // Inicia la actividad para jugar entre 2 jugadores
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void jugarContraCPU(View view) {
        // Inicia la actividad para jugar contra la CPU
        Intent intent = new Intent(this, SinglePlayerActivity.class);
        startActivity(intent);
    }

    public void verCreditos(View view) {

    }
}