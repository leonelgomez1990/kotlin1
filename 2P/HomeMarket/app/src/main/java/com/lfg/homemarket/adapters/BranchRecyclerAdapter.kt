package com.lfg.homemarket.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.google.firebase.storage.FirebaseStorage
import com.lfg.homemarket.R
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lfg.homemarket.clases.GlideApp
import com.lfg.homemarket.clases.PriceBranch
import com.lfg.homemarket.databinding.ItemMarketBinding
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class BranchRecyclerAdapter (
    private var itemList : MutableList<PriceBranch>,
    private val context: Context,
    val onItemEventClick : (EventEnum, Int) -> Boolean
) : RecyclerView.Adapter<BranchRecyclerAdapter.ItemViewHolder>() {

    companion object {
        enum class EventEnum {
            CLICK, LONGCLICK, IMGCLICK
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
        holder.getImageView().setOnClickListener {
            onItemEventClick(EventEnum.IMGCLICK, position)
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

        fun getImageView () : ImageView = binding.imgBranchItem

        private fun ImageView.loadUrl(url: String) {
            if(url.startsWith("gs://"))
            {
                val storage = FirebaseStorage.getInstance()

                // Create a reference to a file from a Google Cloud Storage URI
                val gsReference = storage.getReferenceFromUrl(url)
                    GlideApp
                        .with(context)
                        .load(gsReference)
                        .centerInside()
                        .placeholder(R.mipmap.ic_no_picture)
                        .error(R.mipmap.ic_no_picture)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.d("GLIDEAPP", "Error al obtener la carga de la foto $url")
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                        })
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(this)

            }
            else {
                Picasso
                    .get()
                    .load(url)
                    .placeholder(R.mipmap.ic_no_picture)
                    .error(R.mipmap.ic_no_picture)
                    .into(this)
            }
        }

    }

}