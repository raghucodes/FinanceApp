package com.example.yahoofinance.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.example.yahoofinance.R
import com.example.yahoofinance.ui.actives.ActivesFragment
import com.example.yahoofinance.ui.networkstate.Event
import com.example.yahoofinance.ui.networkstate.NetworkEvents
import com.example.yahoofinance.ui.news.NewsFragment
import com.example.yahoofinance.ui.searchcompanies.SearchCompaniesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_news_details.*

lateinit var appContext: Context
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appContext = this
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigationView.setOnNavigationItemSelectedListener(navigationListener)
        openActivesFragment()

        NetworkEvents.observe(this, Observer {
            if(it is Event.ConnectivityAvailable){
               // Toast.makeText(applicationContext,"connected", Toast.LENGTH_SHORT).show()
                Toasty.success(applicationContext,"Back Online",Toast.LENGTH_LONG).show()
                // dialog?.hide()
            }
            if(it is Event.ConnectivityLost){
                //Toast.makeText(applicationContext,"disconnected", Toast.LENGTH_LONG).show()
                Toasty.error(applicationContext,"No Internet",Toast.LENGTH_LONG).show()
                displayDialog()
            }
        })
    }

    fun displayDialog(){
        val builder: AlertDialog.Builder? = this?.let { AlertDialog.Builder(it) }
        builder?.setMessage("No Internet Detected")?.setTitle("Connection Lost")
        builder?.apply {
            setPositiveButton("open settings", DialogInterface.OnClickListener{ dialog, id ->
                val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                startActivityForResult(panelIntent, 545)
            })
            setNegativeButton("cancel", DialogInterface.OnClickListener{ dialog, id ->

            })
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

     private fun openActivesFragment(){
         val fragment : Fragment = ActivesFragment()
        loadFragment(fragment)
    }
    private fun loadFragment(fragment: Fragment){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private val navigationListener : BottomNavigationView.OnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener{
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                R.id.menu_feed ->{
                    val fragment : Fragment = ActivesFragment()
                    loadFragment(fragment)
                    return true
                }
                R.id.menu_search ->{
                    val fragment : Fragment = SearchCompaniesFragment()
                    loadFragment(fragment)
                    return true
                }
                R.id.menu_news ->{
                    val fragment : Fragment = NewsFragment()
                    loadFragment(fragment)
                    return true
                }
            }
            return false
        }
    }
}