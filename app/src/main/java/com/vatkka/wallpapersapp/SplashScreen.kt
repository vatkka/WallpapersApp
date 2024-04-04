package com.vatkka.wallpapersapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.yodo1.mas.Yodo1Mas
import com.yodo1.mas.helper.model.Yodo1MasAdBuildConfig

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen)

        val config = Yodo1MasAdBuildConfig.Builder().enableUserPrivacyDialog(true).build()
        Yodo1Mas.getInstance().setAdBuildConfig(config)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },2500)

    }
}