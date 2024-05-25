package com.example.chapter_5.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chapter_5.R
import com.example.chapter_5.model.response.Result

class MovieAdapter(
    private var movies: List<Result>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.noteViewHolder>() {
    inner class noteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleMovie: TextView = itemView.findViewById(R.id.title_movie)
        val releaseMovie: TextView = itemView.findViewById(R.id.release_text)
        val popularityMovie: TextView = itemView.findViewById(R.id.popularity_text)
        val posterMovie: ImageView = itemView.findViewById(R.id.posterMovie)

        fun bind(movie: Result) {

            titleMovie.text = movie.title
            releaseMovie.text = movie.releaseDate
            popularityMovie.text = movie.popularity.toString()

            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

            Glide.with(itemView.context)
                .load(posterUrl)
                .into(posterMovie)

            itemView.setOnClickListener {
                onItemClick(movie.id)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.noteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
         return noteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieAdapter.noteViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount() = movies.size

    fun updateMovies(newMovies: List<Result>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}