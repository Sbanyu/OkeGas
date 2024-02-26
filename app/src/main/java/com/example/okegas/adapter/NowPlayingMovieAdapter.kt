package com.example.okegas.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.okegas.R
import com.example.okegas.databinding.MovieRowBinding
import com.example.okegas.model.local.entity.Movie
import com.example.okegas.ui.detail.DetailActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NowPlayingMovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<NowPlayingMovieAdapter.NowPlayingMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return NowPlayingMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        holder.bind(this.movies[position])
    }

    override fun getItemCount(): Int {
        return this.movies.size

    }

    inner class NowPlayingMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MovieRowBinding.bind(view)
        fun bind(movie: Movie) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(binding.imgMovie)
            binding.tvMovieTitle.text = movie.title

            val timeFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(movie.releaseDate, timeFormat)
            binding.tvMovieReleaseDate.text = date.year.toString()

            binding.cvMovie.setOnClickListener {
                val moveActivity = Intent(itemView.context, DetailActivity::class.java)
                moveActivity.putExtra(DetailActivity.EXTRA_DETAILS, movie)
                itemView.context.startActivity(moveActivity)
            }
        }
    }
}