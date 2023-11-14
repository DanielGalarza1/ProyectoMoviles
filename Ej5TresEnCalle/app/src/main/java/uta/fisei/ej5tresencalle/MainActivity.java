package uta.fisei.ej5tresencalle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText jugador1EdiText;
    private EditText jugador2EdiText;
    private Button unoButton;
    private Button dosButton;
    private Button tresButton;
    private Button cuatroButton;
    private Button cincoButton;
    private Button seisButton;
    private Button sieteButton;
    private Button ochoButton;
    private Button nueveButton;
    private RecyclerView historialRecyclerView;
    private PartidasAdapter partidasAdapter;
    private int movimientosRealizados = 0;
    private boolean juegoTerminado = false;
    private boolean tocaX = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jugador1EdiText = (EditText) findViewById(R.id.jugador1EditText);
        jugador2EdiText = (EditText) findViewById(R.id.jugador2EditText);

        unoButton = (Button) findViewById(R.id.unoButton);
        dosButton = (Button) findViewById(R.id.dosButton);
        tresButton = (Button) findViewById(R.id.tresButton);
        cuatroButton = (Button) findViewById(R.id.cuatroButton);
        cincoButton = (Button) findViewById(R.id.cincoButton);
        seisButton = (Button) findViewById(R.id.seisButton);
        sieteButton = (Button) findViewById(R.id.sieteButton);
        ochoButton = (Button) findViewById(R.id.ochoButton);
        nueveButton = (Button) findViewById(R.id.nueveButton);

        historialRecyclerView = (RecyclerView) findViewById(R.id.historialRecyclerView);
        historialRecyclerView.setHasFixedSize(true);
        historialRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        partidasAdapter = new PartidasAdapter();
        historialRecyclerView.setAdapter(partidasAdapter);
    }

    private void verificarEstadoJuego() {
        movimientosRealizados++;
        if (movimientosRealizados == 9) {
            movimientosRealizados = 0;
            juegoTerminado = true;
            Toast.makeText(getApplicationContext(), "¡Empate!", Toast.LENGTH_SHORT).show();

            Date fechaActual = Calendar.getInstance().getTime();

            Partida partida = new Partida("Empate", "Empate", 0, fechaActual);

            partidasAdapter.add(partida);
        }
    }
    public void presionar1(View view) {
        if (!juegoTerminado &&unoButton.getText().toString().equals("")) {
            if (tocaX) {
                unoButton.setText("X");
                verificarSiGano("X");

            } else {
                unoButton.setText("O");
                verificarSiGano("O");
            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void presionar2(View view) {
        if (!juegoTerminado &&dosButton.getText().toString().equals("")) {
            if (tocaX) {
                dosButton.setText("X");
                verificarSiGano("X");
            } else {
                dosButton.setText("O");
                verificarSiGano("O");
            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void presionar3(View view) {
        if (!juegoTerminado &&tresButton.getText().toString().equals("")) {
            if (tocaX) {
                tresButton.setText("X");
                verificarSiGano("X");
            } else {
                tresButton.setText("O");
                verificarSiGano("O");
            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void presionar4(View view) {
        if (!juegoTerminado &&cuatroButton.getText().toString().equals("")) {
            if (tocaX) {
                cuatroButton.setText("X");
                verificarSiGano("X");
            } else {
                cuatroButton.setText("O");
                verificarSiGano("O");
            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void presionar5(View view) {
        if (!juegoTerminado &&cincoButton.getText().toString().equals("")) {
            if (tocaX) {
                cincoButton.setText("X");
                verificarSiGano("X");
            } else {
                cincoButton.setText("O");
                verificarSiGano("O");
            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void presionar6(View view) {
        if (!juegoTerminado &&seisButton.getText().toString().equals("")) {
            if (tocaX) {
                seisButton.setText("X");
                verificarSiGano("X");
            } else {
                seisButton.setText("O");
                verificarSiGano("O");
            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void presionar7(View view) {
        if (!juegoTerminado &&sieteButton.getText().toString().equals("")) {
            if (tocaX) {
                sieteButton.setText("X");
                verificarSiGano("X");
            } else {
                sieteButton.setText("O");
                verificarSiGano("O");
            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void presionar8(View view) {
        if (!juegoTerminado &&ochoButton.getText().toString().equals("")) {
            if (tocaX) {
                ochoButton.setText("X");
                verificarSiGano("X");
            } else {
                ochoButton.setText("O");
                verificarSiGano("O");
            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void presionar9(View view) {
        if (!juegoTerminado &&nueveButton.getText().toString().equals("")) {
            if (tocaX) {
                nueveButton.setText("X");
                verificarSiGano("X");
            } else {
                nueveButton.setText("O");
                verificarSiGano("O");

            }
            tocaX = !tocaX;
            verificarEstadoJuego();
        }
    }

    public void verificarSiGano(String simbolo) {
        if (gano(simbolo)) {
            movimientosRealizados = -1;
            juegoTerminado = true;
            Toast.makeText(getApplicationContext(), "Gano " + simbolo + "!!!", Toast.LENGTH_SHORT).show();

            String nombreJugador1 = jugador1EdiText.getText().toString();
            String nombreJugador2 = jugador2EdiText.getText().toString();

            int quienGano = 0;
            if (simbolo == "X") {
                quienGano = 1;
            }
            if (simbolo == "O") {
                quienGano = 2;
            }

            Date fechaActual = Calendar.getInstance().getTime();

            Partida partida = new Partida(nombreJugador1, nombreJugador2, quienGano, fechaActual);

            partidasAdapter.add(partida);
        }
    }

    public boolean gano(String simbolo) {

        boolean siHayGanador = false;

        if (unoButton.getText().equals(simbolo) && dosButton.getText().equals(simbolo) && tresButton.getText().equals(simbolo)) {
            siHayGanador = true;
        }
        if (unoButton.getText().equals(simbolo) && cuatroButton.getText().equals(simbolo) && sieteButton.getText().equals(simbolo)) {
            siHayGanador = true;
        }
        if (unoButton.getText().equals(simbolo) && cincoButton.getText().equals(simbolo) && nueveButton.getText().equals(simbolo)) {
            siHayGanador = true;
        }

        if (cuatroButton.getText().equals(simbolo) && cincoButton.getText().equals(simbolo) && seisButton.getText().equals(simbolo)) {
            siHayGanador = true;
        }

        if (tresButton.getText().equals(simbolo) && seisButton.getText().equals(simbolo) && nueveButton.getText().equals(simbolo)) {
            siHayGanador = true;
        }
        if (sieteButton.getText().equals(simbolo) && ochoButton.getText().equals(simbolo) && nueveButton.getText().equals(simbolo)) {
            siHayGanador = true;
        }

        if (tresButton.getText().equals(simbolo) && cincoButton.getText().equals(simbolo) && sieteButton.getText().equals(simbolo)) {
            siHayGanador = true;
        }

        if (dosButton.getText().equals(simbolo) && cincoButton.getText().equals(simbolo) && ochoButton.getText().equals(simbolo)) {
            siHayGanador = true;
        }
        return siHayGanador;
    }

    private void limpiar() {
        unoButton.setText("");
        dosButton.setText("");
        tresButton.setText("");
        cuatroButton.setText("");
        cincoButton.setText("");
        seisButton.setText("");
        sieteButton.setText("");
        ochoButton.setText("");
        nueveButton.setText("");
    }

    public void reiniciarJuego(View view) {
        // Restablece todos los botones
        unoButton.setText("");
        dosButton.setText("");
        tresButton.setText("");
        cuatroButton.setText("");
        cincoButton.setText("");
        seisButton.setText("");
        sieteButton.setText("");
        ochoButton.setText("");
        nueveButton.setText("");

        // Reinicia las variables de estado
        movimientosRealizados = 0;
        tocaX = true;
        juegoTerminado = false;

        // Puedes borrar el historial de partidas si lo deseas

        // Notifica a los jugadores que el juego ha sido reiniciado
        Toast.makeText(getApplicationContext(), "Juego reiniciado. ¡Buena suerte!", Toast.LENGTH_SHORT).show();
    }
}
