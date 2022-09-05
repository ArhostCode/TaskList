package space.ardyc.taskspringserver.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import space.ardyc.taskspringserver.service.TaskService

@RestController
@RequestMapping("/tasks")
class TaskController {

    @Autowired
    lateinit var taskService: TaskService

    @GetMapping("/get")
    fun getTasks(@RequestParam token: String): Any {
        return try {
            ResponseEntity.ok(taskService.getTasks(token))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/create")
    fun createTask(@RequestParam name: String, @RequestParam text: String, @RequestParam token: String): Any {
        return try {
            taskService.createTask(name, text, token)
            ResponseEntity.ok("Task has been created")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/complete")
    fun completeTask(@RequestParam id: Long, @RequestParam token: String): Any {
        return try {
            taskService.completeTask(id, token)
            ResponseEntity.ok("Task has been completed")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/delete")
    fun deleteTask(@RequestParam id: Long, @RequestParam token: String): Any {
        return try {
            taskService.deleteTask(id, token)
            ResponseEntity.ok("Task has been deleted")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

}