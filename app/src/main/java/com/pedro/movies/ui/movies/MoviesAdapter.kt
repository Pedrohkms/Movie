package com.pedro.movies.ui.movies

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pedro.movies.data.entities.Movie
import com.pedro.movies.databinding.ItemMovieBinding

class MoviesAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<MovieViewHolder>() {

    interface MovieItemListener {
        fun onClickedMovie(movieId: Int)
    }

    private val items = ArrayList<Movie>()

    fun setItems(items: List<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(items[position])
}

class MovieViewHolder(
    private val itemBinding: ItemMovieBinding,
    private val listener: MoviesAdapter.MovieItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: Movie

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Movie) {
        this.movie = item
        itemBinding.titleMovie.text = item.title
        val imagePoster = item.poster_path
        Glide.with(itemBinding.root)
            .load("https://image.tmdb.org/t/p/w342${item.poster_path}")
            .into(itemBinding.itemMoviePoster)
        Log.d("imagem", "https://image.tmdb.org/t/p/w342${item.poster_path}")
        Log.d("imagem", imagePoster.toString())
    }

    override fun onClick(v: View?) {
        listener.onClickedMovie(movie.id)
    }
}