package com.vatkka.wallpapersapp

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.inject.Deferred
import com.vatkka.wallpapersapp.databinding.ActivityFinalWallpaperBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.ObjectInput
import java.io.OutputStream
import java.net.URL
import java.util.Objects
import kotlin.random.Random as Random
import com.yodo1.mas.Yodo1Mas;
import com.yodo1.mas.banner.Yodo1MasBannerAdListener
import com.yodo1.mas.banner.Yodo1MasBannerAdSize
import com.yodo1.mas.banner.Yodo1MasBannerAdView;
import com.yodo1.mas.error.Yodo1MasError
import com.yodo1.mas.interstitial.Yodo1MasInterstitialAd
import com.yodo1.mas.interstitial.Yodo1MasInterstitialAdListener

class FinalWallpaper : AppCompatActivity() {

    lateinit var bannerAdView : Yodo1MasBannerAdView
    lateinit var binding: ActivityFinalWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Yodo1Mas.getInstance().init(this, "M9I0AstxjF", object : Yodo1Mas.InitListener {
            override fun onMasInitSuccessful() {
                Toast.makeText(this@FinalWallpaper,
                    "[Yodo1 Mas] Successful initialization", Toast.LENGTH_SHORT).show()
            }
            override fun onMasInitFailed(error: Yodo1MasError) {
                Toast.makeText(this@FinalWallpaper, error.message, Toast.LENGTH_SHORT).show() } })



        binding = ActivityFinalWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)







        Yodo1MasInterstitialAd.getInstance().loadAd(this@FinalWallpaper)
        val isLoaded = Yodo1MasInterstitialAd.getInstance().isLoaded
        if(isLoaded) Yodo1MasInterstitialAd.getInstance().showAd(this@FinalWallpaper)

// TODO: Add bannerAdView to your view hierarchy.


        bannerAdView = findViewById(R.id.yodo1_mas_banner1)
        bannerAdView.setAdListener(object : Yodo1MasBannerAdListener {
            override fun onBannerAdLoaded(bannerAdView: Yodo1MasBannerAdView?) {
            }

            override fun onBannerAdFailedToLoad(
                bannerAdView: Yodo1MasBannerAdView?,
                error: Yodo1MasError
            ) {
// Code to be executed when an ad request fails.
            }

            override fun onBannerAdOpened(bannerAdView: Yodo1MasBannerAdView?) {
// Code to be executed when an ad opens an overlay that covers the screen.
            }

            override fun onBannerAdFailedToOpen(
                bannerAdView: Yodo1MasBannerAdView?,
                error: Yodo1MasError
            ) {
// Code to be executed when an ad open fails.
            }

            override fun onBannerAdClosed(bannerAdView: Yodo1MasBannerAdView?) { } })

        bannerAdView.loadAd()

        val url = intent.getStringExtra("link")


        val urlImage =  URL(url)

        Glide.with(this).load(url).into(binding.finalWallpaper)


        var myTrue: Boolean = true

        val button: RelativeLayout = findViewById(R.id.bar_bottom)
        binding.finalWallpaper.setOnClickListener {
            myTrue = if (myTrue){
                button.visibility = View.INVISIBLE
                false
            } else {
                button.visibility = View.VISIBLE
                true
            }

        }



        binding.btnSetWallpaper.setOnClickListener {

            val result: kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async { urlImage.toBitmap() }

            GlobalScope.launch(Dispatchers.Main) {

                try {
                    val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                    wallpaperManager.setBitmap(result.await(), null, true, WallpaperManager.FLAG_SYSTEM)
                    Toast.makeText(this@FinalWallpaper, "Wallpaper set", Toast.LENGTH_SHORT).show()
                }catch (e: Exception)
                {
                    Toast.makeText(this@FinalWallpaper, "Wallpaper NOT set", Toast.LENGTH_SHORT).show()

                }



            }

        }



    }






    fun URL.toBitmap(): Bitmap? {
        return  try {
            BitmapFactory.decodeStream(openStream())
        }catch (e: IOException) {
            null
        }
    }

    fun expand(v: View){
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        val targetHeight: Int = v.measuredHeight

        v.layoutParams.height = 1
        v.visibility = View.VISIBLE

        val a: Animation = object : Animation(){
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    LinearLayout.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()

            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

    fun collapse(v: View) {
        val initialHeight : Int = v.measuredHeight
        val a : Animation = object : Animation(){
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f){
                    v.visibility = View.GONE
                }else{
                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }
    private fun saveImage(image: Bitmap?) {
        val random1 = Random.nextInt(5209)
        val random2 = Random.nextInt(9526)

        val name = "WALLPAPER-${random1 + random2}"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "HD Wallpaper"
            )
        }

        val resolver = contentResolver
        var outputStream: OutputStream? = null

        try {
            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            outputStream = resolver.openOutputStream(imageUri!!)
            image?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Image Not Saved", Toast.LENGTH_SHORT).show()
        } finally {
            outputStream?.close()
        }
    }

}