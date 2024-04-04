package com.vatkka.wallpapersapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.vatkka.wallpapersapp.FinalWallpaper
import com.vatkka.wallpapersapp.Model.BomModel
import com.vatkka.wallpapersapp.R

class CatImagesAdapter(val requireContext: Context, private val listOfCatWallpaper: ArrayList<BomModel>) :
    RecyclerView.Adapter<CatImagesAdapter.bomViewHolder>() {

    inner class bomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val imageview = itemView.findViewById<RoundedImageView>(R.id.catImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return  bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_wallpaper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        Glide.with(requireContext).load(listOfCatWallpaper[position].link).into(holder.imageview)
        holder.itemView.setOnClickListener {

            val intent = Intent(requireContext, FinalWallpaper::class.java)
            intent.putExtra("link",listOfCatWallpaper[position].link)

            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount() = listOfCatWallpaper.size

}