package space.ardyc.taskandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import space.ardyc.taskandroidapp.api.account.AccountManager
import java.io.File

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val loginCredentials = AccountManager().loadFromFile(File(filesDir, "login.json"))
        if(loginCredentials == null) {
            Handler(this.mainLooper).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                overridePendingTransition(R.anim.splash_invisible, R.anim.splash_invisible)
                finish()
            }, 5000)
        }else {
            Handler(this.mainLooper).postDelayed({
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra("loginCred", loginCredentials)
                })
                overridePendingTransition(R.anim.splash_invisible, R.anim.splash_invisible)
                finish()
            }, 5000)
        }
    }
}