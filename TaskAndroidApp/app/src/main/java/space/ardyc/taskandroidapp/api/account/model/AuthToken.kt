package space.ardyc.taskandroidapp.api.account.model

import java.io.Serializable

data class AuthToken(private var token: String) : Serializable {
    fun getString() = token
}