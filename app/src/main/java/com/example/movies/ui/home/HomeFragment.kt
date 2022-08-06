package com.example.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.home.adapter.MoviesAdapter
import com.example.movies.utils.observeOnce
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val moviesViwModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val moviesAdapter by lazy { MoviesAdapter() }
    private var genreId = ""

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

        // init binding
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getMoviesByGenre()

        initMoviesRecycler()

        getAllMovies()

        setAllMovies()

        getAllGenres()
    }

    private fun initMoviesRecycler() = binding.recyclerMovies.apply {
        adapter = moviesAdapter
        layoutManager = GridLayoutManager(context, 2)

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
                        setAllMovies()
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

    private fun getMoviesByGenreSelected() {
        moviesViwModel.getAllMovies(pageNumber.toString(), genreId)
    }

    private fun setAllMovies() = moviesViwModel.getAllMovies.observeOnce(requireActivity()) {
        it?.let {
            moviesAdapter.setMoviesByPagination(it.results)
        }
    }

    private fun setMoviesByGenre() = moviesViwModel.getAllMovies.observeOnce(requireActivity()) {
        it?.let {
            moviesAdapter.setMoviesByGenre(it.results)
        }
    }

    private fun getAllGenres() {
        moviesViwModel.getGenre()
        moviesViwModel.getAllGenres.observeOnce(requireActivity()) { it ->

            // set first element in genres as "All Movies"
            addGenresToChipGroup("All")

            // set all other genres from api
            it.genres.forEach {
                addGenresToChipGroup(it.name, it.id.toString())
            }

            // set check on first genre "All"
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
        binding.genreChipGroup.setOnCheckedChangeListener { group, checkedId ->
            genreId = group.findViewById<Chip>(checkedId).tag.toString()
            setScrollValuesToDefault()
            getMoviesByGenreSelected()
            setMoviesByGenre()
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

}