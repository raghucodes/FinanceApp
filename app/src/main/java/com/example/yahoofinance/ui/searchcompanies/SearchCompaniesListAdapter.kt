package com.example.yahoofinance.ui.searchcompanies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.yahoofinance.R
import com.example.yahoofinance.data.api.models.searchticker.SearchTickerItem
import com.example.yahoofinance.ui.MainActivity
import com.example.yahoofinance.ui.companyprofile.CompanyProfileFragment
import kotlinx.android.synthetic.main.search_item.view.*

class SearchCompaniesListAdapter : RecyclerView.Adapter<SearchCompaniesListAdapter.MyViewHolder>() {
    private var searchCompanies : List<SearchTickerItem> = ArrayList()
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val symbol = itemView.findViewById<TextView>(R.id.symbol)
        val name = itemView.findViewById<TextView>(R.id.name)
        val stockExchange = itemView.findViewById<TextView>(R.id.stockExchangeName)
        val searchCard = itemView.findViewById<CardView>(R.id.search_card)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCompaniesListAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.search_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchCompanies.size
    }

    override fun onBindViewHolder(holder: SearchCompaniesListAdapter.MyViewHolder, position: Int) {
        holder.symbol.text = searchCompanies[position].symbol
        holder.name.text = searchCompanies[position].name
        holder.stockExchange.text = searchCompanies[position].exchangeShortName
        holder.searchCard.setOnClickListener {
            openFragment(it,searchCompanies[position].symbol)
        }
    }
    fun updateList(searchList : List<SearchTickerItem>){
        searchCompanies = searchList
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