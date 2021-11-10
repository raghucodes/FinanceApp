package com.example.yahoofinance.ui.news

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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


class NewsFragment : Fragment() {

    private lateinit var newsRecyclerView: AnimatedRecyclerView
    private lateinit var newsListAdapter: NewsListAdapter
    private val newsViewModel : NewsViewModel by viewModel()
    private lateinit var newsLoading : SwipeRefreshLayout
    private lateinit var newsShimmerFrameLayout: ShimmerFrameLayout
    private lateinit var indexRecyclerView : RecyclerView
    private lateinit var indexListAdapter: IndexListAdapter
    private lateinit var indexLayoutManager : LinearLayoutManager
    internal val handler = Handler()
    val displayMetrics = DisplayMetrics()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListAdapter = NewsListAdapter()
        newsRecyclerView = view.findViewById(R.id.news_list)
        newsRecyclerView.adapter = newsListAdapter
        newsRecyclerView.layoutManager = LinearLayoutManager(activity)

        val duration = 10
        val pixelsToMove = 30
        val mHandler = Handler(Looper.getMainLooper())

        indexRecyclerView = view.findViewById(R.id.majorIndexesList)
        indexListAdapter = IndexListAdapter()
        indexRecyclerView.adapter = indexListAdapter
         indexLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        indexRecyclerView.layoutManager = indexLayoutManager
        val SCROLLING_RUNNABLE: Runnable = object : Runnable {
            override fun run() {
                indexRecyclerView.smoothScrollBy(pixelsToMove, 0)
                mHandler.postDelayed(this, duration.toLong())
            }
        }
        indexRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem: Int = indexLayoutManager.findLastCompletelyVisibleItemPosition()
                if (lastItem == indexLayoutManager.itemCount - 1) {
                    mHandler.removeCallbacks(SCROLLING_RUNNABLE)
                    val postHandler = Handler()
                    postHandler.postDelayed({
                        recyclerView.adapter = null
                        recyclerView.adapter = indexListAdapter
                        mHandler.postDelayed(SCROLLING_RUNNABLE, 2000)
                    }, 2000)
                }
            }
        })
        mHandler.postDelayed(SCROLLING_RUNNABLE, 2000)

        newsLoading = view.findViewById(R.id.newsSwipe)
        newsShimmerFrameLayout = view.findViewById(R.id.news_shimmer)
        newsShimmerFrameLayout.startShimmer()
        try {
            newsViewModel.stockNews.observe(context as MainActivity, Observer {
                newsShimmerFrameLayout.visibility = View.GONE
                newsShimmerFrameLayout.stopShimmer()
                newsListAdapter.updateList(it)
                newsRecyclerView.scheduleLayoutAnimation()
            })
            newsViewModel.majorIndexes.observe(context as MainActivity, Observer {
                Log.e("majorindex",it.size.toString())
                indexListAdapter.updateList(it)
            })
        }catch (exception : Exception){
            Toasty.error(context as MainActivity,exception.toString(),Toast.LENGTH_LONG).show()
        }

        newsLoading.setOnRefreshListener {
            newsViewModel.stockNews.observe(context as MainActivity, Observer {
                newsListAdapter.updateList(it)
                newsRecyclerView.scheduleLayoutAnimation()
            })
            newsLoading.isRefreshing = false
        }

    }
}
