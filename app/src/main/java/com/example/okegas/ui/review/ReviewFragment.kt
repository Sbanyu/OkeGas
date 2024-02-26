package com.example.okegas.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.okegas.R
import com.example.okegas.adapter.ReviewAdapter
import com.example.okegas.model.local.entity.Review
import com.example.okegas.utils.ViewModelFactory

class ReviewFragment : Fragment() {
    private lateinit var rvMovieReview: RecyclerView
    private lateinit var reviewViewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireContext())
        reviewViewModel =
            ViewModelProvider(
                this, factory
            )[ReviewViewModel::class.java]

        val view: View = inflater.inflate(R.layout.fragment_review, container, false)

        rvMovieReview = view.findViewById(R.id.rv_movie_review) as RecyclerView
        rvMovieReview.setHasFixedSize(true)

        reviewViewModel.loadMovieReview(requireActivity().intent) { reviews: List<Review> ->
            rvMovieReview.adapter = ReviewAdapter(requireContext(), reviews)
        }

        rvMovieReview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }
}
