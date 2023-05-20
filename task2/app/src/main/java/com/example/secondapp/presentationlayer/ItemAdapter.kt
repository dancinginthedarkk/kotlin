package com.example.secondapp.presentationlayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondapp.objects.Item
import com.example.secondapp.R

class ItemAdapter(
    private val onReachEndListener: () -> Unit,
    private val onItemClickListener: (Item, Int) -> Unit
) : RecyclerView.Adapter<ItemAdapter.CatsViewHolder>() {

    private var cats = listOf<Item>()
    private val BASE_URL = "https://cataas.com/cat?id="

    fun setCats(cats: List<Item>) {
        this.cats = cats
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return CatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        val cat = cats[position]
        Glide.with(holder.itemView).load(BASE_URL + cat.url).into(holder.imageCat)

        if (position >= cats.size - 10) {
            onReachEndListener.invoke()
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(cat, position)
        }
    }

    override fun getItemCount(): Int = cats.size

    class CatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCat: ImageView = itemView.findViewById(R.id.image_cat)
    }
}