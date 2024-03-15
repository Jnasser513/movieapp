package com.jnasser.movieapp.ui.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jnasser.movieapp.databinding.ItemShowingMovieBinding
import com.jnasser.movieapp.domain.response.movie.MovieResponse
import com.jnasser.movieapp.framework.requestmanager.APIConstants

class NowPlayingAdapter(private val onClick: (Int) -> Unit): PagingDataAdapter<MovieResponse, NowPlayingAdapter.NowPlayingViewHolder>(ITEM_COMPARATOR) {

    inner class NowPlayingViewHolder(private val binding: ItemShowingMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieResponse) {
            binding.movie = item
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.NowPlayingViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.NowPlayingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShowingMovieBinding.inflate(inflater, parent, false)

        return NowPlayingViewHolder(binding)
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<MovieResponse>() {
            override fun areItemsTheSame(
                oldItem: MovieResponse,
                newItem: MovieResponse
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MovieResponse,
                newItem: MovieResponse
            ) = oldItem == newItem
        }
    }

}