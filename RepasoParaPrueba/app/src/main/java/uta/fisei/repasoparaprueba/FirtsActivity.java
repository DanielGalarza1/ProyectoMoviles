package uta.fisei.repasoparaprueba;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import uta.fisei.repasoparaprueba.Logic.Mathemathics;

public class FirtsActivity extends AppCompatActivity {

    private TextView textViewMessage;
    private TextView textViewSecondForm;
    private TextView textViewThirdForm;
    private TextView textViewFourForm;
    private int code = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firts);

        textViewMessage = findViewById(R.id.textView_Mesage_One);
        textViewSecondForm = findViewById(R.id.textView_Mesage_Two);
        textViewThirdForm = findViewById(R.id.textView_Mesage_Three);
        textViewFourForm = findViewById(R.id.textView_Mesage_Four);

        //obtener los datos de la activity pasada como parametros
        Bundle bundle = getIntent().getExtras();
        String parameterResult = bundle.getString("result");
        String parameterFirstNumber = bundle.getString("firstParameter");
        String parameterSecondNumber = bundle.getString("secondParameter");
        //textViewMessage.setText(parameterResult);

        //Firts Label forma normalita
        textViewMessage.setText(parameterFirstNumber+" + "+parameterSecondNumber+" = "+parameterResult);

        //Second Form pasando parametros y realizando la suma aqui mismo
        int firstNumber = Integer.parseInt(parameterFirstNumber);
        int secondNumber = Integer.parseInt(parameterSecondNumber);

        int add = firstNumber + secondNumber;
        textViewSecondForm.setText(parameterFirstNumber + " + " + parameterSecondNumber + " = " + add);

        //Third Form usando vectores y la suma ahi mismo
        int[] values = new int[3];
        values[0] = Integer.parseInt(parameterFirstNumber);
        values[1] = Integer.parseInt(parameterSecondNumber);

        int addVector = 0;

        for (int i = 0; i < values.length; i++) {
            addVector += values[i];
        }

        values[2] = addVector;

        textViewThirdForm.setText(values[0] + " + " + values[1] + " = " + values[2]);

        //Four Form usando la clase
        int addClass = Mathemathics.Add(firstNumber, secondNumber);
        textViewFourForm.setText(parameterFirstNumber + " + " + parameterSecondNumber + " = " + addClass);
    }

    public void OnClickShowThirdActivity(View view){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivityForResult(intent, code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ArrayList<String> selectedItems = data.getStringArrayListExtra("selectedItems");

            if (selectedItems != null && !selectedItems.isEmpty()) {
                StringBuilder selectedItemsText = new StringBuilder();
                for (String item : selectedItems) {
                    selectedItemsText.append(item).append("\n");
                }
                textViewMessage.setText("Valores Seleccionados:\n" + selectedItemsText.toString());
            } else {
                textViewMessage.setText("No se seleccionaron elementos.");
            }
        }
    }
}