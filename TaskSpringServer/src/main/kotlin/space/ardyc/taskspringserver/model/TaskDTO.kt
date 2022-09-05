package space.ardyc.taskspringserver.model

import space.ardyc.taskspringserver.entity.TaskEntity

class TaskDTO(var id: Long,var name: String, var description: String, var completed: Boolean) {

    companion object {
        fun fromTaskEntity(taskEntity: TaskEntity): TaskDTO {
            return TaskDTO(taskEntity.id,taskEntity.name, taskEntity.text, taskEntity.completed)
        }
    }

}