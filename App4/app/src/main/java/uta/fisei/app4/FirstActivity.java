package uta.fisei.app4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uta.fisei.app4.R;

public class FirstActivity extends AppCompatActivity {

    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        editTextName = findViewById(R.id.editTextName);

        registerForContextMenu(editTextName);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*if (id == R.id.menuFileAbout) {
            Toast.makeText(this, "Bienvenido a la App", Toast.LENGTH_SHORT).show();
            return true;
        }*/
        switch (id) {
            case R.id.menuEditPaste:
                Toast.makeText(this, "Presiono en la opcion a la App", Toast.LENGTH_SHORT).show();

            case R.id.menuFileAbout:
                Toast.makeText(this, "Esta es una app de prueba", Toast.LENGTH_SHORT).show();
               break; // Devuelve true para indicar que la opción ha sido manejada.
            case R.id.menuFileFinish:
                finish();
        }

        // Si el elemento del menú no coincide con ningún caso, llama al método de la superclase.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuContextualEditCopy:
                Toast.makeText(this, "Presiono Copiar", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}