package com.lfg.masterdetail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lfg.masterdetail.R
import com.lfg.masterdetail.entities.Product

class ProductAdapter (
    private var productList : MutableList<Product>,
    val context: Context,
    val onItemClick : (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    class ProductHolder (v : View) : RecyclerView.ViewHolder(v) {
        private var view : View = v

        fun setDescription (description : String){
            val txtDescription : TextView = view.findViewById(R.id.txtNameItem)
            txtDescription.text = description
        }
        fun getCardView () : CardView {
            return view.findViewById(R.id.card_package_item)
        }
        fun getImageView () : ImageView {
            return view.findViewById(R.id.imgItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return (ProductHolder(view))
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