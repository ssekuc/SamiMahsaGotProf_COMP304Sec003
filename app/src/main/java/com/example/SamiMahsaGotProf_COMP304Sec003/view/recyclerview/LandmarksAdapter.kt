package com.example.SamiMahsaGotProf_COMP304Sec003.view.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.SamiMahsaGotProf_COMP304Sec003.databinding.ItemLandmarkBinding
import com.example.SamiMahsaGotProf_COMP304Sec003.model.Landmark

class LandmarksAdapter(
    private val onItemClicked: (landmark: Landmark) -> Unit
) : RecyclerView.Adapter<LandmarksAdapter.LandmarksViewHolder>() {
    private val landmarks = mutableListOf<Landmark>()

    // Clears the view and add the list everytime
    fun setLandmarks(types: List<Landmark>) {
        this.landmarks.clear()
        this.landmarks.addAll(types)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarksViewHolder {
        val viewHolder = LandmarksViewHolder(
            ItemLandmarkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(landmarks[position])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = landmarks.size


    override fun onBindViewHolder(holder: LandmarksViewHolder, position: Int) {
        holder.bind(landmarks[position])
    }

    class LandmarksViewHolder(
        private var binding: ItemLandmarkBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(landmark: Landmark) {
            binding.landmarkTitleTextview.text = landmark.place_name
            binding.landmarkAddressTextview.text = landmark.place_address
        }
    }
}
