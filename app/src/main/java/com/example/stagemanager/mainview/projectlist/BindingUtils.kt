package com.example.stagemanager.mainview.projectlist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.stagemanager.database.FormationEntity
import com.example.stagemanager.database.ProjectEntity

@BindingAdapter("projectNameFormatted")
fun TextView.setProjectName(item: ProjectEntity?){
    item?.let{
        text = item.name
    }
}

@BindingAdapter("projectDeadlineFormatted")
fun TextView.setProjectDeadline(item: ProjectEntity?){
    item?.let{
        text = item.deadline
    }
}

@BindingAdapter("projectDescriptionFormatted")
fun TextView.setProjectDescription(item: ProjectEntity?){
    item?.let{
        text = item.description
    }
}

@BindingAdapter("formationNameFormatted")
fun TextView.setFormationName(item: FormationEntity?){
    item?.let{
        if (item.name != "") {
            text = item.name
        }
    }
}

@BindingAdapter("formationActorsFormatted")
fun TextView.setFormationActors(item: FormationEntity?){
    item?.let{
        text = item.formationId.toString()
    }
}