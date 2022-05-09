package com.jh.juanhmoviesdb.feature.moviesDetail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jh.juanhmoviesdb.common.constant.BASE_IMAGE_URL_BIG
import com.jh.juanhmoviesdb.common.constant.VIDEO_BASE_URL
import com.jh.juanhmoviesdb.databinding.FragmentDetailsBinding
import com.jh.juanhmoviesdb.feature.moviesDetail.domain.model.YoutubeVideosModel
import com.jh.juanhmoviesdb.feature.moviesDetail.domain.model.adapter.VideoAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()
    private var youtubeVideos = Vector<YoutubeVideosModel>()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.title.text = args.movieTitle
        binding.description.text = args.movieDescription
        Glide.with(this)
            .load(BASE_IMAGE_URL_BIG + args.movieImage)
            .into(binding.imgmovie)
        binding.recyclervideos.layoutManager = LinearLayoutManager(context)
        binding.recyclervideos.adapter = VideoAdapter(youtubeVideos)
        binding.recyclervideos.itemAnimator = null
    }

    private fun initObservers() {
        viewModel.getVideo(args.idMovies)
        viewModel.getVideos.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                for (i in 1..it.size) {
                    youtubeVideos.add(
                        YoutubeVideosModel(
                            "<iframe width=\"100%\" height=\"100%\" src=\"" + VIDEO_BASE_URL +
                                    "" + it.toMutableList()[i - 1].key + "\" frameborder=\"0\" allowfullscreen></iframe>"
                        )
                    )
                }
            } else {
                binding.txtnovid.visibility = View.VISIBLE
            }
        })
    }
}