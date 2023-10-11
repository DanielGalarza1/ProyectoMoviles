package uta.fisei.app4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uta.fisei.app4.logic.Mathematic;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMessage;
    private TextView textViewSecondForm;
    private TextView textViewThirdForm;
    private TextView textViewFourForm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMessage = findViewById(R.id.textViewMessage);
        textViewSecondForm = findViewById(R.id.textViewSecondForm);
        textViewThirdForm = findViewById(R.id.textViewThirdForm);
        textViewFourForm = findViewById(R.id.textViewFourForm);

        //obtener los datos de la activity pasada como parametros
        Bundle bundle = getIntent().getExtras();

        String parameterResult = bundle.getString("result");
        String parameterFirstNumber = bundle.getString("firstParameter");
        String parameterSecondNumber = bundle.getString("secondParameter");
        //Firts Form
        textViewMessage.setText(parameterFirstNumber+" + "+parameterSecondNumber+" = "+parameterResult);

        //Second Form
        int firstNumber = Integer.parseInt(parameterFirstNumber);
        int secondNumber = Integer.parseInt(parameterSecondNumber);

        int add = firstNumber + secondNumber;
        textViewSecondForm.setText(parameterFirstNumber + " + " + parameterSecondNumber + " = " + add);

        //Third Form
        int[] values = new int[3];
        values[0] = Integer.parseInt(parameterFirstNumber);
        values[1] = Integer.parseInt(parameterSecondNumber);

        int addVector = 0;

        for (int i = 0; i < values.length; i++) {
            addVector += values[i];
        }

        values[2] = addVector;

        textViewThirdForm.setText(values[0] + " + " + values[1] + " = " + values[2]);

        //Four Form
        int addClass = Mathematic.AddForClass(firstNumber, secondNumber);

        textViewFourForm.setText(parameterFirstNumber + " + " + parameterSecondNumber + " = " + addClass);
    }

    public void OnClickShowThirdActivity(View view){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }
}