package space.ardyc.taskspringserver.service

import org.springframework.stereotype.Service
import space.ardyc.taskspringserver.entity.UserEntity
import space.ardyc.taskspringserver.exceptions.UserExists
import space.ardyc.taskspringserver.exceptions.UserNotFound
import space.ardyc.taskspringserver.model.UserDTO
import space.ardyc.taskspringserver.repo.UserRepo
import space.ardyc.taskspringserver.utils.EncodeUtils

@Service
class UserService(private var userRepo: UserRepo) {

    fun login(login: String, password: String): UserDTO {
        val user = userRepo.findByLoginAndPassword(login, EncodeUtils.md5(password)) ?: throw UserNotFound()
        return UserDTO.fromUserEntity(user)
    }
    
    fun signin(login: String, password: String) {
        if(userRepo.findByLogin(login) != null)
            throw UserExists()
        val userEntity = UserEntity().apply {
            this.login = login
            this.password = EncodeUtils.md5(password)
            this.token = EncodeUtils.md5(login + password)
        }
        userRepo.save(userEntity)
    }

}