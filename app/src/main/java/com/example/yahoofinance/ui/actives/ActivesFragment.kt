package com.example.yahoofinance.ui.actives

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.yahoofinance.R
import com.example.yahoofinance.ui.MainActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.mlsdev.animatedrv.AnimatedRecyclerView
import es.dmoral.toasty.Toasty
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class ActivesFragment : Fragment() {

    private lateinit var swipe : SwipeRefreshLayout
    private lateinit var activesRecyclerView: AnimatedRecyclerView
    private lateinit var activesListAdapter: ActivesListAdapter
    private val activesViewModel : ActivesViewModel by viewModel()
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actives, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe = view.findViewById(R.id.swipe)
        activesListAdapter = ActivesListAdapter()
        activesRecyclerView = view.findViewById(R.id.actives_list)
        activesRecyclerView.adapter = activesListAdapter
        activesRecyclerView.layoutManager = LinearLayoutManager(activity)
        shimmerFrameLayout = view.findViewById(R.id.shimmer)
        shimmerFrameLayout.startShimmer()

        try {
            activesViewModel.activesList.observe(context as MainActivity, Observer {
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmer()
                activesListAdapter.updateList(it)
                activesRecyclerView.scheduleLayoutAnimation()
            })
        }catch (exception : Exception){
            Toasty.error(context as MainActivity,exception.toString(),Toast.LENGTH_LONG).show()
        }

        swipe.setOnRefreshListener {
            activesViewModel.activesList.observe(context as MainActivity, Observer {
                activesListAdapter.updateList(it)
                activesRecyclerView.scheduleLayoutAnimation()
            })
            swipe.isRefreshing = false
        }

    }
}