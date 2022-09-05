package space.ardyc.taskandroidapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import space.ardyc.taskandroidapp.api.account.model.AuthData
import space.ardyc.taskandroidapp.api.task.TaskManager
import space.ardyc.taskandroidapp.api.task.model.Task
import space.ardyc.taskandroidapp.databinding.ActivityMainBinding
import space.ardyc.taskandroidapp.views.TaskView
import java.io.File
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginCred: AuthData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, CreateTaskActivity::class.java).apply {
                putExtra("loginCred", loginCred)
            })
            overridePendingTransition(R.anim.splash_invisible, R.anim.splash_invisible)
            finish()
        }
        loginCred = intent.getSerializableExtra("loginCred") as AuthData
        val tasksFromFile =
            TaskManager(loginCred.token, apiUrl).loadFromFile(
                File(filesDir, "tasks.json")
            )
        loadTasks(tasksFromFile)
        updateTasks()

    }

    private fun loadTasks(tasks: ArrayList<Task>) {
        val tasksView = findViewById<LinearLayout>(R.id.tasks)
        tasksView.removeAllViews()
        tasks.forEach { task_n ->
            tasksView.addView(
                TaskView(baseContext, task_n).apply {
                    setOnLongClickListener { view ->
                        deleteTask(task.id)
                        true
                    }
                    setOnClickListener {
                        completeTask(task.id)
                        task.completed = !task.completed
                        updateBackground()
                    }
                }
            )
        }
    }

    fun logout(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.splash_invisible, R.anim.splash_invisible)
        finish()
    }

    fun update(view: View) {
        updateTasks()
    }

    private fun updateTasks() {
        Thread {
            try {
                val tasks = TaskManager(
                    loginCred.token,
                    apiUrl
                ).loadFromNetworkAndSave(
                    File(filesDir, "tasks.json")
                )
                runOnUiThread { loadTasks(tasks) }
            } catch (e: Exception) {
                Snackbar.make(
                    findViewById(R.id.layoutContent),
                    "Ошибка загрузки данных",
                    Snackbar.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }.start()
    }

    private fun completeTask(id: Long) {
        Thread {
            try {
                TaskManager(
                    loginCred.token,
                    apiUrl
                ).completeTask(
                    id
                )
            } catch (e: Exception) {
                Snackbar.make(
                    findViewById(R.id.layoutContent),
                    "Ошибка обработки данных",
                    Snackbar.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }.start()
    }

    private fun deleteTask(id: Long) {
        Thread {
            try {
                TaskManager(
                    loginCred.token,
                    apiUrl
                ).deleteTask(
                    id
                )
                val tasks = TaskManager(
                    loginCred.token,
                    apiUrl
                ).loadFromNetworkAndSave(
                    File(filesDir, "tasks.json")
                )
                runOnUiThread { loadTasks(tasks) }
            } catch (e: Exception) {
                Snackbar.make(
                    findViewById(R.id.layoutContent),
                    "Ошибка обработки данных",
                    Snackbar.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }.start()
    }

    companion object {
        var apiUrl = "http://192.168.0.143:8080"
    }

}