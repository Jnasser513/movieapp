package com.jnasser.movieapp.ui.views.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.jnasser.movieapp.R
import com.jnasser.movieapp.databinding.FragmentMovieDetailBinding
import com.jnasser.movieapp.domain.mappers.toMovieCastEntityList
import com.jnasser.movieapp.domain.mappers.toMovieGenreEntityList
import com.jnasser.movieapp.domain.mappers.toMovieLanguageEntityList
import com.jnasser.movieapp.domain.response.UIStatus
import com.jnasser.movieapp.domain.response.movie.MovieCast
import com.jnasser.movieapp.domain.response.movie.MovieCastResponse
import com.jnasser.movieapp.domain.response.movie.MovieDetailResponse
import com.jnasser.movieapp.domain.response.movie.MovieGenre
import com.jnasser.movieapp.domain.response.videos.VideoResponse
import com.jnasser.movieapp.framework.databasemanager.entities.MovieEntity
import com.jnasser.movieapp.presentation.MovieDetailViewModel
import com.jnasser.movieapp.ui.utils.messageToast
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var mBinding: FragmentMovieDetailBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: MovieDetailViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()

    private val castAdapter = CastAdapter()

    private lateinit var movieDetail: MovieDetailResponse
    private lateinit var movieCast: List<MovieCast>

    private var isFavorite: Boolean = false

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

        initRecyclerViews()

        this@MovieDetailFragment.lifecycle.addObserver(binding.videoPlayer)
        viewModel.getMovieDetail(args.movieId)
        viewModel.getVideo(args.movieId)
        viewModel.getMovieCast(args.movieId)
        viewModel.getLocalMoviesIds(args.movieId)

        setUpListeners()
        setUpObservers()
    }

    private fun initRecyclerViews() {
        with(binding.castRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun setUpListeners() {
        binding.btnReturn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnFavorite.setOnClickListener {
            if(isFavorite) {
                viewModel.deleteMovie(args.movieId)
            } else {
                movieDetail.title?.let { it1 ->
                    movieDetail.point?.let { it2 ->
                        movieDetail.genres?.toMovieGenreEntityList()?.let { it3 ->
                            movieDetail.time?.let { it4 ->
                                movieDetail.description?.let { it5 ->
                                    MovieEntity(
                                        args.movieId,
                                        it1,
                                        it2,
                                        it3,
                                        it4,
                                        movieDetail.languages?.toMovieLanguageEntityList(),
                                        it5,
                                        movieCast.toMovieCastEntityList()
                                    )
                                }
                            }
                        }
                    }
                }?.let { it2 -> viewModel.insertMovie(it2) }
            }
        }
    }

    private fun setUpObservers() {
        viewModel.statusVideos.observe(viewLifecycleOwner) { status ->
            handleVideoState(status)
        }

        viewModel.statusDetail.observe(viewLifecycleOwner) { status ->
            handleDetailState(status)
        }

        viewModel.statusCast.observe(viewLifecycleOwner) { status ->
            handleCastState(status)
        }

        viewModel.statusInsertMovie.observe(viewLifecycleOwner) { status ->
            handleInsertMovieStatus(status)
        }

        viewModel.statusLocalMoviesIds.observe(viewLifecycleOwner) { status ->
            handleLocalMovieIdsStatus(status)
        }

        viewModel.statusDeleteMovie.observe(viewLifecycleOwner) { status ->
            handleDeleteMovieStatus(status)
        }
    }

    private fun handleVideoState(status: UIStatus<List<VideoResponse>>?) {
        when (status) {
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
        when (status) {
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
                    movieDetail = it
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

    private fun handleCastState(status: UIStatus<MovieCastResponse>?) {
        when (status) {
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
                status.data?.cast?.let {
                    movieCast = it
                    castAdapter.setData(it)
                }
            }

            is UIStatus.EmptyList -> {

            }

            is UIStatus.LogOut -> {
                requireContext().messageToast(status.message)
            }
        }
    }

    private fun handleInsertMovieStatus(status: UIStatus<Long>) {
        when (status) {
            is UIStatus.Error -> {
                //Aqui podemos manejar las diferentes excepciones
                requireContext().messageToast("Algo salio mal...")
            }

            is UIStatus.ErrorWithMessage -> {
                requireContext().messageToast(status.message)
            }

            is UIStatus.Loading -> {

            }

            is UIStatus.Success -> {
                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_bookmar_saved))
            }

            is UIStatus.EmptyList -> {
                requireContext().messageToast("No se pudo agregar a favoritos")
            }

            is UIStatus.LogOut -> {
                requireContext().messageToast(status.message)
            }
        }
    }

    private fun handleLocalMovieIdsStatus(status: UIStatus<Int>) {
        when (status) {
            is UIStatus.Error -> {
                //Aqui podemos manejar las diferentes excepciones
                requireContext().messageToast("Algo salio mal...")
            }

            is UIStatus.ErrorWithMessage -> {
                requireContext().messageToast(status.message)
            }

            is UIStatus.Loading -> {

            }

            is UIStatus.Success -> {
                isFavorite = true
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_bookmar_saved
                    )
                )
            }

            is UIStatus.EmptyList -> {
                isFavorite = false
                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_bookmark))
            }

            is UIStatus.LogOut -> {
                requireContext().messageToast(status.message)
            }
        }
    }

    private fun handleDeleteMovieStatus(status: UIStatus<Int>) {
        when (status) {
            is UIStatus.Error -> {
                //Aqui podemos manejar las diferentes excepciones
                requireContext().messageToast("Algo salio mal...")
            }

            is UIStatus.ErrorWithMessage -> {
                requireContext().messageToast(status.message)
            }

            is UIStatus.Loading -> {

            }

            is UIStatus.Success -> {
                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_bookmark))
            }

            is UIStatus.EmptyList -> {

            }

            is UIStatus.LogOut -> {
                requireContext().messageToast(status.message)
            }
        }
    }

    private fun setTypes(types: List<MovieGenre>) {
        val chips = binding.typesGroup
        chips.removeAllViews()
        types.forEach { item ->
            chips.addView(Chip(requireContext()).apply {
                text = item.name
                setTextColor(ContextCompat.getColor(requireContext(), R.color.third_color))
                setChipBackgroundColorResource(R.color.fourth_color)
            })
        }
    }

    private fun playTrailer(videoUrl: String) {
        binding.videoPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
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