package com.vatkka.wallpapersapp

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.vatkka.wallpapersapp.Adapter.CatImagesAdapter
import com.vatkka.wallpapersapp.Model.BomModel
import com.vatkka.wallpapersapp.databinding.ActivityCatBinding
import com.yodo1.mas.Yodo1Mas
import com.yodo1.mas.appopenad.Yodo1MasAppOpenAd
import com.yodo1.mas.appopenad.Yodo1MasAppOpenAdListener
import com.yodo1.mas.error.Yodo1MasError
import com.yodo1.mas.interstitial.Yodo1MasInterstitialAd
import com.yodo1.mas.interstitial.Yodo1MasInterstitialAdListener


class CatActivity : AppCompatActivity() {

    lateinit var binding: ActivityCatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCatBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)


        Yodo1Mas.getInstance().init(this, "M9I0AstxjF", object : Yodo1Mas.InitListener {
            override fun onMasInitSuccessful() {
                Toast.makeText(this@CatActivity,
                    "[Yodo1 Mas] Successful initialization", Toast.LENGTH_SHORT).show()
            }
            override fun onMasInitFailed(error: Yodo1MasError) {
                Toast.makeText(this@CatActivity, error.message, Toast.LENGTH_SHORT).show() } })

        val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid")//key abstract
        val view = findViewById<TextView>(R.id.cat_name)

        db.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener { value, error ->


            view.text = uid.toString()
            val listOfCatWallpaper= arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listOfCatWallpaper.addAll(data!!)
            binding.catRcv.layoutManager  = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

            binding.catRcv.adapter = CatImagesAdapter(this,listOfCatWallpaper)

        }



    }
}