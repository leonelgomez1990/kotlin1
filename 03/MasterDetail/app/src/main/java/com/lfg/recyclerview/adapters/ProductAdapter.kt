package com.lfg.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lfg.recyclerview.R
import com.lfg.recyclerview.entities.Product

class ProductAdapter (
    private var productList : MutableList<Product>,
    val onItemClick : (Int) -> Boolean
) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    class ProductHolder (v : View) : RecyclerView.ViewHolder(v) {
        private var view : View
        init {
            this.view = v
        }
        fun setDescription (description : String){
            var txtDescription : TextView = view.findViewById(R.id.txtNameItem)
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
        holder.getCardView().setOnClickListener() {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}