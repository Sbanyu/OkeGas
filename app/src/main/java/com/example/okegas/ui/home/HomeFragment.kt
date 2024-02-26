package com.example.okegas.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.okegas.adapter.NowPlayingMovieAdapter
import com.example.okegas.adapter.PopularMovieAdapter
import com.example.okegas.adapter.TopRatedMovieAdapter
import com.example.okegas.databinding.FragmentHomeBinding
import com.example.okegas.model.local.entity.Movie
import com.example.okegas.ui.detail.DetailViewModel
import com.example.okegas.utils.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvPopularMovie: RecyclerView
    private lateinit var rvTopRatedMovie: RecyclerView
    private lateinit var rvNowPlayingMovie: RecyclerView
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelVideos: DetailViewModel
    private lateinit var addFavorite: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(
            this, factory
        )[HomeViewModel::class.java]

        val key = ViewModelFactory.getInstance(requireContext())
        viewModelVideos = ViewModelProvider(
            this, key
        )[DetailViewModel::class.java]

        addFavorite = ViewModelProvider(this)[DetailViewModel::class.java]

        rvPopularMovie = binding.rvPopularMovie
        rvPopularMovie.setHasFixedSize(true)

        rvTopRatedMovie = binding.rvTopRatedMovie
        rvTopRatedMovie.setHasFixedSize(true)

        rvNowPlayingMovie = binding.rvNowPlayingMovie
        rvNowPlayingMovie.setHasFixedSize(true)

        Handler(Looper.getMainLooper()).postDelayed({
            loadRecyclerView()
            binding.pbHome.visibility = View.INVISIBLE
            binding.svHome.visibility = View.VISIBLE
        }, 1000)

        return binding.root
    }

    private fun loadRecyclerView() {
        rvPopularMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTopRatedMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvNowPlayingMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.loadPopularMovie { movies: List<Movie> ->
            val movieToAddToFavorites = movies.firstOrNull()

            movieToAddToFavorites?.let { movie ->
                viewModelVideos.addMovieToFavorite(movie)
            }

            rvPopularMovie.adapter = PopularMovieAdapter(movies,viewModelVideos,addFavorite)

        }

        viewModel.loadTopRatedMovies { movies: List<Movie> ->
            rvTopRatedMovie.adapter = TopRatedMovieAdapter(movies)
        }

        viewModel.loadNowPlayingMovies { movies: List<Movie> ->
            rvNowPlayingMovie.adapter = NowPlayingMovieAdapter(movies)
        }
    }

    override fun onResume() {
        super.onResume()
        loadRecyclerView()
        binding.pbHome.visibility = View.INVISIBLE
        binding.svHome.visibility = View.VISIBLE
    }
}