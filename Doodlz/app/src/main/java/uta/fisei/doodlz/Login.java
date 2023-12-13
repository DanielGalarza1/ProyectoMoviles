package uta.fisei.doodlz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonOk;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mdgm);

        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonOk = findViewById(R.id.buttonOk);
        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        // Verificar si hay credenciales almacenadas y completar automáticamente el formulario
        String savedUser = preferences.getString("lastUser", "");
        String savedPassword = preferences.getString("lastPassword", "");

        if (!savedUser.isEmpty() && !savedPassword.isEmpty()) {
            editTextUser.setText(savedUser);
            editTextPassword.setText(savedPassword);
            attemptLogin(savedUser, savedPassword);
        }
    }

    public void onClickButtonOk(View view) {
        String user = editTextUser.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!user.isEmpty() && !password.isEmpty()) {
            // Guardar las credenciales del último usuario
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("lastUser", user);
            editor.putString("lastPassword", password);
            editor.apply();

            attemptLogin(user, password);
        } else {
            Toast.makeText(this, "Debe ingresar los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptLogin(String user, String password) {
        // Lógica de autenticación
        // ...

        // Ejemplo de redirección a MainActivity si la autenticación es exitosa
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("USERNAME", user);
        startActivity(intent);
    }
}
