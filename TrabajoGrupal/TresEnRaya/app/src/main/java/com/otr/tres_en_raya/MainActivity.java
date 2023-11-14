package com.otr.tres_en_raya;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.widget.Space;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private int jugadores;
    private RadioGroup grupo_radio;
    private int[] casillas;
    private int[] tachados;
    private Partida partida;

    private LinearLayout menu;
    private LinearLayout espacio1;

    /**
     * Metodo que se ejecuta cuando se crea la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciamos el array casillas que identifica cada casilla y la almacena en el array
        casillas = new int[9];
            casillas[0] = R.id.a1; casillas[1] = R.id.a2; casillas[2] = R.id.a3;
            casillas[3] = R.id.b1; casillas[4] = R.id.b2; casillas[5] = R.id.b3;
            casillas[6] = R.id.c1; casillas[7] = R.id.c2; casillas[8] = R.id.c3;

        tachados = new int[8];
            tachados[0] = R.id.tach_0; tachados[1] = R.id.tach_1; tachados[2] = R.id.tach_2;
            tachados[3] = R.id.tach_3; tachados[4] = R.id.tach_4; tachados[5] = R.id.tach_5;
            tachados[6] = R.id.tach_6; tachados[7] = R.id.tach_7;

            RadioButton r = findViewById(R.id.radio_facil);
    }

    /**
     * Metodo que se ejecuta al seleccionar uno de los dos modos de juego para comenzar la partida
     * @param view Identifica que vista ha sido pulsada
     */
    public void jugar(View view) {

        ImageView imagen;

        for(int i = 0; i < casillas.length; i++){
            imagen = findViewById(casillas[i]);
            imagen.setImageResource(R.drawable.casilla);
        }

        menu = findViewById(R.id.menu);
        espacio1 = findViewById(R.id.espacio1);

        //cambios la transparencia del menu
        AlphaAnimation animation_alpha = new AlphaAnimation(1f, 0f);
        animation_alpha.setDuration(500);
        animation_alpha.setFillAfter(true);

        //cuando la animacion de alpha termine, ejecuta el metodo onAnimationEnd
        animation_alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {

                menu.setAlpha(0);
                //hacer el menu estrecho
                ResizeAnimation animation_width = new ResizeAnimation(menu, 0);
                animation_width.setDuration(1200);
                menu.startAnimation(animation_width);

                animation_width = new ResizeAnimation(espacio1, 0);
                animation_width.setDuration(1200);
                espacio1.startAnimation(animation_width);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        menu.startAnimation(animation_alpha);

        //comprobamos cuantos jugadoes van a jugar
        jugadores = 1;

        if(view.getId() == R.id.btn_dos_jugadores)
            jugadores = 2;

        //detectamos que dificultad ha elegido el jugador
        grupo_radio = findViewById(R.id.grupo_radio);
        int id = grupo_radio.getCheckedRadioButtonId();
        int dificultad = 0;

        if(id == R.id.radio_normal)
            dificultad = 1;
        else if(id == R.id.radio_imposible)
            dificultad = 2;

        partida = new Partida(dificultad);
    }

    /**
     * Metodo que se ejecuta al pulsar una casilla
     * @param view Identifica que vista ha sido pulsada
     */
    public void tocarCasilla(View view){

        if(partida==null)
            return;
        //
        int casilla = 0;

        for(int i = 0; i < casillas.length; i++){
            if(casillas[i] == view.getId()){
                casilla = i;
                break;
            }
        }

        if(!partida.isTerminada() && partida.getCasillasSeleccionadas(casilla)==0 && partida.getJugador()==1 && jugadores==1)
        {
            marcarCasilla(casilla);
                //comprobar que aun queda espacio para marcar otra casilla
                if(!partida.isTerminada()){
                    while(true) {
                        int casilla_ia = partida.ia();

                        if(partida.getCasillasSeleccionadas(casilla_ia)==0) {
                            marcarCasilla(casilla_ia);
                            break;
                        }
                    }
                }
        }

        else if(!partida.isTerminada() && partida.getCasillasSeleccionadas(casilla)==0 && jugadores==2)
            marcarCasilla(casilla);
    }

    /**
     * Metodo que marca una casilla con un aspa o con un circulo
     * @param casilla Numero de la casilla que se marcara
     */
    private void marcarCasilla(int casilla){

        ImageView imagen;

        imagen = findViewById(casillas[casilla]);

        if(partida.getJugador()==1 && partida.getCasillasSeleccionadas(casilla)==0){
            imagen.setImageResource(R.drawable.aspa);
            partida.setCasillasSeleccionadas(partida.getJugador(), casilla);
            partida.cambiarJugador();
        }
        else if(partida.getJugador()==2 && partida.getCasillasSeleccionadas(casilla)==0){
            imagen.setImageResource(R.drawable.circulo);
            partida.setCasillasSeleccionadas(partida.getJugador(), casilla);
            partida.cambiarJugador();
        }

        //
        //comprobar si alguien ha ganado
        if(partida.comprobarGanador()[0]==1) {
            partida.setTerminada(true);

            //mostrar mensaje por pantalla
            TextView mensaje = findViewById(R.id.mensaje);
            mensaje.setText(R.string.gana_jugador1);

            mensaje.setVisibility(View.VISIBLE);

            AlphaAnimation animation_alpha = new AlphaAnimation(0f, 1f);
            animation_alpha.setDuration(1000);
            mensaje.startAnimation(animation_alpha);
            //

            tachar(partida.comprobarGanador()[1]);
            //
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //volver al layout original de la actividad
                    setContentView(R.layout.activity_main);
                    //
                    RadioButton r = findViewById(R.id.radio_normal);
                    RadioButton r1 = findViewById(R.id.radio_imposible);
                    RadioButton r2 = findViewById(R.id.radio_facil);

                    if(partida.getDificultad()==1){
                        r.setChecked(true);
                    }
                    else if(partida.getDificultad()==2){
                        r1.setChecked(true);
                    }
                    r.jumpDrawablesToCurrentState();
                    r1.jumpDrawablesToCurrentState();
                    r2.jumpDrawablesToCurrentState();
                }
            }, 2500);
        }
        else if(partida.comprobarGanador()[0]==2) {
            partida.setTerminada(true);

            //mostrar mensaje por pantalla
            TextView mensaje = findViewById(R.id.mensaje);
            mensaje.setText(R.string.gana_jugador2);

            mensaje.setVisibility(View.VISIBLE);

            AlphaAnimation animation_alpha = new AlphaAnimation(0f, 1f);
            animation_alpha.setDuration(1000);
            mensaje.startAnimation(animation_alpha);
            //

            tachar(partida.comprobarGanador()[1]);
            //
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //volver al layout original de la actividad
                    setContentView(R.layout.activity_main);
                    //
                    RadioButton r = findViewById(R.id.radio_normal);
                    RadioButton r1 = findViewById(R.id.radio_imposible);
                    RadioButton r2 = findViewById(R.id.radio_facil);

                    if(partida.getDificultad()==1){
                        r.setChecked(true);
                    }
                    else if(partida.getDificultad()==2){
                        r1.setChecked(true);
                    }
                    r.jumpDrawablesToCurrentState();
                    r1.jumpDrawablesToCurrentState();
                    r2.jumpDrawablesToCurrentState();
                }
            }, 2500);
        }else if(partida.comprobarGanador()[0]==3) {
            partida.setTerminada(true);

            //mostrar mensaje por pantalla
            TextView mensaje = findViewById(R.id.mensaje);
            mensaje.setText(R.string.empate);

            mensaje.setVisibility(View.VISIBLE);

            AlphaAnimation animation_alpha = new AlphaAnimation(0f, 1f);
            animation_alpha.setDuration(1000);
            mensaje.startAnimation(animation_alpha);
            //
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //volver al layout original de la actividad
                    setContentView(R.layout.activity_main);
                    //
                    RadioButton r = findViewById(R.id.radio_normal);
                    RadioButton r1 = findViewById(R.id.radio_imposible);
                    RadioButton r2 = findViewById(R.id.radio_facil);

                    if(partida.getDificultad()==1){
                        r.setChecked(true);
                    }
                    else if(partida.getDificultad()==2){
                        r1.setChecked(true);
                    }
                    r.jumpDrawablesToCurrentState();
                    r1.jumpDrawablesToCurrentState();
                    r2.jumpDrawablesToCurrentState();
                }
            }, 2500);
        }

    }

    /**
     * Metodo que tacha con una linea roja la combinación ganadora
     * @param n Numero de la combinacion ganadora
     */
    public void tachar(int n){
        ImageView tachado = findViewById(tachados[n]);

        tachado.setVisibility(View.VISIBLE);

        AlphaAnimation animation_alpha = new AlphaAnimation(0f, 1f);
        animation_alpha.setDuration(225);
        tachado.startAnimation(animation_alpha);
    }
}
