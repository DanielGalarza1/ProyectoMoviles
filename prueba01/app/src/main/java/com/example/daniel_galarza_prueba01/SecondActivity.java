package com.example.daniel_galarza_prueba01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLastname;

    private EditText editTextDividendo;
    private EditText editTextDivisor;
    private EditText editTextNumero;

    private int code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editTextName = findViewById(R.id.editTextName2);
        editTextLastname = findViewById(R.id.editTextLastname2);
        editTextDividendo = findViewById(R.id.editTextDividendo2);
        editTextDivisor = findViewById(R.id.editTextDivisor2);
        editTextNumero = findViewById(R.id.editTextNumero);

        Button confirmButton = findViewById(R.id.buttonClose);
        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enviar los elementos seleccionados a SecondActivity
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra("firstParameter", editTextName.getText().toString());
                intent.putExtra("secondParameter", editTextLastname.getText().toString());
                intent.putExtra("thirdParameter", editTextDividendo.getText().toString());
                intent.putExtra("fourParameter", editTextDivisor.getText().toString());
                intent.putExtra("fiveParameter", editTextNumero.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public void onClickThirdActivity(View view){
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("firstParameter", editTextName.getText().toString());
        intent.putExtra("secondParameter", editTextLastname.getText().toString());
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Habilitar el botón
            Button confirmButton = findViewById(R.id.buttonClose);
            confirmButton.setEnabled(true);
            //obtener los datos de la activity pasada como parametros
            editTextDividendo.setText(data.getStringExtra("thirdParameter"));
            editTextDivisor.setText(data.getStringExtra("fourParameter"));
            editTextNumero.setText(data.getStringExtra("fiveParameter"));
        }
    }
}