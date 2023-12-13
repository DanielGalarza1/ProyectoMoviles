package uta.fisei.doodlz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.content.pm.PackageManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MainActivityFragment extends Fragment {

    private DoodleView doodleView; // Maneja eventos táctiles y dibuja
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private boolean dialogOnScreen = false;

    // Umbral de aceleración para determinar si el usuario agitó el dispositivo para borrar
    private static final int ACCELERATION_THRESHOLD = 100000;

    // Código de solicitud para el permiso de uso de almacenamiento externo necesario para la función de guardar imagen
    private static final int SAVE_IMAGE_PERMISSION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Llamar al método de la clase base
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_main_mdgm, container, false);

        // Establecer si el fragmento debe tener opciones de menú
        setHasOptionsMenu(true);

        // Obtener referencia a DoodleView en el diseño del fragmento
        doodleView = (DoodleView) view.findViewById(R.id.doodleView);

        // Inicializar valores de aceleración
        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        return view;
}

    // Override del método onResume del ciclo de vida del Fragmento
    @Override
    public void onResume() {
        super.onResume();
        // Llamar a enableAccelerometerListening para habilitar la escucha del acelerómetro
        enableAccelerometerListening();
    }

    // Método para habilitar la escucha del acelerómetro
    private void enableAccelerometerListening() {
        // Obtener el servicio SensorManager del sistema
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Registrar un listener para eventos del acelerómetro
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        disableAccelerometerListening();
    }

    private void disableAccelerometerListening() {
        SensorManager sensorManager =
                (SensorManager) getActivity().getSystemService(
                        Context.SENSOR_SERVICE);
        // stop listening for accelerometer events
        sensorManager.unregisterListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    // Manejador de eventos para eventos del acelerómetro
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        // Usa el acelerómetro para determinar si el usuario agitó el dispositivo
        @Override
        public void onSensorChanged(SensorEvent event) {
            // Asegúrate de que no se estén mostrando otros diálogos
            if (!dialogOnScreen) {
                // Obtén los valores de x, y, y z para el SensorEvent
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // Guarda el valor de aceleración anterior
                lastAcceleration = currentAcceleration;

                // Calcula la aceleración actual
                currentAcceleration = x * x + y * y + z * z;

                // Calcula el cambio en la aceleración
                acceleration = currentAcceleration * (currentAcceleration - lastAcceleration);

                // Si la aceleración está por encima de un umbral determinado
                if (acceleration > ACCELERATION_THRESHOLD) {
                    confirmErase(); // Muestra el diálogo para confirmar el borrado
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void confirmErase() {
        EraseImageDialogFragment fragment = new EraseImageDialogFragment();
        fragment.show(getFragmentManager(), "erase dialog");
    }

    // Muestra los elementos del menú del fragmento
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.doodle_fragment_menu, menu); // Infla el menú definido en doodle_fragment_menu.xml
    }

    // Maneja la selección del menú de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Switch basado en el ID del MenuItem
        switch (item.getItemId()) {
            case R.id.color:
                // Crear e mostrar un diálogo para seleccionar color
                ColorDialogFragment_mdgm colorDialog = new ColorDialogFragment_mdgm();
                colorDialog.show(getFragmentManager(), "color dialog");
                return true; // Consume el evento del menú

            case R.id.line_width:
                // Crear e mostrar un diálogo para seleccionar ancho de línea
                LineWidthDialogFragment widthDialog = new LineWidthDialogFragment();
                widthDialog.show(getFragmentManager(), "line width dialog");
                return true; // Consume el evento del menú

            case R.id.delete_drawing:
                confirmErase(); // Confirmar antes de borrar la imagen
                return true; // Consume el evento del menú

            case R.id.save:
                saveImage(); // Verificar permisos y guardar la imagen actual
                return true; // Consume el evento del menú

            case R.id.print:
                doodleView.printImage(); // Imprimir la imagen actual
                return true; // Consume el evento del menú
        }

        return super.onOptionsItemSelected(item);
    }

    // Solicita el permiso necesario para guardar la imagen si es necesario o guarda la imagen si la aplicación ya tiene permiso
    private void saveImage() {
        // Verifica si la aplicación no tiene el permiso necesario para guardar la imagen
        if (getContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Muestra una explicación de por qué se necesita el permiso
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Establece el mensaje del cuadro de diálogo de explicación
                builder.setMessage(R.string.permission_explanation);
                // Agrega un botón Aceptar al diálogo
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Solicita el permiso
                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, SAVE_IMAGE_PERMISSION_REQUEST_CODE);
                    }
                });
                // Muestra el diálogo
                builder.create().show();
            } else {
                // Solicita el permiso
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, SAVE_IMAGE_PERMISSION_REQUEST_CODE);
            }
        } else {
            // Si la aplicación ya tiene permiso para escribir en almacenamiento externo
            doodleView.saveImage(); // Guarda la imagen
        }
    }

    // Método llamado por el sistema cuando el usuario otorga o niega el
// permiso para guardar una imagen
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Switch elige la acción apropiada según la característica que
        // solicitó el permiso
        switch (requestCode) {
            case SAVE_IMAGE_PERMISSION_REQUEST_CODE:
                // Si el usuario otorga el permiso
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doodleView.saveImage(); // Guarda la imagen
                }
                return;
        }
    }

    // returns the DoodleView
       public DoodleView getDoodleView() {
        return doodleView;
        }
    // indicates whether a dialog is displayed
    public void setDialogOnScreen(boolean visible) {
        dialogOnScreen = visible;
    }

}
