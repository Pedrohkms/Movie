package com.pedro.movies.data.remote

import com.pedro.movies.data.LocalData
import com.pedro.movies.data.entities.Movie
import com.pedro.movies.data.entities.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("/3/movie/now_playing")
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String = LocalData().API_KEY,
        @Query("language") language : String = LocalData().language,
        @Query("region") region : String = LocalData().region
    ): Response<MovieList>

    @GET("/3/movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = LocalData().API_KEY,
        @Query("language") language : String = LocalData().language,
        @Query("region") region : String = LocalData().region
    ): Response<Movie>

}