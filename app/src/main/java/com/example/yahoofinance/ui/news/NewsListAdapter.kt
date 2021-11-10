package com.example.yahoofinance.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.yahoofinance.R
import com.example.yahoofinance.data.api.models.news.NewsItem
import com.example.yahoofinance.ui.MainActivity
import com.example.yahoofinance.ui.newsdetails.NewsDetailsFragment
import com.squareup.picasso.Picasso


class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.MyViewHolder>() {
    private var newsList : List<NewsItem> = ArrayList()
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val newsTitle = itemView.findViewById<TextView>(R.id.news_title)
        val newsOverview = itemView.findViewById<TextView>(R.id.news_overview)
        val newsSite = itemView.findViewById<TextView>(R.id.news_site)
        val newsDate = itemView.findViewById<TextView>(R.id.published_date)
        val newsImage = itemView.findViewById<ImageView>(R.id.news_image)
        val newsCard = itemView.findViewById<CardView>(R.id.news_card)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListAdapter.MyViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsListAdapter.MyViewHolder, position: Int) {
       holder.newsTitle.text = newsList[position].title
        holder.newsOverview.text = newsList[position].text
        holder.newsSite.text = newsList[position].site
        holder.newsDate.text = newsList[position].publishedDate
        Picasso.get().load(newsList[position].image).into(holder.newsImage)
        holder.newsCard.setOnClickListener {
            val newsUrl = newsList[position].url
            openFragment(it,newsUrl)
        }
    }
    fun updateList(updatedList : List<NewsItem>){
        newsList = updatedList
        notifyDataSetChanged()
    }
    fun openFragment(view: View,newsUrl : String){
        val fragment : Fragment = NewsDetailsFragment()
        val args : Bundle = Bundle()
        args.putString("newsUrl",newsUrl)
        fragment.arguments = args
        val appCompatActivity : AppCompatActivity = view.context as MainActivity
        appCompatActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }
}