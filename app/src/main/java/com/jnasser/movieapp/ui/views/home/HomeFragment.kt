package com.jnasser.movieapp.ui.views.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jnasser.movieapp.databinding.FragmentHomeBinding
import com.jnasser.movieapp.domain.response.movie.MovieResponse
import com.jnasser.movieapp.framework.requestmanager.pagingDataSource.NowPlayingMoviesPagingSource
import com.jnasser.movieapp.presentation.NowPlayingViewModel
import com.jnasser.movieapp.ui.utils.messageToast
import com.jnasser.movieapp.ui.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: NowPlayingViewModel by viewModels()

    private lateinit var nowPlayingAdapter: NowPlayingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNowPLayingMovies()

        initRecyclerViews()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnNowMore.setOnClickListener {

        }
    }

    private fun setUpObservers() {
        observeAdapterState()

        viewModel.getNowPLayingMovies().observe(viewLifecycleOwner) { movies ->
            nowPlayingAdapter.submitData(this@HomeFragment.lifecycle, movies)
        }
    }

    private fun initRecyclerViews() {
        nowPlayingAdapter = NowPlayingAdapter { movieId ->
            val direction = HomeFragmentDirections
                .actionHomeFragmentToMovieDetailFragment(movieId)
            findNavController().safeNavigate(direction)
        }

        with(binding.nowShowingRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = nowPlayingAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun observeAdapterState() {
        nowPlayingAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                requireContext().messageToast("Cargando")
                //binding.progressBar.visibility = View.VISIBLE
            } else {
                requireContext().messageToast("Finalizado")
                //binding.progressBar.visibility = View.GONE

                when(val currentState = loadState.refresh) {
                    is LoadState.Error -> {
                        val message = currentState.error.message
                        when (currentState.error) {
                            is NowPlayingMoviesPagingSource.EmptyListException -> {
                                //TODO("Manejar caso de lista vacia")
                            }
                            is NowPlayingMoviesPagingSource.ErrorException -> {
                                requireContext().messageToast(message.toString())
                            }
                            is HttpException -> {
                                when((currentState.error as HttpException).code()) {
                                    401 -> requireContext().messageToast("Usuario sin autorización")
                                    500 -> requireContext().messageToast("Error de servidor")
                                    else -> Log.d("HttpCode", (currentState.error as HttpException).code().toString())
                                }
                            }
                            else -> {
                                Toast.makeText(requireContext(), "Error de conección", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    is LoadState.NotLoading -> {
                        //TODO("La vista ya cargo")
                    }
                }
            }
        }
    }

}