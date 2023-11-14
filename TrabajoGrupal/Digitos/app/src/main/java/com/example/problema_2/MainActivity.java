package com.example.problema_2;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editTextImporte;
    private Button buttonProteger;
    private TextView textViewImporteProtegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextImporte = findViewById(R.id.editTextImporte);
        buttonProteger = findViewById(R.id.buttonProteger);
        textViewImporteProtegido = findViewById(R.id.textViewImporteProtegido);

        buttonProteger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String importe = editTextImporte.getText().toString();

                if (validarFormato(importe)) {
                    String importeProtegido = protegerImporte(importe);
                    textViewImporteProtegido.setText("Importe protegido: " + importeProtegido);
                } else {
                    textViewImporteProtegido.setText("Formato incorrecto.");
                }
            }
        });
    }

    public boolean validarFormato(String importe) {
        // Permitir un número opcional seguido de una coma y dos dígitos
        return importe.matches("\\d+(,\\d{2})?");
    }

    public String protegerImporte(String importe) {
        StringBuilder importeProtegido = new StringBuilder();
        int longitud = importe.length();
        int asteriscosNecesarios = 8 - longitud;

        for (int i = 0; i < asteriscosNecesarios; i++) {
            importeProtegido.append("*");
        }

        importeProtegido.append(importe);

        return importeProtegido.toString();
    }
}
