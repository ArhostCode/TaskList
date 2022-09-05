package space.ardyc.taskspringserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskSpringServerApplication

fun main(args: Array<String>) {
    runApplication<TaskSpringServerApplication>(*args)
}
