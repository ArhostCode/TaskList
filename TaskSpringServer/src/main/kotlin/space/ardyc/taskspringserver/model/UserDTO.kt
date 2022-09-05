package space.ardyc.taskspringserver.model

import space.ardyc.taskspringserver.entity.UserEntity

class UserDTO(var name: String, var token: String) {

    companion object {
        fun fromUserEntity(userEntity: UserEntity): UserDTO {
            return UserDTO(userEntity.login, userEntity.token)
        }
    }

}