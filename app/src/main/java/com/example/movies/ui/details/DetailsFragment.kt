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
import com.example.movies.ui.MainActivity

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var selectedMovie:Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        selectedMovie = requireArguments().getSerializable("selectedMovie") as Result

        binding = FragmentDetailsBinding.bind(
            LayoutInflater.from(requireActivity())
                .inflate(R.layout.fragment_details, container, false) )

        (requireActivity() as MainActivity).supportActionBar?.title = selectedMovie.title

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initImageSlider()
        binding.txtTitle.text = selectedMovie.title
        binding.txtDescription.text = selectedMovie.overview
        binding.txtDate.text = selectedMovie.releaseDate
        binding.rating.rating = (selectedMovie.voteAverage / 2).toFloat()

    }

    private fun initImageSlider() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(
            "https://image.tmdb.org/t/p/w500${selectedMovie.posterPath}", ScaleTypes.CENTER_INSIDE))
        imageList.add(SlideModel(
            "https://image.tmdb.org/t/p/w500${selectedMovie.backdropPath}", ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList)
    }

}