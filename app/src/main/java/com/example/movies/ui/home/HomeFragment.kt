package com.example.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.models.movie.Result
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.adapter.MoviesAdapter
import com.example.movies.ui.adapter.OnClickMovie
import com.example.movies.utils.*
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnClickMovie {

    private val moviesViwModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val moviesAdapter by lazy { MoviesAdapter(this) }

    private lateinit var selectedGenreId: String
    private var selectedGenreName = ""

    /** init pagination variables */
    private var isLoading = true
    private var pageNumber = 1
    private var itemCount = 20
    private var previousTotal = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItem = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        /** init binding view */
        binding = FragmentHomeBinding.bind(
            inflater.inflate(R.layout.fragment_home, container, false)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initMoviesRecycler()

        getAllGenres()

        getMoviesByGenre()

        searchListener()
    }

    private fun initMoviesRecycler() = binding.recyclerMovies.apply {
        adapter = moviesAdapter
        layoutManager = GridLayoutManager(context, 2)

        /** to implement recyclerView pagination */
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
                        getAllMovies()
                        setAllMovies(MoviesListState.PAGINATION)
                        isLoading = true
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun getAllGenres() {
        moviesViwModel.getGenres()

        moviesViwModel.getAllGenres.observeOnce(requireActivity()) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { allGenres ->
                        /** set first element in genres as "All Movies" to chip group */
                        addGenresToChipGroup(getString(R.string.all))

                        /** set all other genres from api to chip group */
                        allGenres.genres.forEach { genre ->
                            addGenresToChipGroup(genre.name, genre.id.toString())
                        }

                        /** set check on first genre "All" */
                        if (selectedGenreName.isEmpty())
                            binding.genreChipGroup.check(binding.genreChipGroup.getChildAt(0).id)
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
    }

    private fun addGenresToChipGroup(genreName: String, genreId: String = "") {
        val chip = Chip(requireActivity())
        chip.text = genreName
        chip.tag = genreId

        chip.initGenreChip(selectedGenreName, requireActivity())

        binding.genreChipGroup.addView(chip)
    }

    private fun getMoviesByGenre() =
        binding.genreChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val selectedGenre = group.findViewById<Chip>(group.checkedChipId)
            selectedGenreName = selectedGenre.text.toString()
            selectedGenreId = selectedGenre.tag.toString()
            setScrollValuesToDefault()
            getAllMovies()
            setAllMovies(MoviesListState.GENRE)
        }

    private fun getAllMovies() {
        moviesViwModel.getAllMovies(pageNumber.toString(), selectedGenreId)
    }

    private fun setAllMovies(movieListState: MoviesListState) =
        moviesViwModel.getAllMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        binding.progressLoading.visibility = View.GONE
                        moviesAdapter.setMoviesList(movieListState, it.results)
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
                if (!query.isNullOrEmpty()) navigateToSearchFragment(query)
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

    private fun navigateToSearchFragment(query: String?) {
        val searchQuery = Bundle()
        searchQuery.putString(Constants.QUERY, query)
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment, searchQuery)
        emptySearchView()
    }

    private fun emptySearchView() {
        binding.searchView.setQuery("", true)
        binding.searchView.onActionViewCollapsed()
    }

    override fun onClickToMovie(movieDetails: Result) {
        val selectedMovie = Bundle()
        selectedMovie.putSerializable(Constants.SELECTED_MOVIE, movieDetails)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, selectedMovie)
    }

}