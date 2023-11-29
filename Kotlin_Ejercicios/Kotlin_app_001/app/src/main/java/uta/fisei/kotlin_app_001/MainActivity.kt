package uta.fisei.kotlin_app_001

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextNumberOne = findViewById<EditText>(R.id.editTextNumberOne)
        val editTextNumberTwo = findViewById<EditText>(R.id.editTextNumberTwo)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val a = editTextNumberOne.text.toString().toInt()
            val b = editTextNumberTwo.text.toString().toInt()
            val add = a+b;

            textViewResult.text = add.toString()
            //textViewResult.setText(add.toString());
        }
    }
}