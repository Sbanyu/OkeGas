package com.example.okegas.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.okegas.R
import com.example.okegas.databinding.MovieRowPopularBinding
import com.example.okegas.model.local.entity.Movie
import com.example.okegas.ui.detail.DetailActivity
import com.example.okegas.ui.detail.DetailViewModel

class PopularMovieAdapter(
    private val movies: List<Movie>,
    private val viewModelVideos: DetailViewModel,
    private val addFavorite: DetailViewModel
) :
    RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_row_popular, parent, false)
        return PopularMovieViewHolder(view, viewModelVideos, addFavorite) // Teruskan viewModelVideos ke view holder
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return this.movies.size
    }

    class PopularMovieViewHolder(view: View, private val viewModelVideos: DetailViewModel,addFavorite: DetailViewModel) : RecyclerView.ViewHolder(view) {
        private val binding = MovieRowPopularBinding.bind(view)
        private val context = view.context

        fun bind(movie: Movie) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + movie.backdrop)
                .into(binding.imgMovie)
            binding.tvMovieTitle.text = movie.title

            binding.imgMovie.setOnClickListener {
                val moveActivity = Intent(context, DetailActivity::class.java)
                moveActivity.putExtra(DetailActivity.EXTRA_DETAILS, movie)
                context.startActivity(moveActivity)
            }

            // Load video key when the movie is bound to the view holder
            viewModelVideos.loadMovieVideos(movie) { videos ->
                if (videos.isNotEmpty()) {
                    val videoKey = videos[0].key
                    binding.btPlayYoutube.setOnClickListener {
                        val youtubeUrl = "https://www.youtube.com/watch?v=$videoKey"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                        context.startActivity(intent)
                    }
                } else {
                    // Handle case when there are no videos available
                }
            }

            binding.mylist.setOnClickListener {
                viewModelVideos.addMovieToFavorite(movie) // Panggil listener untuk menambahkan item ke daftar favorit
                Toast.makeText(context, "${movie.title} added to favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
