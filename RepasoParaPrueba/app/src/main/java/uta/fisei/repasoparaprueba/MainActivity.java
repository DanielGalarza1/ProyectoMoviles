package uta.fisei.repasoparaprueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inicializa la barra de herramientas
        Toolbar toolbar = findViewById(R.id.toolbar); // Reemplaza "R.id.toolbar" con el ID real de tu Toolbar en el layout XML
        setSupportActionBar(toolbar);
    }

    public void onClickButtonBrowser(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(intent);
    }

    public void onClickButtonCallPhone(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: +593923456895"));
        startActivity(intent);
    }

    public void onClickButtonGoogleMaps(View view){
        Uri uri = Uri.parse("geo:37.2202,-100.202");  // Corrección aquí
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //Intent intent = new Intent("com.google.android.apps.maps");
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No se puede mostrar el mapa",
                    Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }

    public void onClickShowMainActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

}