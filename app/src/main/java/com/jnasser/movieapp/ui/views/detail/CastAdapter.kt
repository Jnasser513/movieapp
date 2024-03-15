package com.jnasser.movieapp.ui.views.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jnasser.movieapp.databinding.ItemCastBinding
import com.jnasser.movieapp.domain.response.movie.MovieCast
import com.jnasser.movieapp.domain.response.movie.MovieCastResponse

class CastAdapter: RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private var castList: List<MovieCast>? = null

    inner class CastViewHolder(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieCast) {
            binding.cast = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCastBinding.inflate(inflater, parent, false)

        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val item = castList?.get(position)
        item?.let { holder.bind(it) }
    }

    override fun getItemCount() = castList?.size ?: 0

    fun setData(list: List<MovieCast>) {
        castList = list
        notifyDataSetChanged()
    }

}