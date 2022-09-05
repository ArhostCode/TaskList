package space.ardyc.taskandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import space.ardyc.taskandroidapp.api.account.model.AuthData
import space.ardyc.taskandroidapp.api.task.TaskManager
import java.io.File
import java.lang.Exception

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var loginCred: AuthData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginCred = intent.getSerializableExtra("loginCred") as AuthData
        setContentView(R.layout.activity_create_task)
    }

    fun createTask(view: View) {
        deleteTask(
            findViewById<EditText>(R.id.taskName).text.toString(),
            findViewById<EditText>(R.id.taskDesc).text.toString()
        )
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra("loginCred", loginCred)
        })
        overridePendingTransition(R.anim.splash_invisible, R.anim.splash_invisible)
        finish()
    }

    private fun deleteTask(name: String, desc: String) {
        val thread = Thread {
            try {
                TaskManager(
                    loginCred.token,
                    MainActivity.apiUrl
                ).createTask(
                    name, desc
                )
            } catch (e: Exception) {
                Snackbar.make(
                    findViewById(R.id.layoutContent),
                    "Ошибка обработки данных",
                    Snackbar.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
        thread.start()
        while (thread.isAlive){
            Thread.sleep(100)
        }
    }
}