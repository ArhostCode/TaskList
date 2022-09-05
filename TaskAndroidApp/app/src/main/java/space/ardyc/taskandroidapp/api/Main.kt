package space.ardyc.taskandroidapp.api

import space.ardyc.taskandroidapp.MainActivity
import space.ardyc.taskandroidapp.api.account.AccountManager
import space.ardyc.taskandroidapp.api.task.TaskManager
import java.io.File

fun main() {
    val authData = AccountManager(MainActivity.apiUrl).loginAccountIgnoreFile("ardyc", "123", File("test.cred"))

    TaskManager(authData.token, MainActivity.apiUrl).createTask("Test2", "Testik")
    TaskManager(authData.token, MainActivity.apiUrl).loadFromNetwork().forEach { println(it) }

}