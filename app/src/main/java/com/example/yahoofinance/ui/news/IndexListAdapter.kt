package com.example.yahoofinance.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.yahoofinance.R
import com.example.yahoofinance.data.api.models.majorindexes.MajorIndexesItem
import com.example.yahoofinance.data.api.models.news.NewsItem
import com.example.yahoofinance.ui.appContext

class IndexListAdapter : RecyclerView.Adapter<IndexListAdapter.MyViewHolder>() {
    private var indexList : List<MajorIndexesItem> = ArrayList()
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val indexName = itemView.findViewById<TextView>(R.id.majorIndexName)
        val indexPrice = itemView.findViewById<TextView>(R.id.majorIndexPrice)
        val indexPercentage = itemView.findViewById<TextView>(R.id.majorIndexPercentage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndexListAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.major_index_item,parent,false)
        return IndexListAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return indexList.size
    }

    override fun onBindViewHolder(holder: IndexListAdapter.MyViewHolder, position: Int) {
       holder.indexName.text = indexList[position].symbol
        holder.indexPrice.text = indexList[position].price.toString()
        holder.indexPercentage.text = indexList[position].changesPercentage.toString()+"%"
        if(indexList[position].changesPercentage < 0){
            holder.indexPrice.setTextColor(ContextCompat.getColor(appContext,R.color.red))
            holder.indexPercentage.setTextColor(ContextCompat.getColor(appContext,R.color.red))
        }else{
            holder.indexPrice.setTextColor(ContextCompat.getColor(appContext,R.color.green))
            holder.indexPercentage.setTextColor(ContextCompat.getColor(appContext,R.color.green))
        }
    }
    fun updateList(updatedList : List<MajorIndexesItem>){
        indexList = updatedList
        notifyDataSetChanged()
    }
}