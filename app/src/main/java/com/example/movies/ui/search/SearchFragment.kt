package com.example.movies.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.models.movie.Result
import com.example.movies.databinding.FragmentSearchBinding
import com.example.movies.ui.adapter.MoviesAdapter
import com.example.movies.ui.adapter.OnClickMovie
import com.example.movies.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), OnClickMovie {

    private val searchViwModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchQuery: String

    private val moviesAdapter by lazy { MoviesAdapter(this) }

    /** init pagination variables */
    private var isLoading = true
    private var pageNumber = 1
    private var itemCount = 20
    private var previousTotal = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItem = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchQuery = requireArguments().getString(Constants.QUERY).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.bind(
            LayoutInflater.from(requireActivity())
                .inflate(R.layout.fragment_search, container, false)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleSearchView()

        initRecyclerView()

        getMoviesByQuery(searchQuery)

        setMoviesToAdapter(MoviesListState.PAGINATION)

        searchListener()
    }

    private fun handleSearchView() {
        binding.searchView.onActionViewExpanded()
        binding.searchView.setQuery(searchQuery, true)
        binding.searchView.setOnSearchClickListener { binding.searchView.onActionViewExpanded() }
    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        adapter = moviesAdapter
        layoutManager = GridLayoutManager(context, 2)

        /** to implement pagination to recyclerView */
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                visibleItemCount = layoutManager?.childCount ?: 0
                totalItemCount = (layoutManager as GridLayoutManager).itemCount
                pastVisibleItem =
                    (layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()

                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false
                            previousTotal = totalItemCount
                        }
                    }

                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItem + itemCount)) {
                        pageNumber++
                        getMoviesByQuery(searchQuery)
                        setMoviesToAdapter(MoviesListState.PAGINATION)
                        isLoading = true
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun getMoviesByQuery(query: String) {
        searchViwModel.getMoviesByQuery(query, pageNumber.toString())
    }

    private fun setMoviesToAdapter(moviesListState: MoviesListState) =
        searchViwModel.getSearchResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.let {
                        binding.progressLoading.visibility = View.GONE
                        moviesAdapter.setMoviesList(moviesListState, response.data!!.results)
                    }
                }

                is NetworkResult.Error -> {
                    binding.progressLoading.visibility = View.GONE
                    showErrorSnackBar(response.message.toString())
                }

                is NetworkResult.Loading -> {
                    binding.progressLoading.visibility = View.VISIBLE
                }
            }

        }

    private fun showErrorSnackBar(message: String) {
        this.showSnackBar(message)
    }

    private fun setScrollValuesToDefault() {
        previousTotal = 0
        visibleItemCount = 0
        totalItemCount = 0
        pastVisibleItem = 0
        pageNumber = 1
        isLoading = true
    }

    private fun searchListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchQuery = query
                    setScrollValuesToDefault()
                    getMoviesByQuery(searchQuery)
                    setMoviesToAdapter(MoviesListState.SEARCH)
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        /** to open searchView when click to any position on view */
        binding.searchView.setOnClickListener {
            binding.searchView.requestFocus()
            binding.searchView.onActionViewExpanded()
        }
    }

    override fun onClickToMovie(movieDetails: Result) {
        val selectedMovie = Bundle()
        selectedMovie.putSerializable(Constants.SELECTED_MOVIE, movieDetails)
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, selectedMovie)
    }

}