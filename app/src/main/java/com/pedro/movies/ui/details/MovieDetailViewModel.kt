package com.pedro.movies.ui.details

import androidx.lifecycle.*
import com.pedro.movies.data.entities.Movie
import com.pedro.movies.data.local.MovieDao
import com.pedro.movies.data.repository.MovieRepository
import com.pedro.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val dao: MovieDao
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _movie = _id.switchMap { id ->
        repository.getMovie(id)
    }
    val movie: LiveData<Resource<Movie>> = _movie


    fun start(id: Int) {
        _id.value = id
    }

    fun update(isFav : Boolean, id : Int){
        viewModelScope.launch {
            dao.updateFav(isFav, id)
        }
    }

}
