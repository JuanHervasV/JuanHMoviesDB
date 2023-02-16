package com.data.recipesyape.feature.recipesDetail.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.data.recipesyape.R
import com.data.recipesyape.common.constant.BASE_IMAGE_URL
import com.data.recipesyape.common.constant.SPACE
import com.data.recipesyape.common.network.ConnectionUtil
import com.data.recipesyape.common.utils.showInternetDialog
import com.data.recipesyape.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()
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
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners() {
        with(binding) {
            title.text = args.modelRecipe.name
            txtIngridientsBody.text = args.modelRecipe.ingredients
            txtPreparationBody.text = args.modelRecipe.preparation
            btnGoToMap.text = getString(R.string.locate) + SPACE + args.modelRecipe.origin
            btnGoToMap.setOnClickListener {
                if (ConnectionUtil().isConnected(context)) {
                    findNavController().navigate(
                        DetailsFragmentDirections.goToMapsFragment(
                            modelRecipe = args.modelRecipe
                        )
                    )
                } else {
                    showInternetDialog(context, true, findNavController())
                }

            }
        }
        Glide.with(this)
            .load(BASE_IMAGE_URL + args.modelRecipe.images)
            .into(binding.imgRecipe)
    }
}