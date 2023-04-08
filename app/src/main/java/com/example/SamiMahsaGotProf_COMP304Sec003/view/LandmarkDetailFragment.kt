package com.example.SamiMahsaGotProf_COMP304Sec003.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.SamiMahsaGotProf_COMP304Sec003.R
import com.example.SamiMahsaGotProf_COMP304Sec003.databinding.FragmentLandmarkDetailBinding
import com.example.SamiMahsaGotProf_COMP304Sec003.model.database.AppDatabase
import com.example.SamiMahsaGotProf_COMP304Sec003.viewmodel.MainViewModel
import com.example.SamiMahsaGotProf_COMP304Sec003.viewmodel.MainViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LandmarkDetailFragment : Fragment() {


    private var _binding: FragmentLandmarkDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelProvider(AppDatabase.getDatabase(requireContext()).landmarkDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandmarkDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        setupMaps()
        setupViews()
    }

    private fun setupMaps() {
        val normalMapFragment = SupportMapFragment.newInstance(normalMapOptions())
        val satelliteMapFragment = SupportMapFragment.newInstance(satelliteMapOptions())

        normalMapFragment.getMapAsync { mapView ->
            // Marks the location on map
            mapView.addMarker(
                getMarkerOption(
                    viewModel.selectedLandmark!!.place_latitude,
                    viewModel.selectedLandmark!!.place_longitude
                )
            )

            // Zooms to the location
            mapView.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        viewModel.selectedLandmark!!.place_latitude,
                        viewModel.selectedLandmark!!.place_longitude
                    ),
                    16f
                )
            )
        }

        satelliteMapFragment.getMapAsync { mapView ->
            // Marks the location on map
            mapView.addMarker(
                getMarkerOption(
                    viewModel.selectedLandmark!!.place_latitude,
                    viewModel.selectedLandmark!!.place_longitude
                )
            )

            // Zooms to the location
            mapView.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        viewModel.selectedLandmark!!.place_latitude,
                        viewModel.selectedLandmark!!.place_longitude
                    ),
                    16f
                )
            )

            showLoading(false)
        }

        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.normal_map_container, normalMapFragment)
            .commitAllowingStateLoss()

        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.satellite_map_container, satelliteMapFragment)
            .commitAllowingStateLoss()
    }

    private fun setupViews() {
        viewModel.selectedLandmark?.let { landmark ->
            binding.landmarkTitleTextview.text = landmark.place_name
            binding.landmarkAddressTextview.text = landmark.place_address
        }
    }

    private fun normalMapOptions(): GoogleMapOptions {
        return GoogleMapOptions().apply {
            mapType(GoogleMap.MAP_TYPE_NORMAL)
            compassEnabled(false)
            // Disable interact options
            rotateGesturesEnabled(false)
            tiltGesturesEnabled(false)
            scrollGesturesEnabled(false)
            zoomGesturesEnabled(false)
        }
    }

    private fun satelliteMapOptions(): GoogleMapOptions {
        return GoogleMapOptions().apply {
            mapType(GoogleMap.MAP_TYPE_SATELLITE)
            compassEnabled(false)
            // Disable interact options
            rotateGesturesEnabled(false)
            tiltGesturesEnabled(false)
            scrollGesturesEnabled(false)
            zoomGesturesEnabled(false)
        }
    }

    private fun getMarkerOption(latitude: Double, longitude: Double): MarkerOptions {
        return MarkerOptions().apply {
            position(LatLng(latitude, longitude))
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressbar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.contentFrameLayout.visibility = if (loading) View.GONE else View.VISIBLE
    }
}