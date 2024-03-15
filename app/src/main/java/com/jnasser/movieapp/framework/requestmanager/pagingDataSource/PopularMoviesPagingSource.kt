package com.jnasser.movieapp.framework.requestmanager.pagingDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jnasser.movieapp.domain.response.movie.MovieResponse
import com.jnasser.movieapp.framework.requestmanager.APIConstants
import com.jnasser.movieapp.framework.requestmanager.APIService
import okio.IOException
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

class PopularMoviesPagingSource(
    private val service: APIService
) : PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val call = service.getPopularMovies(APIConstants.LANGUAGE, position)
            val response = call.body()
            val message = response?.message ?: "Lista recuperada exitosamente"
            val data = response?.results ?: emptyList<MovieResponse>()

            if(call.isSuccessful) {
                if (response?.success == false) {
                    throw ErrorException(message)
                } else {
                    if (data.isEmpty()) {
                        throw EmptyListException()
                    } else {
                        LoadResult.Page(
                            data = data,
                            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                            nextKey = if (data.isEmpty()) null else position + 1
                        )
                    }
                }
            } else {
                throw HttpException(call)
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    class ErrorException(message: String) : IOException(message)
    class EmptyListException : IOException()
}