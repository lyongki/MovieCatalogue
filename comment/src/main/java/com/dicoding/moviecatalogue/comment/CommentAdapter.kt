package com.dicoding.moviecatalogue.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moviecatalogue.comment.databinding.ItemsCommentBinding
import com.dicoding.moviecatalogue.core.domain.model.Comment

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    var list: List<Comment> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemsCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.tvComment.text = comment.comment
            binding.tvDate.text = comment.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}