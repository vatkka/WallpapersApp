package com.vatkka.wallpapersapp.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vatkka.wallpapersapp.FinalWallpaper
import com.vatkka.wallpapersapp.Model.colortoneModel
import com.vatkka.wallpapersapp.R

class TctAdapter(val requireContext: Context, private val listTheColorTone: ArrayList<colortoneModel>) :
    RecyclerView.Adapter<TctAdapter.tctViewHolder>() {

    inner class tctViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val cardBack = itemView.findViewById<CardView>(R.id.item_Card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tctViewHolder {
        return  tctViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_colortone, parent, false)
        )
    }

    override fun onBindViewHolder(holder: tctViewHolder, position: Int) {

        val color = listTheColorTone[position].color
        holder.cardBack.setCardBackgroundColor(Color.parseColor(color!!))
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, FinalWallpaper::class.java)
            intent.putExtra("link",listTheColorTone[position].link)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount() = listTheColorTone.size

}