package uta.fisei.kotlin_app_002

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import uta.fisei.kotlin_app_002.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val editTextUserName = findViewById<EditText>(R.id.editTextUserName)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonOk = findViewById<Button>(R.id.buttonOk)

        buttonOk.setOnClickListener {
            val username = editTextUserName.text.toString()
            val password = editTextPassword.text.toString()

            if (username != "" && password != "") {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Los datos son Obligatorios", Toast.LENGTH_LONG).show()
            }
        }*/

        binding.buttonOk.setOnClickListener {
            val username = binding.editTextUserName.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (username != "" && password != "") {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Los datos son Requeridos", Toast.LENGTH_LONG).show()
            }
        }
    }
}
