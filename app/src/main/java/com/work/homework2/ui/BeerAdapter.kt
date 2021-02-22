package com.work.homework2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.homework2.R
import com.work.homework2.data.BeerModelsItem

open class BeerAdapter : RecyclerView.Adapter<BeerAdapter.ViewHolder>() {
    var itemList = emptyList<BeerModelsItem>()
    lateinit var listener: OnBeerItemClickListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val beerName: TextView

        init {
            beerName = itemView.findViewById(R.id.beer_name)
            itemView.setOnClickListener {
                listener.onItemClick(this, itemView, itemList[adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.beer_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.beerName.text = itemList[position].name
    }

    open fun setBeerData(newList: List<BeerModelsItem>) {
        itemList = newList
        notifyDataSetChanged()
    }
}