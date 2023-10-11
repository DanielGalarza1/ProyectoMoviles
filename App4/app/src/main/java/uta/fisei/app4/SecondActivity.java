package uta.fisei.app4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uta.fisei.app4.logic.Mathematic;

public class SecondActivity extends AppCompatActivity {

    private EditText editTextNumberOne;
    private EditText editTextNumberTwo;
    private TextView textViewResult;
    private Button buttonAdd;

    private static  final String NAME_ACTIVITY = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //referencias de las vistas controles
        editTextNumberOne = findViewById(R.id.editTextNumberOne);
        editTextNumberTwo = findViewById(R.id.editTextNumberTwo);
        textViewResult = findViewById(R.id.textViewResult);
        buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String numberOne = editTextNumberOne.getText().toString();
                String numberTwo = editTextNumberTwo.getText().toString();

                int a = Integer.parseInt(numberOne);
                int b = Integer.parseInt(numberTwo);
                long add = a + b;

                String result = String.valueOf(add);
                textViewResult.setText(result);

            }
        });

        Log.d(NAME_ACTIVITY, "Se ejecuto: onCreate()");
        Toast.makeText(this, NAME_ACTIVITY + "" + "Se ejecuto: onCreate()", Toast.LENGTH_SHORT).show();

    }

    public void onClickAdd(View view){
        String numberOne = editTextNumberOne.getText().toString();
        String numberTwo = editTextNumberTwo.getText().toString();

        int a = Integer.parseInt(numberOne);
        int b = Integer.parseInt(numberTwo);
        long add = a + b;

        String result = String.valueOf(add);
        textViewResult.setText(result);
    }

    public void onClickSubstract(View view){
        String numberOne = editTextNumberOne.getText().toString();
        String numberTwo = editTextNumberTwo.getText().toString();

        int a = Integer.parseInt(numberOne);
        int b = Integer.parseInt(numberTwo);
        long add = a - b;

        String result = String.valueOf(add);
        textViewResult.setText(result);
    }

    public void onClickMultiply(View view){
        String numberOne = editTextNumberOne.getText().toString();
        String numberTwo = editTextNumberTwo.getText().toString();

        int a = Integer.parseInt(numberOne);
        int b = Integer.parseInt(numberTwo);
        long add = a * b;

        String result = String.valueOf(add);
        textViewResult.setText(result);
    }

    public void onClickDivide(View view){
        String numberOne = editTextNumberOne.getText().toString();
        String numberTwo = editTextNumberTwo.getText().toString();

        int a = Integer.parseInt(numberOne);
        int b = Integer.parseInt(numberTwo);
        long add = a / b;

        String result = String.valueOf(add);
        textViewResult.setText(result);
    }

    public void onClickPow(View view){
        String numberOne = editTextNumberOne.getText().toString();
        String numberTwo = editTextNumberTwo.getText().toString();

        int a = Integer.parseInt(numberOne);
        int b = Integer.parseInt(numberTwo);

        Mathematic mathematic = new Mathematic();

        long powResult = mathematic.pow(a,b);

        String result = String.valueOf(powResult);
        textViewResult.setText(result);
    }

    public void onClickFactorial(View view){
        //String numberOne = editTextNumberOne.getText().toString();
        String numberTwo = editTextNumberTwo.getText().toString();

        //int a = Integer.parseInt(numberOne);
        int b = Integer.parseInt(numberTwo);

        long factorialResult = Mathematic.factorial(b);

        String result = String.valueOf(factorialResult);
        textViewResult.setText(result);
    }

    public void onClickShowSecondActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("firstParameter", editTextNumberOne.getText().toString());
        intent.putExtra("secondParameter", editTextNumberTwo.getText().toString());
        intent.putExtra("result", textViewResult.getText().toString());

        startActivity(intent);
    }
    //evento de una actividad

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(NAME_ACTIVITY, "Se ejecuto: onStart()");
        Toast.makeText(this, NAME_ACTIVITY + "" + "Se ejecuto: onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(NAME_ACTIVITY, "Se ejecuto: onRestar()");
        Toast.makeText(this, NAME_ACTIVITY + "" + "Se ejecuto: onRestart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(NAME_ACTIVITY, "Se ejecuto: onStop()");
        Toast.makeText(this, NAME_ACTIVITY + "" + "Se ejecuto: onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(NAME_ACTIVITY, "Se ejecuto: onPause()");
        Toast.makeText(this, NAME_ACTIVITY + "" + "Se ejecuto: onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(NAME_ACTIVITY, "Se ejecuto: onResume()");
        Toast.makeText(this, NAME_ACTIVITY + "" + "Se ejecuto: onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(NAME_ACTIVITY, "Se ejecuto: onDestroy()");
        Toast.makeText(this, NAME_ACTIVITY + "" + "Se ejecuto: onDestroy()", Toast.LENGTH_SHORT).show();
    }
}