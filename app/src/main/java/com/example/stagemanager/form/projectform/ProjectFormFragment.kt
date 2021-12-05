package com.example.stagemanager.form.projectform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.stagemanager.R
import com.example.stagemanager.databinding.FragmentProjectFormBinding
import com.example.stagemanager.databinding.FragmentProjectListBinding

class ProjectFormFragment : Fragment() {

    private lateinit var viewModel: ProjectFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProjectFormBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_form, container, false
        )

        val application = requireNotNull(this.activity).application

        return binding.root
    }


}