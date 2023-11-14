package uta.fisei.app_005;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonOk = findViewById(R.id.buttonOk);
    }

    public void onClickButtonOk(View view) {
        String user = editTextUser.getText().toString();
        String password = editTextPassword.getText().toString();
        if (!user.matches("") && !password.matches("")){
            Intent intent = new Intent(this, DataBaseActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Debe Ingresar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}