package com.pedro.movies.data.remote

import com.pedro.movies.data.LocalData
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {

    suspend fun getMovies() =
        getResult { movieService.getAllMovies(LocalData().API_KEY, LocalData().language, LocalData().region) }
    suspend fun getMovie(id: Int) =
        getResult { movieService.getMovie(id, LocalData().API_KEY, LocalData().language, LocalData().region) }

}