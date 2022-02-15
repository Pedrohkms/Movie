package com.pedro.movies.data.repository

import com.pedro.movies.data.local.MovieDao
import com.pedro.movies.data.remote.MovieRemoteDataSource
import com.pedro.movies.utils.performGetOperation
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getMovie(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getMovie(id) },
        networkCall = { remoteDataSource.getMovie(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies() },
        networkCall = { remoteDataSource.getMovies() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )

    fun getMoviesFav() = performGetOperation(
        databaseQuery = { localDataSource.getMoviesFav() },
        networkCall = { remoteDataSource.getMovies() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )

}