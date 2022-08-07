package com.example.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.models.movie.Result
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.home.adapter.MoviesAdapter
import com.example.movies.ui.home.adapter.OnClickMovie
import com.example.movies.utils.MoviesListState
import com.example.movies.utils.observeOnce
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnClickMovie {

    private val moviesViwModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val moviesAdapter by lazy { MoviesAdapter(this) }

    private var genreId = ""

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
    }

    private fun initMoviesRecycler() = binding.recyclerMovies.apply {
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
                        getAllMovies()
                        setAllMovies(MoviesListState.PAGINATION)
                        isLoading = true
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun getAllMovies() {
        moviesViwModel.getAllMovies(pageNumber.toString(), genreId)
    }

    private fun setAllMovies(movieListState: MoviesListState) =
        moviesViwModel.getAllMovies.observeOnce(viewLifecycleOwner) {
            it?.let {
                moviesAdapter.setMoviesList(movieListState, it.results)
            }
    }

    private fun getAllGenres() {
        moviesViwModel.getGenres()
        moviesViwModel.getAllGenres.observeOnce(viewLifecycleOwner) { it ->

            /** set first element in genres as "All Movies" */
            addGenresToChipGroup(getString(R.string.all))

            /** set all other genres from api */
            it.genres.forEach {
                addGenresToChipGroup(it.name, it.id.toString())
            }

            /** set check on first genre "All" */
            binding.genreChipGroup.check(binding.genreChipGroup.getChildAt(0).id)
        }
    }

    private fun addGenresToChipGroup(genreName: String, genreId: String = "") {
        val chip = Chip(requireActivity())
        chip.text = genreName
        chip.tag = genreId

        val chipDrawable = ChipDrawable.createFromAttributes(
            requireActivity(),
            null,
            0,
            R.style.CustomChipStyle
        )

        chip.setChipDrawable(chipDrawable)

        binding.genreChipGroup.addView(chip)
    }

    private fun getMoviesByGenre() {
        binding.genreChipGroup.setOnCheckedStateChangeListener { group, _ ->
            genreId = group.findViewById<Chip>(group.checkedChipId).tag.toString()
            setScrollValuesToDefault()
            getAllMovies()
            setAllMovies(MoviesListState.GENRE)
        }
    }

    private fun setScrollValuesToDefault() {
        previousTotal = 0
        visibleItemCount = 0
        totalItemCount = 0
        pastVisibleItem = 0
        pageNumber = 1
        isLoading = true
    }

    override fun onClick(movieDetails: Result) {
        val bundle = Bundle()
        bundle.putSerializable("selectedMovie",movieDetails)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        getMoviesByGenre()
    }

}