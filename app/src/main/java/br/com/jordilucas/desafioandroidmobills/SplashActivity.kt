package br.com.jordilucas.desafioandroidmobills

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : BaseActivity() {
    override fun init() {
        val it = Intent(this, BottomNavActivity::class.java)
        startActivity(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}