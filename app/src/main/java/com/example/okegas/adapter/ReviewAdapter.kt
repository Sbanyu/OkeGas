package com.example.okegas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.okegas.R
import com.example.okegas.databinding.ReviewRowBinding
import com.example.okegas.model.local.entity.Review
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ReviewAdapter(private val context: Context, private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewMovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.review_row, parent, false)
        return ReviewMovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ReviewMovieViewHolder,
        position: Int
    ) {
        holder.bind(this.reviews[position])
    }

    override fun getItemCount(): Int {
        return this.reviews.size
    }

    inner class ReviewMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ReviewRowBinding.bind(view)
        fun bind(review: Review) {
            binding.tvReviewAuthor.text = review.authorDetail.author
            review.authorDetail.rating.apply {
                var rating = review.authorDetail.rating
                if (rating != null) {
                    if (rating >= 8.5) {
                        binding.ivBintang.setImageResource(R.drawable.ic_star_fill)
                    } else if (rating >= 5.0) {
                        binding.ivBintang.setImageResource(R.drawable.ic_one_third_start)
                    } else if (rating >= 1.0) {
                        binding.ivBintang.setImageResource(R.drawable.ic_half_star)
                    }
                    binding.tvMovieRating.text = rating.toString()
                } else {
                    binding.ivBintang.setImageResource(R.drawable.ic_star_no_fill)
                    binding.tvMovieRating.text = context.getString(R.string.no_rating)
                }
            }


            binding.tvMovieReview.text = review.content
            val timeFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val date = LocalDate.parse(review.createdAt, timeFormat)
            binding.tvMovieReviewDate.text = date.toString()
        }
    }
}