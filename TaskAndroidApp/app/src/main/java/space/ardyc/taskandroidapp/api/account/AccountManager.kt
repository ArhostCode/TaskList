package space.ardyc.taskandroidapp.api.account

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import space.ardyc.taskandroidapp.api.account.model.AuthData
import space.ardyc.taskandroidapp.api.account.model.AuthToken
import space.ardyc.taskandroidapp.util.RequestUtils
import java.io.File
import java.net.URL

class AccountManager(private var url: String = "") {

    fun loginAccount(login: String, password: String, baseFile: File): AuthData {
        val fromFile = loadFromFile(baseFile)
        if (fromFile != null)
            return fromFile
        return loginAccountIgnoreFile(login, password, baseFile)
    }

    fun loginAccountIgnoreFile(login: String, password: String, baseFile: File): AuthData {
        val data = loadFromNetwork(login, password)
        saveToFile(data, baseFile)
        return data
    }

    fun createAccount(login: String, password: String, baseFile: File): AuthData {
        createAccountWithNetwork(login, password)
        val data = loadFromNetwork(login, password)
        saveToFile(data, baseFile)
        return data
    }

    fun loadFromFile(baseFile: File): AuthData? {
        try {
            if (baseFile.exists())
                return Gson().fromJson(baseFile.readText(), AuthData::class.java)
        } catch (_: Exception) {
        }
        return null
    }

    private fun loadFromNetwork(login: String, password: String): AuthData {
        try {
            val okHttpClient = RequestUtils.getBaseClient()
            val request = RequestUtils.getBaseRequestUtilsBuilder()
                .url(URL("$url/auth/login?name=$login&password=$password"))
                .get()
                .build()
            val response = okHttpClient.newCall(request).execute()
            if (response.code != 200) {
                throw AuthException()
            }
            val json = JsonParser.parseString(response.body.string()) as JsonObject
            return AuthData(json["name"].asString, AuthToken(json["token"].asString))
        } catch (e: Exception) {
            e.printStackTrace()
            throw AuthException()
        }
    }

    private fun createAccountWithNetwork(login: String, password: String) {
        try {
            val okHttpClient = RequestUtils.getBaseClient()
            val request = RequestUtils.getBaseRequestUtilsBuilder()
                .url(URL("$url/auth/signin?name=$login&password=$password"))
                .post("".toRequestBody())
                .build()
            val response = okHttpClient.newCall(request).execute()
            if (response.code != 200) {
                throw AuthException()
            }
        } catch (e: Exception) {
            throw AuthException()
        }
    }

    private fun saveToFile(data: AuthData, baseFile: File) {
        if (!baseFile.exists())
            baseFile.createNewFile()
        baseFile.writeText(Gson().toJson(data))
    }


}