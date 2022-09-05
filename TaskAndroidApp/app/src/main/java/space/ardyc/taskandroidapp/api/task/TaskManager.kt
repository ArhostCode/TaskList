package space.ardyc.taskandroidapp.api.task

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody.Companion.toRequestBody
import space.ardyc.taskandroidapp.api.account.model.AuthToken
import space.ardyc.taskandroidapp.api.task.exception.TaskCompleteError
import space.ardyc.taskandroidapp.api.task.exception.TaskCreateError
import space.ardyc.taskandroidapp.api.task.exception.TaskDeleteError
import space.ardyc.taskandroidapp.api.task.exception.TaskNotLoaded
import space.ardyc.taskandroidapp.api.task.model.Task
import space.ardyc.taskandroidapp.util.RequestUtils
import java.io.File
import java.net.URL


class TaskManager(private var authToken: AuthToken, private var url: String) {

    fun loadFromNetworkAndSave(baseFile: File): ArrayList<Task> {
        val tasks = loadFromNetwork()
        saveToFile(tasks, baseFile)
        return tasks
    }

    fun loadFromNetwork(): ArrayList<Task> {
        val okHttpClient = RequestUtils.getBaseClient()
        val request = RequestUtils.getBaseRequestUtilsBuilder()
            .url(URL("$url/tasks/get?token=${authToken.getString()}"))
            .get()
            .build()
        val response = okHttpClient.newCall(request).execute()
        if (response.code != 200) {
            throw TaskNotLoaded()
        }
        return Gson().fromJson(
            response.body.string(),
            object : TypeToken<ArrayList<Task>>() {}.type
        )
    }

    fun createTask(name: String, desc: String) {
        val okHttpClient = RequestUtils.getBaseClient()
        val request = RequestUtils.getBaseRequestUtilsBuilder()
            .url(URL("$url/tasks/create?name=$name&text=$desc&token=${authToken.getString()}"))
            .post("".toRequestBody())
            .build()
        var req = okHttpClient.newCall(request).execute()
        if((req.code) != 200) {
            println(req.body.string())
            throw TaskCreateError()
        }
    }

    fun loadFromFile(baseFile: File): ArrayList<Task> {
        try {
            if (baseFile.exists())
                return Gson().fromJson(
                    baseFile.readText(),
                    object : TypeToken<List<Task>>() {}.type
                )
        } catch (_: Exception) {
        }
        return ArrayList()
    }

    fun completeTask(id: Long) {
        val okHttpClient = RequestUtils.getBaseClient()
        val request = RequestUtils.getBaseRequestUtilsBuilder()
            .url(URL("$url/tasks/complete?id=$id&token=${authToken.getString()}"))
            .post("".toRequestBody())
            .build()
        if(okHttpClient.newCall(request).execute().code != 200)
            throw TaskCompleteError()
    }

    fun deleteTask(id: Long) {
        val okHttpClient = RequestUtils.getBaseClient()
        val request = RequestUtils.getBaseRequestUtilsBuilder()
            .url(URL("$url/tasks/delete?id=$id&token=${authToken.getString()}"))
            .post("".toRequestBody())
            .build()
        if(okHttpClient.newCall(request).execute().code != 200)
            throw TaskDeleteError()
    }

    private fun saveToFile(tasks: ArrayList<Task>, baseFile: File) {
        if (!baseFile.exists())
            baseFile.createNewFile()
        baseFile.writeText(Gson().toJson(tasks))
    }


}