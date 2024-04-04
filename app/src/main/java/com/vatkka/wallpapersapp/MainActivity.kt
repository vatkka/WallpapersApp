package com.vatkka.wallpapersapp

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vatkka.wallpapersapp.Fragments.HomeFragment
import com.vatkka.wallpapersapp.databinding.ActivityMainBinding
import com.yodo1.mas.Yodo1Mas
import com.yodo1.mas.banner.Yodo1MasBannerAdView
import com.yodo1.mas.error.Yodo1MasError

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding;

    var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)


        replaceFragment(HomeFragment())


        Yodo1Mas.getInstance().init(this@MainActivity, "M9I0AstxjF", object : Yodo1Mas.InitListener {
            override fun onMasInitSuccessful() {


                var bannerAdView : Yodo1MasBannerAdView = findViewById(R.id.yodo1_mas_banner)
                bannerAdView.loadAd()
            }

            override fun onMasInitFailed(error: Yodo1MasError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show() }
        })
        var bannerAdView : Yodo1MasBannerAdView = findViewById(R.id.yodo1_mas_banner)
        bannerAdView.loadAd()


    }

    private fun replaceFragment(fragment:Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentReplace, fragment)
        transaction.commit()
    }


    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }


}

