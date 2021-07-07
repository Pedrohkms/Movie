package com.pedro.movies.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedro.movies.data.entities.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: Int): LiveData<Movie>

    @Query("SELECT * FROM movies WHERE isFavorited = 1")
    fun getMoviesFav() : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movies: Movie)

    @Query("UPDATE movies SET isFavorited = :isFav WHERE id = :id")
    suspend fun updateFav(isFav : Boolean, id: Int)
}