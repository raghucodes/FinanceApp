package com.example.yahoofinance.ui.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.yahoofinance.R


class NewsDetailsFragment : Fragment() {

    private lateinit var newsUrl : String
    private lateinit var webView: WebView
    private lateinit var textView: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsUrl = arguments?.getString("newsUrl").toString()
        webView = view.findViewById(R.id.news)
        textView = view.findViewById(R.id.loading)
        webView.loadUrl(newsUrl)
        textView.visibility = View.GONE
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
    }
}