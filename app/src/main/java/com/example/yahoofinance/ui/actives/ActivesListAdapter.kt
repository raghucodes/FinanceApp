package com.example.yahoofinance.ui.actives

import android.os.Bundle
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
import com.example.yahoofinance.data.api.models.actives.TodaysActivesItem
import com.example.yahoofinance.ui.MainActivity
import com.example.yahoofinance.ui.companyprofile.CompanyProfileFragment

class ActivesListAdapter:RecyclerView.Adapter<ActivesListAdapter.MyViewHolder>() {
    private var activeCompanies : List<TodaysActivesItem> = ArrayList()
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var companyTicker = itemView.findViewById<TextView>(R.id.ticker)
        var companyName = itemView.findViewById<TextView>(R.id.companyname)
        var price = itemView.findViewById<TextView>(R.id.price)
        var trending = itemView.findViewById<ImageView>(R.id.trending)
        var priceChange = itemView.findViewById<TextView>(R.id.pricechange)
        var percentageChange = itemView.findViewById<TextView>(R.id.percentage)
        var activesCard = itemView.findViewById<CardView>(R.id.actives_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivesListAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.actives_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return activeCompanies.size
    }

    override fun onBindViewHolder(holder: ActivesListAdapter.MyViewHolder, position: Int) {
        holder.companyTicker.text = activeCompanies[position].ticker
        holder.companyName.text = activeCompanies[position].companyName
        holder.price.text = activeCompanies[position].price
        holder.priceChange.text = activeCompanies[position].changes.toString()
        holder.percentageChange.text = activeCompanies[position].changesPercentage
        if(activeCompanies[position].changesPercentage.contains("-")){
            holder.trending.setImageResource(R.drawable.ic_trending_down)
        }else{
            holder.trending.setImageResource(R.drawable.ic_trending_up)
        }
        holder.activesCard.setOnClickListener {
            val ticker = activeCompanies[position].ticker
            openFragment(it,ticker)
        }
    }

    fun updateList(activesList : List<TodaysActivesItem>){
        activeCompanies = activesList
        notifyDataSetChanged()
    }

    fun openFragment(view: View,ticker : String){
        val fragment : Fragment = CompanyProfileFragment()
        val args : Bundle = Bundle()
        args.putString("ticker",ticker)
        fragment.arguments = args
        val appCompatActivity : AppCompatActivity = view.context as MainActivity
        appCompatActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }

}