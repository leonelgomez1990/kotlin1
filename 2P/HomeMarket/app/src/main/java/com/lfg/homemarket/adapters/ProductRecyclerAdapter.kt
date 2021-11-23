package com.lfg.homemarket.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lfg.homemarket.R
import com.lfg.homemarket.clases.Product
import com.lfg.homemarket.databinding.ItemProductBinding
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class ProductRecyclerAdapter (
    private var itemList : MutableList<Product>,
    val onItemEventClick : (EventEnum, Int) -> Boolean
) : RecyclerView.Adapter<ProductRecyclerAdapter.ItemViewHolder>() {

    companion object {
        enum class EventEnum {
            CLICK, LONGCLICK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        /* Uso de métodos definidos en el View Holder */
        holder.bind(currentItem)
        holder.getCardView().setOnClickListener {
            onItemEventClick(EventEnum.CLICK, position)
        }
        holder.getCardView().setOnLongClickListener {
            onItemEventClick(EventEnum.LONGCLICK, position)
        }
    }

    inner class ItemViewHolder (private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product){
            val strPrice = "$ ${DecimalFormat("0.00").format(item.price)}"
            binding.txtNameItem.text = item.description
            binding.imgItem.loadUrl(item.urlImage)
            if(item.price > 0)
                binding.txtPriceItem.text = strPrice
            else
                binding.txtPriceItem.text = ""
        }

        fun getCardView () : CardView = binding.cardPackageItem

        private fun ImageView.loadUrl(url: String) {
            Picasso
                .get()
                .load(url)
                .placeholder(R.mipmap.ic_no_picture)
                .error(R.mipmap.ic_no_picture)
                .into(this)
        }
    }

}