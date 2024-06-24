package com.example.myneighborhoodv2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborhoodv2.data.BlogPost
import com.example.myneighborhoodv2.databinding.ItemBlogPostBinding

class BlogPostAdapter: RecyclerView.Adapter<BlogPostAdapter.BlogPostsViewHolder>() {
    inner class BlogPostsViewHolder(private val binding: ItemBlogPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(BlogPost: BlogPost) {
            binding.apply {
                postTitle.text = BlogPost.postTitle
                postDetails.text = BlogPost.postDetails
                postDetails.text = BlogPost.postDate
            }

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<BlogPost>() {
        override fun areItemsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogPostsViewHolder {
        return BlogPostsViewHolder(
            ItemBlogPostBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BlogPostsViewHolder, position: Int) {
        val BlogPost = differ.currentList[position]
        holder.bind(BlogPost)

        holder.itemView.setOnClickListener {
            onClick?.invoke(BlogPost)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((BlogPost) -> Unit)? = null

}