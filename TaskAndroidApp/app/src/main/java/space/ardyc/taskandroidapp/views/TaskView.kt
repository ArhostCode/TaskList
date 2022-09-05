package space.ardyc.taskandroidapp.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import space.ardyc.taskandroidapp.R
import space.ardyc.taskandroidapp.api.task.model.Task

class TaskView(context: Context?, var task: Task) : LinearLayout(context) {

    init {
        initLayoutParams()
        updateBackground()

        addView(ImageView(context).apply {
            layoutParams = LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT, 0F)
            setImageResource(R.drawable.ic_launcher_foreground)
        })
        addView(LinearLayout(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0F
            )
            orientation = VERTICAL

            addView(TextView(context).apply {
                layoutParams = LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT, 0F)
                setPadding(35, 15, 0, 0)
                textSize = 14F
                text = task.name
                setTextColor(ContextCompat.getColor(context!!, R.color.white))
            })
            addView(TextView(context).apply {
                layoutParams = LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT, 0F)
                setPadding(35, 5, 0, 0)
                textSize = 14F
                text = task.description
                setTextColor(ContextCompat.getColor(context!!, R.color.gray))
            })

            setPadding(0, 0, 0, 20)

        })
    }

    private fun initLayoutParams() {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        (layoutParams as LayoutParams).topMargin = 30
        (layoutParams as LayoutParams).marginStart = 20
        (layoutParams as LayoutParams).marginEnd = 20
    }

    fun updateBackground() {
        if (task.completed)
            setBackgroundResource(R.drawable.task_completed)
        else
            setBackgroundResource(R.drawable.task)
    }
}