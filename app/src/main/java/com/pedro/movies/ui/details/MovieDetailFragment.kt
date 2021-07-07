package com.pedro.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.pedro.movies.R
import com.pedro.movies.data.entities.Movie
import com.pedro.movies.databinding.MovieDetailFragmentBinding
import com.pedro.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: MovieDetailFragmentBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindMovie(it.data!!)
                    binding.progressBar.visibility = View.GONE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun bindMovie(movie: Movie) {
        binding.movieTitleDescriptionView.text = movie.title
        binding.releaseDate.text = movie.release_date
        binding.popularity.text = movie.popularity.toString()
        binding.note.text = movie.vote_average.toString()
        binding.description.text = movie.overview
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/w342${movie.poster_path}")
            .into(binding.imageCoverDescription)

        if (!movie.isFavorited){
            binding.favButton.setOnClickListener {
                viewModel.update(true, movie.id)
                binding.favButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0)
                binding.favButton.text = "Favorito"
            }
        } else {
            binding.favButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0)
            binding.favButton.text = "Favorito"
            binding.favButton.setOnClickListener {
                viewModel.update(false, movie.id)
                binding.favButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0)
                binding.favButton.text = "Favoritar"
            }
        }
    }
}
