package space.ardyc.taskspringserver.service

import org.springframework.stereotype.Service
import space.ardyc.taskspringserver.entity.TaskEntity
import space.ardyc.taskspringserver.exceptions.TaskNotFound
import space.ardyc.taskspringserver.exceptions.Unauthorized
import space.ardyc.taskspringserver.model.TaskDTO
import space.ardyc.taskspringserver.repo.TaskRepo
import space.ardyc.taskspringserver.repo.UserRepo
import java.util.logging.Level
import java.util.logging.Logger

@Service
class TaskService(var userRepo: UserRepo, var taskRepo: TaskRepo) {

    fun getTasks(token: String): List<TaskDTO> {
        val user = userRepo.findByToken(token) ?: throw Unauthorized()
        return user.tasks.map { TaskDTO.fromTaskEntity(it) }
    }

    fun createTask(name: String, text: String, token: String) {
        val user = userRepo.findByToken(token) ?: throw Unauthorized()
        taskRepo.save(TaskEntity().apply {
            this.name = name
            this.text = text
            this.user = user
        })
    }

    fun completeTask(id: Long, token: String) {
        val task = taskRepo.findById(id)
        if (task.isPresent) {
            if (task.get().user.token == token) {
                task.get().completed = !task.get().completed
            } else {
                throw Unauthorized()
            }
            taskRepo.save(task.get())
            return
        }
        throw TaskNotFound()
    }

    fun deleteTask(id: Long, token: String) {
        val task = taskRepo.findById(id)
        if (task.isPresent) {
            if (task.get().user.token == token) {
                taskRepo.deleteById(id)
                return
            } else {
                throw Unauthorized()
            }
        }
        throw TaskNotFound()
    }
}