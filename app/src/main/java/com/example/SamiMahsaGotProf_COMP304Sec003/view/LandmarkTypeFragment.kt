package com.example.SamiMahsaGotProf_COMP304Sec003.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.SamiMahsaGotProf_COMP304Sec003.R
import com.example.SamiMahsaGotProf_COMP304Sec003.databinding.FragmentLandmarkTypesBinding
import com.example.SamiMahsaGotProf_COMP304Sec003.model.database.AppDatabase
import com.example.SamiMahsaGotProf_COMP304Sec003.view.recyclerview.LandmarkTypesAdapter
import com.example.SamiMahsaGotProf_COMP304Sec003.viewmodel.MainViewModel
import com.example.SamiMahsaGotProf_COMP304Sec003.viewmodel.MainViewModelProvider

class LandmarkTypeFragment : Fragment() {

    // View stuff
    private var _binding: FragmentLandmarkTypesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: LandmarkTypesAdapter

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelProvider(AppDatabase.getDatabase(requireContext()).landmarkDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandmarkTypesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = LandmarkTypesAdapter {
            viewModel.setSelectedType(it)
            view?.findNavController()
                ?.navigate(R.id.action_landmarkTypeFragment_to_landmarksFragment)
        }
        adapter.setLandmarkTypes(viewModel.getLandmarkTypes())
        binding.typesRecyclerview.adapter = this.adapter
    }
}