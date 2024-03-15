package com.jnasser.movieapp.ui.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jnasser.movieapp.databinding.ItemShowingMovieBinding
import com.jnasser.movieapp.domain.response.movie.MovieResponse

class PopularAdapter(private val onClick: (Int) -> Unit): RecyclerView.Adapter<PopularAdapter.NowPlayingViewHolder>() {

    private var movieList: List<MovieResponse>? = null

    inner class NowPlayingViewHolder(private val binding: ItemShowingMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieResponse) {
            binding.movie = item
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShowingMovieBinding.inflate(inflater, parent, false)

        return NowPlayingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val item = movieList?.get(position)
        item?.let { holder.bind(it) }
    }

    override fun getItemCount() = movieList?.size ?: 0

    fun setData(list: List<MovieResponse>) {
        movieList = list
        notifyDataSetChanged()
    }

}