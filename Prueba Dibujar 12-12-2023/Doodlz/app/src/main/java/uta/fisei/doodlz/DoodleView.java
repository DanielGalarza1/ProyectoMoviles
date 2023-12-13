package uta.fisei.doodlz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.print.PrintHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// custom View for drawing
public class DoodleView extends View {
    // used to determine whether user moved a finger enough to draw again
    private static final float TOUCH_TOLERANCE = 10;

    private Bitmap bitmap; // drawing area for displaying or saving
    private Canvas bitmapCanvas; // used to to draw on the bitmap
    private final Paint paintScreen; // used to draw bitmap onto screen
    private final Paint paintLine; // used to draw lines onto bitmap
    // Maps of current Paths being drawn and Points in those Paths
    private final Map<Integer, Path> pathMap = new HashMap<>();
    private final Map<Integer, Point> previousPointMap = new HashMap<>();

    // DoodleView constructor initializes the DoodleView
    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs); // pasa el contexto al constructor de View

        // Crear el objeto Paint para mostrar el bitmap en la pantalla
        paintScreen = new Paint();

        // Configurar la apariencia inicial para la línea pintada
        paintLine = new Paint();
        paintLine.setAntiAlias(true); // suavizar bordes de la línea
        paintLine.setColor(Color.BLACK); // color inicial de la línea
        paintLine.setStyle(Paint.Style.STROKE); // estilo de la línea
        paintLine.setStrokeWidth(5); // ancho de la línea
        paintLine.setStrokeCap(Paint.Cap.ROUND); // extremo redondeado de la línea
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }
    // clear the painting
    public void clear() {
        pathMap.clear(); // eliminar todos los caminos
        previousPointMap.clear(); // eliminar todos los puntos anteriores
        bitmap.eraseColor(Color.WHITE); // borrar el bitmap (establecer a blanco)
        invalidate(); // refrescar la pantalla
    }

    // set the painted line's color
    public void setDrawingColor(int color) {
        paintLine.setColor(color);
    }

    // return the painted line's color
    public int getDrawingColor() {
        return paintLine.getColor();
    }

    // set the painted line's width
    public void setLineWidth(int width) {
        paintLine.setStrokeWidth(width);
    }

    // return the painted line's width
    public int getLineWidth() {
        return (int) paintLine.getStrokeWidth();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);
        for (Integer key : pathMap.keySet())
            canvas.drawPath(Objects.requireNonNull(pathMap.get(key)), paintLine);
    }

    // handle touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked(); // tipo de evento
        int actionIndex = event.getActionIndex(); // puntero (por ejemplo, dedo)

        // determinar si el toque comenzó, terminó o se está moviendo
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            touchStarted(event.getX(actionIndex), event.getY(actionIndex), event.getPointerId(actionIndex));
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            touchEnded(event.getPointerId(actionIndex));
        } else {
            touchMoved(event);
        }
        invalidate();
        return true;
    }

    private void touchStarted(float x, float y, int lineID) {
        Path path; // utilizado para almacenar la ruta para el ID de toque dado
        Point point; // utilizado para almacenar el último punto en la ruta

        // si ya hay una ruta para lineID
        if (pathMap.containsKey(lineID)) {
            path = pathMap.get(lineID); // obtener la Path
            path.reset(); // restablecer la Path porque ha comenzado un nuevo toque
            point = previousPointMap.get(lineID); // obtener el último punto de la Path
        } else {
            path = new Path();
            pathMap.put(lineID, path); // agregar la Path al Map
            point = new Point(); // crear un nuevo Point
            previousPointMap.put(lineID, point); // agregar el Point al Map
        }

        // mover a las coordenadas del toque
        point.x = (int) x;
        point.y = (int) y;

        path.moveTo(x, y); // establecer el punto de inicio de la ruta
    }

    // called when the user drags along the screen
    private void touchMoved(MotionEvent event) {
        // para cada uno de los punteros en el MotionEvent dado
        for (int i = 0; i < event.getPointerCount(); i++) {
            // obtener el ID y el índice del puntero
            int pointerID = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pointerID);

            // si hay una ruta asociada con el puntero
            if (pathMap.containsKey(pointerID)) {
                // obtener las nuevas coordenadas para el puntero
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                // obtener la ruta y el punto anterior asociados con este puntero
                Path path = pathMap.get(pointerID);
                Point point = previousPointMap.get(pointerID);

                // calcular cuánto se movió el usuario desde la última actualización
                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                // si la distancia es lo suficientemente significativa
                if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE) {
                    // mover la ruta a la nueva ubicación
                    path.quadTo(point.x, point.y, (newX + point.x) / 2, (newY + point.y) / 2);

                    // almacenar las nuevas coordenadas
                    point.x = (int) newX;
                    point.y = (int) newY;
                }
            }
        }
    }

    // called when the user finishes a touch
    private void touchEnded(int lineID) {
        Path path = pathMap.get(lineID); // obtener la Path correspondiente
        bitmapCanvas.drawPath(path, paintLine); // draw to bitmapCanvas
        path.reset(); // reset the Path
    }

    // save the current image to the Gallery
    public void saveImage() {
        // utiliza "Doodlz" seguido por el tiempo actual como nombre de la imagen
        final String name = "Doodlz" + System.currentTimeMillis() + ".jpg";

        // inserta la imagen en el dispositivo
        String location = MediaStore.Images.Media.insertImage(
                getContext().getContentResolver(), bitmap, name, "Doodlz Drawing");

        if (location != null) {
            // muestra un mensaje indicando que la imagen se guardó
            Toast message = Toast.makeText(getContext(),
                    R.string.message_saved, Toast.LENGTH_SHORT);

            message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
                    message.getYOffset() / 2);
            message.show();
        } else {
            // muestra un mensaje indicando que hubo un error al guardar
            Toast message = Toast.makeText(getContext(),
                    R.string.message_error_saving, Toast.LENGTH_SHORT);

            message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
                    message.getYOffset() / 2);
            message.show();
        }
    }

    // print the current image
    public void printImage() {
        if (PrintHelper.systemSupportsPrint()) {
            // utiliza PrintHelper de la Android Support Library para imprimir la imagen
            PrintHelper printHelper = new PrintHelper(getContext());

            // ajusta la imagen en los límites de la página e imprime la imagen
            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            printHelper.printBitmap("Doodlz Image", bitmap);
        } else {
            // muestra un mensaje indicando que el sistema no permite la impresión
            Toast message = Toast.makeText(getContext(),
                    R.string.message_error_printing, Toast.LENGTH_SHORT);

            message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
                    message.getYOffset() / 2);
            message.show();
        }
    }

}

