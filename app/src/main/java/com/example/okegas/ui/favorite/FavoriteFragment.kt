package com.example.okegas.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.okegas.adapter.FavoriteAdapter
import com.example.okegas.databinding.FragmentFavoriteBinding
import com.example.okegas.utils.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var rvFavoriteMovie: RecyclerView
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        adapter = FavoriteAdapter()

        rvFavoriteMovie = binding.rvFavoriteMovie
        rvFavoriteMovie.setHasFixedSize(true)
        binding.rvFavoriteMovie.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavoriteMovie.adapter = adapter

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(
            this, factory
        )[FavoriteViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteMovieList().observe(
                viewLifecycleOwner
            ) { movies ->
                adapter.setData(movies).apply {
                    if (movies.isNotEmpty()) {
                        binding.rvFavoriteMovie.visibility = View.VISIBLE
                        binding.ivEmptyFavorite.visibility = View.INVISIBLE
                    } else {
                        binding.rvFavoriteMovie.visibility = View.INVISIBLE
                        binding.ivEmptyFavorite.visibility = View.VISIBLE
                    }
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteMovieList().observe(
                viewLifecycleOwner
            ) { movies ->
                adapter.setData(movies).apply {
                    if (movies.isNotEmpty()) {
                        binding.rvFavoriteMovie.visibility = View.VISIBLE
                        binding.ivEmptyFavorite.visibility = View.INVISIBLE
                    } else {
                        binding.rvFavoriteMovie.visibility = View.INVISIBLE
                        binding.ivEmptyFavorite.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}