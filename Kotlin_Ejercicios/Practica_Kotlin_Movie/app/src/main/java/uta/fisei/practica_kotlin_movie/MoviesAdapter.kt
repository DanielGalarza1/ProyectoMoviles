package uta.fisei.practica_kotlin_movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uta.fisei.practica_kotlin_movie.databinding.ViewMovieItemBinding

class MoviesAdapter(private val movies: List<Movie>,
                    private val movieClickedListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        holder.bind(movie)

        holder.itemView.setOnClickListener {
            movieClickedListener (movie)
        }
    }

    class ViewHolder(private val binding: ViewMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.textViewTitle.text = movie.title

            Glide
                .with(binding.root.context)
                .load(movie.cover).
                into(binding.imageViewCover)
        }
    }
}

/*
interface  MovieClickedListener{
    fun onMovieClicked(movie: Movie)
}
 */


