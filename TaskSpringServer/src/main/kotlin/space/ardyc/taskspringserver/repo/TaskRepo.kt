package space.ardyc.taskspringserver.repo

import org.springframework.data.repository.CrudRepository
import space.ardyc.taskspringserver.entity.TaskEntity

interface TaskRepo: CrudRepository<TaskEntity,Long> {

}