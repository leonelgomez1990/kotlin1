package com.lfg.miacell.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lfg.miacell.databinding.ItemProductBinding
import com.lfg.miacell.entities.Product

class ProductAdapter (
    private var productList : MutableList<Product>,
    val context: Context,
    val onItemClick : (Int) -> Unit
    ) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

        class ProductHolder (binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.cardPackageItem) {
            val binding : ItemProductBinding = binding

            fun setDescription (description : String){
                binding.txtNameItem.text = description
            }
            fun getCardView () : CardView {
                return binding.cardPackageItem
            }
            fun getImageView () : ImageView {
                return binding.imgItem
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
            val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context))
            return (ProductHolder(binding))
        }

        override fun onBindViewHolder(holder: ProductHolder, position: Int) {
            holder.setDescription(productList[position].description)
            holder.getCardView().setOnClickListener {
                onItemClick(position)
            }

            Glide
                .with(context)
                .load(productList[position].urlImage)
                .centerInside()
                .into(holder.getImageView())

        }

        override fun getItemCount(): Int {
            return productList.size
        }
}