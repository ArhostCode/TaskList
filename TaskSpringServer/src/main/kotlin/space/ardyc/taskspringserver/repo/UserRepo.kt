package space.ardyc.taskspringserver.repo

import org.springframework.data.repository.CrudRepository
import space.ardyc.taskspringserver.entity.UserEntity

interface UserRepo : CrudRepository<UserEntity, Long> {

    fun findByLoginAndPassword(login: String, password: String): UserEntity?
    fun findByLogin(login: String): UserEntity?
    fun findByToken(token: String): UserEntity?

}