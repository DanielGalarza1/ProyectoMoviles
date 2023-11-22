package uta.fisei.cannongame;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
//import android.app.DialogFragment;
//import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.Random;

import uta.fisei.cannongame.logic.*;
public class CannonView extends SurfaceView
    implements SurfaceHolder.Callback{
    private static final String TAG = "CannonView"; // for logging errors
    // Constantes para el juego
    public static final int MISS_PENALTY = 2; // segundos deducidos en un fallo
    public static final int HIT_REWARD = 3; // segundos añadidos en un acierto

    // Constantes para el cañón
    public static final double CANNON_BASE_RADIUS_PERCENT = 3.0 / 40;
    public static final double CANNON_BARREL_WIDTH_PERCENT = 3.0 / 40;
    public static final double CANNON_BARREL_LENGTH_PERCENT = 1.0 / 10;

    // Constantes para la bola de cañón
    public static final double CANNONBALL_RADIUS_PERCENT = 3.0 / 80;
    public static final double CANNONBALL_SPEED_PERCENT = 3.0 / 2;
    // Constantes para los objetivos
    public static final double TARGET_WIDTH_PERCENT = 1.0 / 40;
    public static final double TARGET_LENGTH_PERCENT = 3.0 / 20;
    public static final double TARGET_FIRST_X_PERCENT = 3.0 / 5;
    public static final double TARGET_SPACING_PERCENT = 1.0 / 60;
    public static final double TARGET_PIECES = 9;
    public static final double TARGET_MIN_SPEED_PERCENT = 3.0 / 4;
    public static final double TARGET_MAX_SPEED_PERCENT = 6.0 / 4;

    // Constantes para el bloqueador
    public static final double BLOCKER_WIDTH_PERCENT = 1.0 / 40;
    public static final double BLOCKER_LENGTH_PERCENT = 1.0 / 4;
    public static final double BLOCKER_X_PERCENT = 1.0 / 2;
    public static final double BLOCKER_SPEED_PERCENT = 1.0;

    // Tamaño del texto: 1/18 del ancho de la pantalla
    public static final double TEXT_SIZE_PERCENT = 1.0 / 18;
    private CannonThread cannonThread; // controls the game loop
    private Activity activity;
    private boolean dialogIsDisplayed = false;
    private Blocker blocker;
    private ArrayList<Target> targets;

    // dimension variables
    private int screenWidth;
    private int screenHeight;

    // variables for the game loop and tracking statistics
    private boolean gameOver; // is the game over?
    private double timeLeft; // time remaining in seconds
    private int shotsFired; // shots the user has fired
    private double totalElapsedTime; // elapsed seconds

    // constants and variables for managing sounds
    public static final int TARGET_SOUND_ID = 0;
    public static final int CANNON_SOUND_ID = 1;
    public static final int BLOCKER_SOUND_ID = 2;
    private SoundPool soundPool; // plays sound effects
    private SparseIntArray soundMap; // maps IDs to SoundPool

// Paint variables used when drawing each item on the screen
    private Paint textPaint; // Paint used to draw text
    private Paint backgroundPaint; // Paint used to clear the drawing area
    private Cannon cannon;

    // Constructor
    public CannonView(Context context, AttributeSet attrs) {
        super(context, attrs); // Llama al constructor de la superclase
        activity = (Activity) context; // Almacena una referencia a MainActivity

        // Registra el escucha SurfaceHolder.Callback
        getHolder().addCallback(this);

        // Configura los atributos de audio para el audio del juego
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        // Inicializa SoundPool para reproducir los tres efectos de sonido de la aplicación
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(1);
        builder.setAudioAttributes(attrBuilder.build());
        soundPool = builder.build();

        // Crea un mapa de sonidos y precarga los sonidos
        soundMap = new SparseIntArray(3); // Crea un nuevo SparseIntArray
        soundMap.put(TARGET_SOUND_ID, soundPool.load(context, R.raw.target_hit, 1));
        soundMap.put(CANNON_SOUND_ID, soundPool.load(context, R.raw.cannon_fire, 1));
        soundMap.put(BLOCKER_SOUND_ID, soundPool.load(context, R.raw.blocker_hit, 1));

        textPaint = new Paint();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
    }

    public static class GameResultDialogFragment extends DialogFragment {}
    // Se llama cuando cambia el tamaño del SurfaceView, por ejemplo, cuando se añade por primera vez a la jerarquía de vistas
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w; // Almacena el ancho de CannonView
        screenHeight = h; // Almacena la altura de CannonView

        // Configura las propiedades del texto
        textPaint.setTextSize((int) (TEXT_SIZE_PERCENT * screenHeight));
        textPaint.setAntiAlias(true); // Suaviza el texto
    }

    // Obtiene el ancho de la pantalla de juego
    public int getScreenWidth() {
        return screenWidth;
    }

    // Obtiene la altura de la pantalla de juego
    public int getScreenHeight() {
        return screenHeight;
    }

    // Reproduce un sonido con el soundId dado en soundMap
    public void playSound(int soundId) {
        soundPool.play(soundMap.get(soundId), 1, 1, 1, 0, 1f);
    }

    // Restablece todos los elementos de la pantalla y comienza un nuevo juego
    public void newGame() {
        // Construye un nuevo Cannon
        cannon = new Cannon(this,
                (int) (CANNON_BASE_RADIUS_PERCENT * screenHeight),
                (int) (CANNON_BARREL_LENGTH_PERCENT * screenWidth),
                (int) (CANNON_BARREL_WIDTH_PERCENT * screenHeight));

        Random random = new Random(); // para determinar velocidades aleatorias
        targets = new ArrayList<>(); // construye una nueva lista de objetivos

        // Inicializa targetX para el primer Target desde la izquierda
        int targetX = (int) (TARGET_FIRST_X_PERCENT * screenWidth);

        // Calcula la coordenada Y de los Targets
        int targetY = (int) ((0.5 - TARGET_LENGTH_PERCENT / 2) * screenHeight);

        // Añade TARGET_PIECES Targets a la lista de Targets
        for (int n = 0; n < TARGET_PIECES; n++) {

            // Determina una velocidad aleatoria entre los valores mínimo y máximo
            // para el Target n
            double velocity = screenHeight * (random.nextDouble() *
                    (TARGET_MAX_SPEED_PERCENT - TARGET_MIN_SPEED_PERCENT) +
                    TARGET_MIN_SPEED_PERCENT);

            // Alterna colores de Target entre oscuro y claro
            int color = (n % 2 == 0) ?
                    getResources().getColor(R.color.dark, getContext().getTheme()) :
                    getResources().getColor(R.color.light, getContext().getTheme());

            velocity *= -1; // invierte la velocidad inicial para el próximo Target

            // Crea y añade un nuevo Target a la lista de Targets
            targets.add(new Target(this, color, HIT_REWARD, targetX, targetY,
                    (int) (TARGET_WIDTH_PERCENT * screenWidth),
                    (int) (TARGET_LENGTH_PERCENT * screenHeight),
                    (int) velocity));

            // Aumenta la coordenada x para posicionar el próximo Target más
            // a la derecha
            targetX += (TARGET_WIDTH_PERCENT + TARGET_SPACING_PERCENT) * screenWidth;
        }

        // Crea un nuevo Blocker
        blocker = new Blocker(this, Color.BLACK, MISS_PENALTY,
                (int) (BLOCKER_X_PERCENT * screenWidth),
                (int) ((0.5 - BLOCKER_LENGTH_PERCENT / 2) * screenHeight),
                (int) (BLOCKER_WIDTH_PERCENT * screenWidth),
                (int) (BLOCKER_LENGTH_PERCENT * screenHeight),
                (float) (BLOCKER_SPEED_PERCENT * screenHeight));

        timeLeft = 10; // Inicia la cuenta regresiva en 10 segundos
        shotsFired = 0; // Establece el número inicial de disparos realizados
        totalElapsedTime = 0.0; // Establece el tiempo transcurrido en cero

        if (gameOver) { // Inicia un nuevo juego después de que el último juego terminó
            gameOver = false; // el juego no ha terminado
            cannonThread = new CannonThread(getHolder()); // crea un hilo
            cannonThread.start(); // inicia el hilo del bucle de juego
        }
        hideSystemBars();
    }

    // Llamado repetidamente por CannonThread para actualizar los elementos del juego
    private void updatePositions(double elapsedTimeMS) {
        double interval = elapsedTimeMS / 1000.0; // convierte a segundos

        // Actualiza la posición de la bala si está en pantalla
        if (cannon.getCannonball() != null)
            cannon.getCannonball().update(interval);

        blocker.update(interval); // Actualiza la posición del bloqueador

        for (GameElement target : targets)
            target.update(interval); // Actualiza la posición del objetivo

        timeLeft -= interval; // Resta al tiempo restante

        // Si el temporizador llega a cero
        if (timeLeft <= 0) {
            timeLeft = 0.0;
            gameOver = true; // el juego ha terminado
            cannonThread.setRunning(false); // termina el hilo
            showGameOverDialog(R.string.lose); // muestra el diálogo de pérdida
        }

        // Si todas las piezas han sido alcanzadas
        if (targets.isEmpty()) {
            cannonThread.setRunning(false); // termina el hilo
            showGameOverDialog(R.string.win); // muestra el diálogo de victoria
            gameOver = true;
        }
    }

    // Alinea el cañón y dispara una Cannonball si no hay
// una Cannonball en pantalla
    public void alignAndFireCannonball(MotionEvent event) {
        // Obtiene la ubicación del toque en esta vista
        Point touchPoint = new Point((int) event.getX(), (int) event.getY());

        // Calcula la distancia del toque desde el centro de la pantalla
        // en el eje y
        double centerMinusY = (screenHeight / 2 - touchPoint.y);

        double angle = 0; // Inicializa el ángulo en 0

        // Calcula el ángulo que hace el cañón con la horizontal
        angle = Math.atan2(touchPoint.x, centerMinusY);

        // Apunta el cañón al punto donde se tocó la pantalla
        cannon.align(angle);

        // Dispara Cannonball si no hay una Cannonball en pantalla
        if (cannon.getCannonball() == null || !cannon.getCannonball().isOnScreen()) {
            cannon.fireCannonball();
            ++shotsFired;
        }
    }

    // Muestra un AlertDialog cuando el juego termina
    private void showGameOverDialog(final int messageId) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(getResources().getString(messageId));
        dialogBuilder.setCancelable(false);

        dialogBuilder.setMessage(getResources().getString(
                R.string.results_format, shotsFired, totalElapsedTime));
        dialogBuilder.setPositiveButton(R.string.reset_game,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialogIsDisplayed = false;
                        newGame();
                    }
                }
        );

        // En el hilo GUI, usa FragmentManager para mostrar DialogFragment
        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        dialogIsDisplayed = true;
                        dialogBuilder.show();
                    }
                }
        );
    }

    // Dibuja el juego en el Canvas proporcionado
    public void drawGameElements(Canvas canvas) {
        // Limpiar el fondo
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);

        // Mostrar el tiempo restante
        canvas.drawText(getResources().getString(R.string.time_remaining_format, timeLeft), 50, 100, textPaint);

        cannon.draw(canvas); // Dibujar el cañón

        // Dibujar los GameElements
        if (cannon.getCannonball() != null && cannon.getCannonball().isOnScreen())
            cannon.getCannonball().draw(canvas);

        blocker.draw(canvas); // Dibujar el bloqueador

        // Dibujar todos los objetivos (Targets)
        for (GameElement target : targets)
            target.draw(canvas);
    }

    // Verifica si la bola colisiona con el bloqueador o alguno de los objetivos (Targets)
// y maneja las colisiones
    public void testForCollisions() {
        // Elimina cualquier objetivo (Target) con el que la bola de cañón colisiona
        if (cannon.getCannonball() != null && cannon.getCannonball().isOnScreen()) {
            for (int n = 0; n < targets.size(); n++) {
                if (cannon.getCannonball().collidesWith(targets.get(n))) {
                    targets.get(n).playSound(); // Reproducir sonido de golpe al objetivo
                    // Agrega la recompensa de tiempo al tiempo restante
                    timeLeft += targets.get(n).getHitReward();
                    cannon.removeCannonball(); // Elimina la bola de cañón del juego
                    targets.remove(n); // Elimina el objetivo que fue golpeado
                    --n; // Asegura que no omitamos la prueba del nuevo objetivo n
                    break;
                }
            }
        } else { // Elimina la bola de cañón si no debería estar en pantalla
            cannon.removeCannonball();
        }

        // Verifica si la bola colisiona con el bloqueador
        if (cannon.getCannonball() != null && cannon.getCannonball().collidesWith(blocker)) {
            blocker.playSound(); // Reproducir sonido de golpe al bloqueador
            // Invertir la dirección de la bola
            cannon.getCannonball().reverseVelocityX();
            // Resta la penalización por fallo del bloqueador al tiempo restante
            timeLeft -= blocker.getMissPenalty();
        }
    }

    // Detiene el juego: llamado por el método onPause de CannonGameFragment
    public void stopGame() {
        if (cannonThread != null) {
            cannonThread.setRunning(false); // Indica al hilo que termine
        }
    }

    // Libera los recursos: llamado por el método onDestroy de CannonGame
    public void releaseResources() {
        soundPool.release(); // Libera todos los recursos utilizados por SoundPool
        soundPool = null;
    }

    // Llamado cuando cambia el tamaño de la superficie
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Puedes implementar lógica específica si es necesario
    }

    // Llamado cuando la superficie es creada por primera vez
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!dialogIsDisplayed) {
            newGame(); // Configura y comienza un nuevo juego
            cannonThread = new CannonThread(holder); // Crea un nuevo hilo
            cannonThread.setRunning(true); // Inicia la ejecución del juego
            cannonThread.start(); // Inicia el hilo del bucle del juego
        }
    }

    // Llamado cuando la superficie es destruida
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Asegúrate de que el hilo se termine adecuadamente
        boolean retry = true;
        cannonThread.setRunning(false); // Termina el hilo cannonThread
        while (retry) {
            try {
                cannonThread.join(); // Espera a que cannonThread termine
                retry = false;
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread interrupted", e);
            }
        }
    }

    // Llamado cuando el usuario toca la pantalla en esta actividad
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Obtiene el entero que representa el tipo de acción que causó este evento
        int action = e.getAction();

        // El usuario tocó la pantalla o arrastró a lo largo de la pantalla
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            // Dispara la bala del cañón hacia el punto de contacto
            alignAndFireCannonball(e);
        }

        return true;
    }

    // Subclase Thread para controlar el bucle del juego
    private class CannonThread extends Thread {
        private SurfaceHolder surfaceHolder; // para manipular el lienzo
        private boolean threadIsRunning = true; // en ejecución por defecto

        // inicializa el SurfaceHolder
        public CannonThread(SurfaceHolder holder) {
            surfaceHolder = holder;
            setName("CannonThread");
        }

        // cambia el estado de ejecución
        public void setRunning(boolean running) {
            threadIsRunning = running;
        }

        // controla el bucle del juego
        @Override
        public void run() {
            Canvas canvas = null; // utilizado para dibujar
            long previousFrameTime = System.currentTimeMillis();

            while (threadIsRunning) {
                try {
                    // obtén el Canvas para dibujar de manera exclusiva desde este hilo
                    canvas = surfaceHolder.lockCanvas(null);
                    // bloquea el surfaceHolder para dibujar
                    synchronized (surfaceHolder) {
                        long currentTime = System.currentTimeMillis();
                        double elapsedTimeMS = currentTime - previousFrameTime;
                        totalElapsedTime += elapsedTimeMS / 1000.0;
                        updatePositions(elapsedTimeMS); // actualiza el estado del juego
                        testForCollisions(); // prueba colisiones con GameElement
                        drawGameElements(canvas); // dibuja usando el canvas
                        previousFrameTime = currentTime; // actualiza el tiempo anterior
                    }
                } finally {
                    // muestra el contenido del canvas en CannonView
                    // y permite que otros hilos utilicen el Canvas
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    // oculta las barras del sistema y la barra de la aplicación
    private void hideSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    // muestra las barras del sistema y la barra de la aplicación
    private void showSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
}
