package com.lfg.retrofit.clases

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lfg.retrofit.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)

    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivDog)
    }
}