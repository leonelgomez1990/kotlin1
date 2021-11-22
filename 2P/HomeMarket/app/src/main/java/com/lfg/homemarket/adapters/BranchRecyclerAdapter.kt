package com.lfg.homemarket.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lfg.homemarket.R
import com.lfg.homemarket.clases.PriceBranch
import com.lfg.homemarket.databinding.ItemMarketBinding
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class BranchRecyclerAdapter (
    private var itemList : MutableList<PriceBranch>,
    val onItemEventClick : (EventEnum, Int) -> Boolean
) : RecyclerView.Adapter<BranchRecyclerAdapter.ItemViewHolder>() {

    companion object {
        enum class EventEnum {
            CLICK, LONGCLICK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ItemViewHolder (private val binding: ItemMarketBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PriceBranch){
            val strPrice = "$ ${DecimalFormat("0.00").format(item.price)}"
            val strDistance = "${DecimalFormat("0.0").format(item.distance)} km"
            binding.txtBranchDescription.text = item.description
            binding.txtBranchAddress.text = item.address
            binding.txtBranchType.text = item.type
            binding.txtBranchDistance.text = strDistance
            binding.txtBranchPrice.text = strPrice

            binding.imgBranchItem.loadUrl(item.urlImage)

        }

        fun getCardView () : CardView = binding.cardMarketItem

        private fun ImageView.loadUrl(url: String) {
            Picasso
                .get()
                .load(url)
                .placeholder(R.mipmap.ic_no_picture)
                .into(this)
        }

    }

}