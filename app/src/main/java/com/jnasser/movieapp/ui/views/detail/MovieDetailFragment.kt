package com.jnasser.movieapp.ui.views.detail

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.MediaController
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.jnasser.movieapp.R
import com.jnasser.movieapp.databinding.FragmentMovieDetailBinding
import com.jnasser.movieapp.domain.response.UIStatus
import com.jnasser.movieapp.domain.response.movie.MovieDetailResponse
import com.jnasser.movieapp.domain.response.movie.MovieGenre
import com.jnasser.movieapp.domain.response.videos.VideoResponse
import com.jnasser.movieapp.framework.requestmanager.APIConstants
import com.jnasser.movieapp.presentation.MovieDetailViewModel
import com.jnasser.movieapp.ui.utils.messageToast
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MovieDetailFragment: Fragment() {

    private var mBinding: FragmentMovieDetailBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: MovieDetailViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this@MovieDetailFragment.lifecycle.addObserver(binding.videoPlayer)
        viewModel.getVideo(args.movieId)
        viewModel.getMovieDetail(args.movieId)

        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.btnReturn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpObservers() {
        viewModel.statusVideos.observe(viewLifecycleOwner) { status ->
            handleVideoState(status)
        }

        viewModel.statusDetail.observe(viewLifecycleOwner) { status ->
            handleDetailState(status)
        }
    }

    private fun handleVideoState(status: UIStatus<List<VideoResponse>>?) {
        when(status) {
            is UIStatus.Error -> {
                //Aqui podemos manejar las diferentes excepciones
                requireContext().messageToast("Algo salio mal...")
            }
            is UIStatus.ErrorWithMessage -> {
                requireContext().messageToast(status.message)
            }
            is UIStatus.Loading -> {
                //Aqui podemos manejar el estado de cargando
            }
            is UIStatus.Success -> {
                val trailer = status.data?.firstOrNull() { it.type == "Trailer" }
                playTrailer(trailer?.key ?: status.data?.get(0)?.key.toString())
            }
            is UIStatus.EmptyList -> {

            }
            is UIStatus.LogOut -> {
                requireContext().messageToast(status.message)
            }
        }
    }

    private fun handleDetailState(status: UIStatus<MovieDetailResponse>?) {
        when(status) {
            is UIStatus.Error -> {
                //Aqui podemos manejar las diferentes excepciones
                requireContext().messageToast("Algo salio mal...")
            }
            is UIStatus.ErrorWithMessage -> {
                requireContext().messageToast(status.message)
            }
            is UIStatus.Loading -> {
                //Aqui podemos manejar el estado de cargando
            }
            is UIStatus.Success -> {
                status.data?.let {
                    binding.movie = it
                    binding.executePendingBindings()
                }
                status.data?.genres?.let {
                    setTypes(it)
                }
            }

            is UIStatus.EmptyList -> {

            }
            is UIStatus.LogOut -> {
                requireContext().messageToast(status.message)
            }
        }
    }

    private fun setTypes(types: List<MovieGenre>){
        val chips = binding.typesGroup
        chips.removeAllViews()
        types.forEach{ item ->
            chips.addView(Chip(requireContext()).apply {
                text = item.name
                setTextColor(ContextCompat.getColor(requireContext(), R.color.third_color))
                setChipBackgroundColorResource(R.color.fourth_color)
            })
        }
    }

    private fun playTrailer(videoUrl: String) {
        binding.videoPlayer.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoUrl, 0f)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}