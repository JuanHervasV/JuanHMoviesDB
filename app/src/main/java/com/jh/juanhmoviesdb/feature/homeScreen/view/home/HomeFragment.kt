package com.jh.juanhmoviesdb.feature.homeScreen.view.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.denzcoskun.imageslider.models.SlideModel
import com.jh.juanhmoviesdb.databinding.FragmentHomeBinding
import com.jh.juanhmoviesdb.feature.homeScreen.domain.model.adapter.PopularMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.GridLayoutManager
import com.jh.data.feature.popularMovies.remote.response.Results
import com.jh.database.model.MovieResults
import com.jh.juanhmoviesdb.common.constant.BASE_IMAGE_URL_BIG
import com.jh.juanhmoviesdb.common.network.ConnectionUtil
import android.widget.Button
import com.jh.juanhmoviesdb.R

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val imageList = ArrayList<SlideModel>()
    private val movieResults = mutableListOf<MovieResults>()
    private val listResults = mutableListOf<Results>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.moviesRecyclerView.adapter = PopularMoviesAdapter()
        binding.moviesRecyclerView.itemAnimator = null
        binding.btnsearch.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchMediaFragment()
            )
        }
    }

    private fun initObservers() {
        if (ConnectionUtil().isConnected(context)) {
            viewModel.callPopularMovies()
            viewModel.popularMovies.observe(viewLifecycleOwner, { it ->
                (binding.moviesRecyclerView.adapter as PopularMoviesAdapter).setPopularMoviesData(it.toMutableList())
                it.forEach {
                    movieResults += MovieResults(
                        it.id,
                        it.title,
                        it.overview,
                        it.image,
                        it.posterimage,
                        it.voteAverage
                    )
                    imageList.add(
                        SlideModel(
                            "" + BASE_IMAGE_URL_BIG + it.posterimage,
                            "" + it.title
                        )
                    )
                }
                viewModel.addMoviesToDataBase(movieResults)
                binding.imageSlider.setImageList(imageList)
            })
        } else {
            viewModel.moviesDataBaseRoom.observe(viewLifecycleOwner, { it ->
                if (it.isNullOrEmpty()) {
                    showInternetDialog()
                } else {
                    it.forEach {
                        listResults += Results(
                            it.id,
                            it.title,
                            it.overview,
                            it.image,
                            it.posterImage,
                            it.voteAverage
                        )
                        imageList.add(
                            SlideModel(
                                "" + BASE_IMAGE_URL_BIG + it.posterImage,
                                "" + it.title
                            )
                        )
                    }
                    (binding.moviesRecyclerView.adapter as PopularMoviesAdapter).setPopularMoviesData(
                        listResults
                    )
                    binding.imageSlider.setImageList(imageList)
                }
            })
        }
    }

    private fun showInternetDialog() {
        val dialog: AlertDialog = AlertDialog.Builder(context)
            .setTitle(R.string.no_internet)
            .setNeutralButton(R.string.try_again, null)
            .create()

        dialog.setOnShowListener {
            val button: Button =
                (dialog).getButton(AlertDialog.BUTTON_NEUTRAL)
            button.setOnClickListener {
                if (ConnectionUtil().isConnected(context)) {
                    dialog.dismiss()
                    binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 2)
                    initListeners()
                    initObservers()
                } else {
                }
            }
        }
        dialog.show()
    }
}