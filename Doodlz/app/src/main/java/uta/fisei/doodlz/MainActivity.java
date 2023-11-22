package uta.fisei.doodlz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtén el tamaño de la pantalla (screenSize) utilizando las técnicas de la Sección 4.6.3
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        // Utiliza el modo paisaje (landscape) para tabletas extra grandes; de lo contrario, utiliza el modo retrato (portrait)
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            // Configura la orientación en modo paisaje para tabletas extra grandes
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            // Configura la orientación en modo retrato para otros dispositivos
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // Añadir el fragmento MainActivityFragment al diseño de la actividad
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MainActivityFragment mainActivityFragment = new MainActivityFragment();
            fragmentTransaction.add(R.id.doodleFragment, mainActivityFragment); // Reemplaza R.id.fragmentContainer con el ID real de tu contenedor de fragmentos
            fragmentTransaction.commit();
        }
    }

}