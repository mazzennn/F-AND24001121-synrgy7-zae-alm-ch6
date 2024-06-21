package com.example.chapter_5.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chapter_5.R
import com.example.chapter_5.domain.model.MovieResult

class MovieAdapter(
    private var movies: List<MovieResult> = listOf(),
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleMovie: TextView = itemView.findViewById(R.id.title_movie)
        private val releaseMovie: TextView = itemView.findViewById(R.id.release_text)
        private val popularityMovie: TextView = itemView.findViewById(R.id.popularity_text)
        private val posterMovie: ImageView = itemView.findViewById(R.id.posterMovie)

        fun bind(movie: MovieResult) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie) // Panggil method bind() dari ViewHolder dengan data movie pada posisi saat ini
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateMovies(newMovies: List<MovieResult>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}