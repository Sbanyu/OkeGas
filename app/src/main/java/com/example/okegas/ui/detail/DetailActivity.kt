package com.example.okegas.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.okegas.R
import com.example.okegas.databinding.ActivityDetailBinding
import com.example.okegas.model.local.entity.Movie
import com.example.okegas.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var viewModel: DetailViewModel


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this, factory
        )[DetailViewModel::class.java]

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DETAILS, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(EXTRA_DETAILS) as Movie
        }


        if (movie != null) {
            viewModel.loadMovieVideos(movie) { videos ->
                if (videos.isNotEmpty()) {
                    val videoKey = videos[0].key // Ambil kunci video dari objek pertama dalam daftar video
                    binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoKey, 0f)
                        }
                    })

                    binding.btPlayYoutube.setOnClickListener {
                        val youtubeUrl = "https://www.youtube.com/watch?v=$videoKey"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                        startActivity(intent)
                    }

                    val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            // using pre-made custom ui
                            val defaultPlayerUiController =
                                DefaultPlayerUiController(binding.youtubePlayerView, youTubePlayer)
                            binding.youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                        }
                    }

                    // disable iframe ui
                    val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
                    binding.youtubePlayerView.initialize(listener, options)
                } else {
                    // Handle case when there are no videos available
                }
            }
        } else {
            Toast.makeText(this, "Data is not retrieved yet", Toast.LENGTH_SHORT).show()
        }

        if (movie != null) {
            binding.tvMovieTitle.text = movie.title
            val timeFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(movie.releaseDate, timeFormat)
            binding.tvMovieReleaseDate.text = date.year.toString()

            var overview = movie.overview
            if (overview.length > 400) {
                overview = overview.substring(0, 400)
            }
            binding.tvMovieDesc.text = "$overview"

            binding.tvVoteAverage.text = movie.voteAverage.toString()
        } else {
            Toast.makeText(this, "Data is not retrieved yet", Toast.LENGTH_SHORT).show()
        }

        val id = movie!!.id

        var isCheck = false
        CoroutineScope(Dispatchers.Default).launch {
            val count = viewModel.checkMovieIsFavorite(id)
            isCheck = if (count > 0.toString()) {
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity, R.drawable.baseline_favorite_24
                    )
                )
                true
            } else {
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity, R.drawable.baseline_favorite_border_24
                    )
                )
                false
            }
        }

        binding.fabFavorites.setOnClickListener {
            isCheck = !isCheck
            if (isCheck) {
                viewModel.addMovieToFavorite(movie)
                Toast.makeText(this, "${movie.title} added to favorite", Toast.LENGTH_SHORT).show()
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity, R.drawable.baseline_favorite_24
                    )
                )
            } else {
                viewModel.deleteMovieFromFavorite(movie)
                Toast.makeText(this, "${movie.title} removed from favorite", Toast.LENGTH_SHORT)
                    .show()
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity, R.drawable.baseline_favorite_border_24
                    )
                )
            }
        }

        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("InflateParams")
    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
    }

    companion object {
        const val EXTRA_DETAILS = "extra_details"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                showBottomSheet()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}