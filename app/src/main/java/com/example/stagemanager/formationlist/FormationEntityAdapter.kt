package com.example.stagemanager.formationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemanager.database.FormationEntity
import com.example.stagemanager.databinding.FormationEntityViewBinding

class FormationEntityAdapter(private val clickListener: FormationEntityListener): ListAdapter<FormationEntity, FormationEntityAdapter.ViewHolder>(
    FormationEntityDiffCallback()
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(getItem(position)!!, clickListener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: FormationEntityViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FormationEntity, clickListener: FormationEntityListener) {
            binding.formation = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FormationEntityViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class FormationEntityDiffCallback : DiffUtil.ItemCallback<FormationEntity>() {
    override fun areItemsTheSame(oldItem: FormationEntity, newItem: FormationEntity): Boolean {
        return oldItem.formationId == newItem.formationId
    }

    override fun areContentsTheSame(oldItem: FormationEntity, newItem: FormationEntity): Boolean {
        return oldItem == newItem
    }
}

class FormationEntityListener(val clickListener: (formationId: Long) -> Unit){
    fun OnClick(formation: FormationEntity) = clickListener(formation.formationId)
}