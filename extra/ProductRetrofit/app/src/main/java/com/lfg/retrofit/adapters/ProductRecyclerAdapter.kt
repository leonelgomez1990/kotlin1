package com.lfg.retrofit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lfg.retrofit.clases.Product
import com.lfg.retrofit.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ProductRecyclerAdapter (
    private var itemList : MutableList<Product>,
    private val context: Context,
    val onItemClick : (Int) -> Unit
) : RecyclerView.Adapter<ProductRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater)
        //val binding = layoutInflater.inflate(R.layout.item_product, parent, false)
        return (ItemViewHolder(binding))
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        /* Uso de métodos definidos en el View Holder */

        holder.bind(item)
        holder.getCardView().setOnClickListener {
            onItemClick(position)
        }

        /*
        Glide
            .with(context)
            .load(item.urlImage)
            .centerInside()
            .into(holder.getImageView())
        */
    }

    class ItemViewHolder (private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.cardPackageItem) {

        fun bind(item: Product){
            binding.txtNameItem.text = item.description

            binding.imgItem.loadUrl(item.urlImage)

        }
        private fun ImageView.loadUrl(url: String) {
            Picasso.get().load(url).into(this)
        }

        fun getCardView () : CardView {
            return binding.cardPackageItem
        }
        fun getImageView () : ImageView {
            return binding.imgItem
        }

    }
}