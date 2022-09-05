package space.ardyc.taskandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import space.ardyc.taskandroidapp.api.account.AccountManager
import java.io.File

class LoginActivity : AppCompatActivity() {

    lateinit var loginField: EditText
    lateinit var passwordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginField = findViewById(R.id.loginField)
        passwordField = findViewById(R.id.passwordField)

    }

    fun login(view: View) {
        Thread {
            try {
                val loginCred = AccountManager(MainActivity.apiUrl).loginAccountIgnoreFile(
                    loginField.text.toString(),
                    passwordField.text.toString(),
                    File(filesDir, "login.json")
                )
                runOnUiThread {
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        putExtra("loginCred", loginCred)
                    })
                    overridePendingTransition(R.anim.splash_invisible, R.anim.splash_invisible)
                    finish()
                }

            } catch (e: Exception) {
                Snackbar.make(view, "Ошибка авторизации", Snackbar.LENGTH_LONG).show()
            }
        }.start()
    }

    fun signin(view: View) {
        Thread {
            try {
                val loginCred = AccountManager(MainActivity.apiUrl).createAccount(
                    loginField.text.toString(),
                    passwordField.text.toString(),
                    File(filesDir, "login.json")
                )
                runOnUiThread {
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        putExtra("loginCred", loginCred)
                    })
                    overridePendingTransition(R.anim.splash_invisible, R.anim.splash_invisible)
                    finish()
                }

            } catch (e: Exception) {
                Snackbar.make(view, "Ошибка авторизации", Snackbar.LENGTH_LONG).show()
            }
        }.start()
    }

}