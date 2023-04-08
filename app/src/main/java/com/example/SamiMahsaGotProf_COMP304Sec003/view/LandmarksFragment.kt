package com.example.SamiMahsaGotProf_COMP304Sec003.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.SamiMahsaGotProf_COMP304Sec003.R
import com.example.SamiMahsaGotProf_COMP304Sec003.databinding.FragmentLandmarksBinding
import com.example.SamiMahsaGotProf_COMP304Sec003.model.database.AppDatabase
import com.example.SamiMahsaGotProf_COMP304Sec003.view.recyclerview.LandmarksAdapter
import com.example.SamiMahsaGotProf_COMP304Sec003.viewmodel.MainViewModel
import com.example.SamiMahsaGotProf_COMP304Sec003.viewmodel.MainViewModelProvider
import kotlinx.coroutines.launch

class LandmarksFragment : Fragment() {


    private var _binding: FragmentLandmarksBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: LandmarksAdapter

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelProvider(AppDatabase.getDatabase(requireContext()).landmarkDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = LandmarksAdapter {
            viewModel.selectedLandmark = it
            view?.findNavController()
                ?.navigate(R.id.action_landmarksFragment_to_landmarkDetailFragment)
        }
        binding.landmarksRecyclerview.adapter = this.adapter
        lifecycleScope.launch {
            viewModel.getLandmarksByType(viewModel.selectedType!!).collect {
                adapter.setLandmarks(it)
            }
        }
    }
}