package com.example.problema_3;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumeroRomano1;
    private EditText editTextNumeroRomano2;
    private EditText editTextNumeroRomano3;
    private Button buttonMultiplicar;
    private TextView textViewResultado;

    // Mapa para convertir números romanos a decimales
    private HashMap<Character, Integer> valoresRomanos = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumeroRomano1 = findViewById(R.id.editTextNumeroRomano1);
        editTextNumeroRomano2 = findViewById(R.id.editTextNumeroRomano2);
        editTextNumeroRomano3 = findViewById(R.id.editTextNumeroRomano3);
        buttonMultiplicar = findViewById(R.id.buttonMultiplicar);
        textViewResultado = findViewById(R.id.textViewResultado);

        // Inicializar el mapa de valores romanos
        valoresRomanos.put('I', 1);
        valoresRomanos.put('V', 5);
        valoresRomanos.put('X', 10);
        valoresRomanos.put('L', 50);
        valoresRomanos.put('C', 100);
        valoresRomanos.put('D', 500);
        valoresRomanos.put('M', 1000);

        buttonMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroRomano1 = editTextNumeroRomano1.getText().toString();
                String numeroRomano2 = editTextNumeroRomano2.getText().toString();
                String numeroRomano3 = editTextNumeroRomano3.getText().toString();

                // Realizar la multiplicación de los números romanos
                int resultadoDecimal = multiplicarNumerosRomanos(numeroRomano1, numeroRomano2, numeroRomano3);

                // Convertir el resultado a número romano
                String resultadoRomano = convertirANumeroRomano(resultadoDecimal);

                // Mostrar los resultados
                textViewResultado.setText("Resultado en decimal: " + resultadoDecimal + "\nResultado en romano: " + resultadoRomano);
            }
        });
    }

    // Función para multiplicar números romanos
    private int multiplicarNumerosRomanos(String numeroRomano1, String numeroRomano2, String numeroRomano3) {
        int decimal1 = convertirANumeroDecimal(numeroRomano1);
        int decimal2 = convertirANumeroDecimal(numeroRomano2);
        int decimal3 = convertirANumeroDecimal(numeroRomano3);

        return decimal1 * decimal2 * decimal3;
    }

    // Función para convertir números romanos a decimales
    private int convertirANumeroDecimal(String numeroRomano) {
        int decimal = 0;
        int prevValor = 0;

        for (int i = numeroRomano.length() - 1; i >= 0; i--) {
            char letra = numeroRomano.charAt(i);
            int valor = valoresRomanos.get(letra);

            if (valor < prevValor) {
                decimal -= valor;
            } else {
                decimal += valor;
            }

            prevValor = valor;
        }

        return decimal;
    }

    // Función para convertir números decimales a romanos
    private String convertirANumeroRomano(int numeroDecimal) {
        if (numeroDecimal < 1 || numeroDecimal > 3999) {
            return "Número fuera de rango";
        }

        String[] unidades = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] decenas = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] centenas = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] miles = {"", "M", "MM", "MMM"};

        int unidad = numeroDecimal % 10;
        int decena = (numeroDecimal / 10) % 10;
        int centena = (numeroDecimal / 100) % 10;
        int millar = numeroDecimal / 1000;

        return miles[millar] + centenas[centena] + decenas[decena] + unidades[unidad];
    }
}

