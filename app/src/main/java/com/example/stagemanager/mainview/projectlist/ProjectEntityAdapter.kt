package com.example.stagemanager.mainview.projectlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stagemanager.database.ProjectEntity
import com.example.stagemanager.databinding.ProjectEntityViewBinding
import com.example.stagemanager.generated.callback.OnClickListener

class ProjectEntityAdapter(val clickListener: ProjectEntityListener): ListAdapter<ProjectEntity, ProjectEntityAdapter.ViewHolder>(ProjectEntityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: ProjectEntityViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProjectEntity, clickListener: ProjectEntityListener) {
            binding.project = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProjectEntityViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ProjectEntityDiffCallback : DiffUtil.ItemCallback<ProjectEntity>() {
        override fun areItemsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
            return oldItem.projectId == newItem.projectId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
            return oldItem == newItem
        }
    }
}

class ProjectEntityListener(val clickListener: (projectId: Long) -> Unit){
    fun onClick(project: ProjectEntity) = clickListener(project.projectId)
}