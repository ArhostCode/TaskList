package space.ardyc.taskandroidapp.api.task.model

import java.io.Serializable

data class Task(var id: Long, var name: String, var description: String, var completed: Boolean): Serializable {
    override fun toString(): String {
        return "Task(id=$id, name='$name', text='$description', completed=$completed)"
    }
}