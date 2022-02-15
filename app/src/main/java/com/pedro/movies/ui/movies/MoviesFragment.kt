package com.pedro.movies.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.pedro.movies.R
import com.pedro.movies.databinding.MoviesFragmentBinding
import com.pedro.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(), MoviesAdapter.MovieItemListener {

    private lateinit var binding: MoviesFragmentBinding
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = MoviesAdapter(this)
        binding.moviesRV.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.moviesRV.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.movies.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(it.data)

                }
                Resource.Status.ERROR ->
                    Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabFav()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                return
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tabFav()
            }

        })

    }

    private fun tabFav() {
        when (binding.tabLayout.selectedTabPosition) {
            0 -> {
                viewModel.movies.observe(viewLifecycleOwner) {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(it.data)
                }
            }
            1 -> {
                viewModel.moviesFav.observe(viewLifecycleOwner) {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(it.data)
                }
            }
        }
    }

    override fun onClickedMovie(movieId: Int) {
        findNavController().navigate(
            R.id.action_moviesFragment_to_movieDetailFragment,
            bundleOf("id" to movieId)
        )
    }
}