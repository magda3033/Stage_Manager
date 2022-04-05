package com.example.stagemanager.form.formationform

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.DragEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stagemanager.R
import com.example.stagemanager.database.PositionEntity
import com.example.stagemanager.database.ProjectDatabase
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.databinding.FragmentFormationFormBinding
import com.example.stagemanager.databinding.FragmentProjectDetailBinding
import com.example.stagemanager.form.projectform.ProjectFormFragmentArgs
import com.example.stagemanager.form.projectform.ProjectFormFragmentDirections
import com.example.stagemanager.form.projectform.ProjectFormViewModel
import com.example.stagemanager.form.projectform.ProjectFormViewModelFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor


class FormationFormFragment : Fragment() {

    lateinit var binding: FragmentFormationFormBinding
    lateinit var dataSource :ProjectDatabaseDao

    var formationId = 0L

    var placed = false


//    lateinit var positions : List<PositionEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_formation_form, container, false)

        binding.editorLayout.setOnDragListener(dragListener)
        binding.actorPool.setOnDragListener(dragListener2)

        val application = requireNotNull(this.activity).application

        val arguments: FormationFormFragmentArgs by navArgs()
        formationId = arguments.formationId

        dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao

        val viewModelFactory = FormationFormViewModelFactory(dataSource, application, arguments.formationId)

        val formationFormViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(FormationFormViewModel::class.java)


        binding.formationFormViewModel = formationFormViewModel

        formationFormViewModel.navigateToFormationList.observe(this, Observer {
            if (it==true) {
                this.findNavController().navigate(
                    FormationFormFragmentDirections.actionFormationFormFragmentToProjectInfoTabsFragment(arguments.projectId))
                formationFormViewModel.doneNavigating()
            }
        })

        formationFormViewModel.getData.observe(this, Observer {
          if (it == true) {
              getActorsPositions()
          }
        })

        createNewActor()

        placeActors()

        return binding.root
    }

    private fun placeActors() {
        val viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        var act : List<PositionEntity>? =null
        uiScope.launch {
            withContext(Dispatchers.IO){
                act = dataSource.getFormationPositionsList(formationId)
            }
            act?.forEach{
                val actor = createNewActor()
                actor.setXY(it.x, it.y)
            }
        }
    }

    private fun getActorsPositions() {
        val positions = binding.formationFormViewModel!!.positions
        binding.editorLayout.children.forEach { child ->
//            Log.i("FormationFormFragment", "child: ${child.marginStart}/${child.marginTop}")

            positions.add(PositionEntity(
                formationId = formationId,
                x = child.marginStart,
                y = child.marginTop))
        }
    }


    private fun createNewActor(): View {
        val actor = View(context)

        val params = LayoutParams(100, 100)

        actor.layoutParams = params

        actor.setBackgroundColor(Color.YELLOW)

        binding.actorPool.addView(actor)

        actor.setOnLongClickListener{
            getNewActor(it)
        }

        return actor
    }

    private fun getNewActor(it: View): Boolean {
        val clipText = ""
        val item = ClipData.Item(clipText)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(clipText, mimeTypes, item)

        val dragShadowBuilder = View.DragShadowBuilder(it)
        it.startDragAndDrop(data, dragShadowBuilder, it, 0)

        it.visibility = View.INVISIBLE

        return true
    }



    private val dragListener = View.OnDragListener { view, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
//                Log.i("FormationFormFragment", "Drag dropped")
                val item = event.clipData.getItemAt(0)
                val dragData = item.text
//                Toast.makeText(context, dragData, Toast.LENGTH_LONG).show()

                view.invalidate()

                val x = event.x
                val y = event.y

                val v = event.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)
                val destination = view as RelativeLayout

                val params = (v.layoutParams as ViewGroup.MarginLayoutParams)

                val new_x = (x - v.width / 2).toInt()
                val new_y = (y - v.height / 2).toInt()

                params.updateMarginsRelative(start = new_x, top = new_y)

                destination.addView(v)
                v.visibility = View.VISIBLE
                placed = true
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
//                Log.i("FormationFormFragment", "Drag ended")
                if (!placed) {
//                    Log.i("FormationFormFragment", "Placed incorrectly")

                    val pool = binding.actorPool
                    val v = event.localState as View
                    v.visibility = View.VISIBLE

                } else {
//                    Log.i("FormationFormFragment", "Placed correctly")
                    if (binding.actorPool.childCount == 0){
                        createNewActor()
                    }
                    placed = false
                }
                view.invalidate()
                true
            }
            else -> false


        }
    }

    private val dragListener2 = View.OnDragListener { view, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                val v = event.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
//                Log.i("FormationFormFragment", "Placed in bin")
                if (binding.actorPool.childCount == 0){
                    createNewActor()
                }
                view.invalidate()
                true
            }
            else -> {

                false
            }

        }
    }

    private fun View.setXY(x: Int, y: Int) {
        val owner = this.parent as ViewGroup
        owner.removeView(this)
        val destination = binding.editorLayout

        val params = (this.layoutParams as ViewGroup.MarginLayoutParams)

        params.updateMarginsRelative(start = x, top = y)

        destination.addView(this)
    }
}


