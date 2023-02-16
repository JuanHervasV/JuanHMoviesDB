package com.data.recipesyape.feature.mapScreen.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.data.recipesyape.R
import com.data.recipesyape.common.constant.SPACE
import com.data.recipesyape.common.constant.ZOOM_LVL
import com.data.recipesyape.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private val args: MapsFragmentArgs by navArgs()

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney =
            LatLng(args.modelRecipe.latitude.toDouble(), args.modelRecipe.longitude.toDouble())
        googleMap.addMarker(
            MarkerOptions().position(sydney)
                .title(getString(R.string.marker_in) + SPACE + args.modelRecipe.name)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, ZOOM_LVL))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized.not()) {
            binding = FragmentMapsBinding.inflate(inflater)
            binding.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}