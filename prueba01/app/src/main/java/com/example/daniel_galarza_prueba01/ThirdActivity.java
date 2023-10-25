package com.example.daniel_galarza_prueba01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLastname;
    private EditText editTextDividendo;
    private EditText editTextDivisor;
    private EditText editTextnumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextName = findViewById(R.id.editTextName3);
        editTextLastname = findViewById(R.id.editTextLastname3);
        editTextDividendo = findViewById(R.id.editTextDividendo3);
        editTextDivisor = findViewById(R.id.editTextDivisor3);
        editTextnumero = findViewById(R.id.editTextNumero2);

        //obtener los datos de la activity pasada como parametros
        Bundle bundle = getIntent().getExtras();
        String parameterFirstNumber = bundle.getString("firstParameter");
        String parameterSecondNumber = bundle.getString("secondParameter");

        //Firts Label forma normalita
        editTextName.setText(parameterFirstNumber);
        editTextLastname.setText(parameterSecondNumber);

        Button confirmButton = findViewById(R.id.buttonClose2);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enviar los elementos seleccionados a SecondActivity
                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                intent.putExtra("thirdParameter", editTextDividendo.getText().toString());
                intent.putExtra("fourParameter", editTextDivisor.getText().toString());
                intent.putExtra("fiveParameter", editTextnumero.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


}