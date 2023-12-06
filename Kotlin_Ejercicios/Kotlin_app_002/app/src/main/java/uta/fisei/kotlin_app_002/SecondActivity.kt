package uta.fisei.kotlin_app_002

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uta.fisei.kotlin_app_002.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_second)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonShow.setOnClickListener {
            val message = binding.editTextMessage.text.toString()

            // Llamada al nuevo método
            showToast("Bienvenido: $message", Toast.LENGTH_LONG)
        }
        binding.buttonFinish.setOnClickListener {
            finish()
        }
    }
    private fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }
}
