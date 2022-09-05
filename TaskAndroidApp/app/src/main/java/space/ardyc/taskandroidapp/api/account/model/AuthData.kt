package space.ardyc.taskandroidapp.api.account.model

import java.io.Serializable

data class AuthData(var login: String, var token: AuthToken) : Serializable