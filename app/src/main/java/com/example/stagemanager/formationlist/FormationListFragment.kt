package com.example.stagemanager.formationlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.stagemanager.R
import com.example.stagemanager.database.ProjectDatabase
import com.example.stagemanager.databinding.FragmentFormationListBinding
import com.example.stagemanager.databinding.FragmentProjectDetailBinding
import com.example.stagemanager.projectdetail.ProjectInfoTabsFragmentArgs


class FormationListFragment(val projectKey: Long) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
//
//    var projectKey: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFormationListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_formation_list, container, false)

        val application = requireNotNull(this.activity).application

        val datasource = ProjectDatabase.getInstance(application).projectDatabaseDao

        val viewModelFactory = FormationListViewModelFactory(datasource, application, projectKey!!)

        val formationListViewModel =
            ViewModelProvider(this, viewModelFactory).get(FormationListViewModel::class.java)

        binding.formationListViewModel = formationListViewModel

        val adapter = FormationEntityAdapter(FormationEntityListener {
            formationId -> Toast.makeText(context, "Clicked formation: $formationId", Toast.LENGTH_SHORT).show()
        })

        binding.formationList.adapter = adapter

        formationListViewModel.formations.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }
}