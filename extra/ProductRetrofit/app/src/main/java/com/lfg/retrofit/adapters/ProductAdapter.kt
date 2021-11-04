package com.lfg.retrofit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lfg.retrofit.R
import com.lfg.retrofit.clases.ProductViewHolder

class ProductAdapter(private val images: List<String>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int = images.size


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }
}