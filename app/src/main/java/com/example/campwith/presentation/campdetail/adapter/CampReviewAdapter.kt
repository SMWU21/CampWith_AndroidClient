package com.example.campwith.presentation.campdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.campwith.R
import com.example.campwith.data.review.response.ReviewResponseItem
import com.example.campwith.databinding.ItemCampReviewBinding

class CampReviewAdapter : RecyclerView.Adapter<CampReviewAdapter.Holder>() {

    private var reviewList = mutableListOf<ReviewResponseItem>()
    var onClick: ((ReviewResponseItem, Int) -> Unit)? = null

    fun addAll(newReviewList: List<ReviewResponseItem>) {
        reviewList.clear()
        reviewList.addAll(newReviewList)
        notifyDataSetChanged()
    }

    fun addOne(review: ReviewResponseItem) {
        reviewList.add(0, review)
        notifyItemInserted(0)
    }

    fun modifyOne(review: ReviewResponseItem, position: Int) {
        reviewList[position] = review
        notifyItemChanged(position)
    }

    inner class Holder(private val binding: ItemCampReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewItem: ReviewResponseItem) {
            binding.itemReview = reviewItem
            binding.tvModify.setOnClickListener {
                onClick?.invoke(reviewItem, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_camp_review,
            parent,
            false
        )
    )

    override fun getItemCount() = reviewList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(reviewList[position])
    }
}