package com.example.movies.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.movies.R
import com.example.movies.data.models.movie.Result
import com.example.movies.databinding.FragmentDetailsBinding
import com.example.movies.ui.main.MainActivity
import com.example.movies.utils.Constants

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var selectedMovie:Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        selectedMovie = requireArguments().getSerializable(Constants.SELECTED_MOVIE) as Result

        binding = FragmentDetailsBinding.bind(
            LayoutInflater.from(requireActivity())
                .inflate(R.layout.fragment_details, container, false) )

        (requireActivity() as MainActivity).supportActionBar?.title = selectedMovie.title

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initImageSlider()

        initMovieDetails()

    }

    private fun initMovieDetails() {
        binding.txtTitle.text = selectedMovie.title
        binding.txtDescription.text = selectedMovie.overview
        binding.txtDate.text = selectedMovie.releaseDate
        binding.rating.rating = (selectedMovie.voteAverage / 2).toFloat()
    }

    private fun initImageSlider() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(
            "${Constants.IMAGE_URL}${selectedMovie.posterPath}", ScaleTypes.CENTER_INSIDE))
        imageList.add(SlideModel(
            "${Constants.IMAGE_URL}${selectedMovie.backdropPath}", ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList)
    }

}