package com.vatkka.wallpapersapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.vatkka.wallpapersapp.Adapter.BomAdapter
import com.vatkka.wallpapersapp.Adapter.CatAdapter
import com.vatkka.wallpapersapp.Model.BomModel
import com.vatkka.wallpapersapp.Model.catModel
import com.vatkka.wallpapersapp.R
import com.vatkka.wallpapersapp.databinding.FragmentHomeBinding
import com.yodo1.mas.Yodo1Mas;
import com.yodo1.mas.appopenad.Yodo1MasAppOpenAd
import com.yodo1.mas.banner.Yodo1MasBannerAdView;
import com.yodo1.mas.error.Yodo1MasError



class HomeFragment : Fragment() {


     lateinit var binding: FragmentHomeBinding
     lateinit var db:FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container, false)

        db = FirebaseFirestore.getInstance()






        db.collection("bestofmonth").addSnapshotListener { value, error ->
            val listBestOfTheMonth = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listBestOfTheMonth.addAll(data!!)



            binding.rcvBom.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcvBom.adapter = BomAdapter(requireContext(), listBestOfTheMonth)

        }



        db.collection("categories").addSnapshotListener { value, error ->

            val listOfCategory= arrayListOf<catModel>()
            val data = value?.toObjects(catModel::class.java)
            listOfCategory.addAll(data!!)


            binding.rcvCat.layoutManager = GridLayoutManager(requireContext(),2)
            binding.rcvCat.adapter = CatAdapter(requireContext(), listOfCategory)
        }

        return binding.root
    }


}