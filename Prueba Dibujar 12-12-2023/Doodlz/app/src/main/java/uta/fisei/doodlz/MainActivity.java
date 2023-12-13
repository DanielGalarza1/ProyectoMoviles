package uta.fisei.doodlz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_mdgm);
            textViewUsuario = findViewById(R.id.textViewUsuario);

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

            // Verificar si es la primera vez que el usuario ha iniciado sesión
            SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
            boolean isFirstLogin = preferences.getBoolean("isFirstLogin", false);

            if (isFirstLogin) {
                // Realizar la configuración de la práctica para dibujar
                // Puedes agregar aquí la lógica específica para configurar la práctica

                // ...

                // Marcar que la configuración ha sido realizada
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirstLogin", false);
                editor.apply();
            }

            // Obtener el nombre de usuario del Intent y mostrarlo en el TextView
            String userName = getIntent().getStringExtra("USERNAME");
            if (userName != null && !userName.isEmpty()) {
                textViewUsuario.setText("Bienvenido, " + userName);
            }
        }
    }
