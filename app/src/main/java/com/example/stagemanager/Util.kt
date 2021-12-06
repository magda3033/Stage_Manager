package com.example.stagehelper

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.stagemanager.R
import com.example.stagemanager.database.ProjectEntity


fun formatProjects(nights: List<ProjectEntity>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.project_id))
            append("\t${it.projectId.toString()}<br>")
            append("<b>Name:</b>")
            append("\t${it.name}<br>")
            append("<b>Music piece:</b>")
            append("\t${it.songUsed}<br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}