package com.example.chapter_5.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter_5.R
import com.example.chapter_5.model.response.Result

class MovieAdapter(
    private var movies: List<Result>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.noteViewHolder>() {
    inner class noteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleMovie: TextView = itemView.findViewById(R.id.title_movie)
        val overviewMovie: TextView = itemView.findViewById(R.id.overview_movie)

        fun bind(movie: Result) {
            titleMovie.text = movie.originalTitle
            overviewMovie.text = movie.overview

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