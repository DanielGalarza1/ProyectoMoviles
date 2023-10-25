package com.example.daniel_galarza_prueba01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private int code = 1;

    private EditText editTextName;
    private EditText editTextLastname;

    private EditText editTextDividendo;
    private EditText editTextDivisor;
    private EditText editTextEntero;
    private EditText editTextResiduo;
    private EditText editTextInvertido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Desabilidar el Botton
        Button confirmButton = findViewById(R.id.buttonResult);
        confirmButton.setEnabled(false);

        editTextName = findViewById(R.id.editTextName);
        editTextLastname = findViewById(R.id.editTextLastname);
        editTextDividendo = findViewById(R.id.editTextDividendo);
        editTextDivisor = findViewById(R.id.editTextDivisor);
        editTextEntero = findViewById(R.id.editTextEntero);
        editTextResiduo = findViewById(R.id.editTextResiduo);
        editTextInvertido = findViewById(R.id.editTextInvertido);

    }

    public void onClickShowSecondActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Habilitar el botón
            Button confirmButton = findViewById(R.id.buttonResult);
            confirmButton.setEnabled(true);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //obtener los datos de la activity pasada como parametros
                    editTextName.setText(data.getStringExtra("firstParameter"));
                    editTextLastname.setText(data.getStringExtra("secondParameter"));
                    editTextDividendo.setText(data.getStringExtra("thirdParameter"));
                    editTextDivisor.setText(data.getStringExtra("fourParameter"));

                    //Alrevez
                    String inputText = data.getStringExtra("fiveParameter");
                    String reversedText = new StringBuilder(inputText).reverse().toString();
                    editTextInvertido.setText(reversedText);

                    // Divisor y Entero
                    String dividendText = data.getStringExtra("thirdParameter");
                    String divisorText = data.getStringExtra("fourParameter");

                    int dividend = Integer.parseInt(dividendText);
                    int divisor = Integer.parseInt(divisorText);

                    int quotient = 0;
                    int remainder = dividend;

                    // División por restas .
                    while (remainder >= divisor) {
                        remainder -= divisor;
                        quotient++;
                    }
                    editTextEntero.setText(String.valueOf(quotient));
                    editTextResiduo.setText(String.valueOf(remainder));
                }
            });
        }
    }
}