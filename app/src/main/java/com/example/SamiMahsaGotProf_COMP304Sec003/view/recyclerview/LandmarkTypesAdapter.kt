package com.example.SamiMahsaGotProf_COMP304Sec003.view.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.SamiMahsaGotProf_COMP304Sec003.databinding.ItemLandmarkTypeBinding

class LandmarkTypesAdapter(
    private val onItemClicked: (type: String) -> Unit
) : RecyclerView.Adapter<LandmarkTypesAdapter.LandmarkTypesViewHolder>() {
    private val types = mutableListOf<String>()

    fun setLandmarkTypes(types: List<String>) {
        this.types.clear()
        this.types.addAll(types)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkTypesViewHolder {
        val viewHolder = LandmarkTypesViewHolder(
            ItemLandmarkTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(types[position])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = types.size


    override fun onBindViewHolder(holder: LandmarkTypesViewHolder, position: Int) {
        holder.bind(types[position])
    }

    class LandmarkTypesViewHolder(
        private var binding: ItemLandmarkTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(type: String) {
            binding.typeTextview.text = type
        }
    }
}
