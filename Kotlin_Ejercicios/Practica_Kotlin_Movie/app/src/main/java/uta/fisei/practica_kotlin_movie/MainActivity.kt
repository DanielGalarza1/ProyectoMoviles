package uta.fisei.practica_kotlin_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uta.fisei.practica_kotlin_movie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = MoviesAdapter(
            listOf(
                Movie("Titulo 1","https://loremflickr.com/320/240?lock=7"),
                Movie("Titulo 2","https://loremflickr.com/320/240?lock=2"),
                Movie("Titulo 3","https://loremflickr.com/320/240?lock=3"),
                Movie("Titulo 4","https://loremflickr.com/320/240?lock=4"),
                Movie("Titulo 5","https://loremflickr.com/320/240?lock=5"),
                Movie("Titulo 6","https://loremflickr.com/320/240?lock=6"),
            )
        )
            {movie->
                Toast.makeText(this@MainActivity, movie.title, Toast.LENGTH_SHORT).show()
            }
        }
}