package com.example.yahoofinance.ui.companyprofile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setAlpha
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.yahoofinance.R
import com.example.yahoofinance.data.api.models.chart.ChartItem
import com.example.yahoofinance.ui.MainActivity
import com.example.yahoofinance.ui.appContext
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_company_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CompanyProfileFragment : Fragment() {
    private val companyProfileViewModel : CompanyProfileViewModel by viewModel()
    private lateinit var swipe : SwipeRefreshLayout
    private var chartItems : List<ChartItem> = ArrayList()
    private lateinit var barChart : BarChart
    private lateinit var lineChart : LineChart
    private lateinit var ticker : String
    private lateinit var companyCeo : TextView
    private lateinit var companySector : TextView
    private lateinit var companyImage : ImageView
    private lateinit var companyTicker : TextView
    private lateinit var companyName : TextView
    private lateinit var companyPrice : TextView
    private lateinit var companyOverview : TextView
    private lateinit var time : String
    private lateinit var oneMin : Button
    private lateinit var fifteenMin : Button
    private lateinit var fiveMin : Button
    private  var range : Int = 10
    private lateinit var mView: View
    val barData : ArrayList<BarEntry> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        ticker = arguments?.getString("ticker").toString()
        time = "1min"
        swipe = view.findViewById(R.id.swipe)
        lineChart = view.findViewById(R.id.profileChart)
        lineChart.setTouchEnabled(true)
        companyImage = view.findViewById(R.id.companyImage)
        oneMin = view.findViewById(R.id.oneMin)
        fiveMin = view.findViewById(R.id.fiveMin)
        fifteenMin = view.findViewById(R.id.fifteenMin)
        companyTicker = view.findViewById(R.id.companyTicker)
        companyName = view.findViewById(R.id.companyName)
        companyPrice = view.findViewById(R.id.companyPrice)
        companyCeo = view.findViewById(R.id.companyCeo)
        companySector = view.findViewById(R.id.companySector)
        companyOverview = view.findViewById(R.id.companyOverview)
        barChart = view.findViewById(R.id.barChart)
        try {
            if (ticker != null) {
                getHistoricPrice(time,ticker,range)
                getProfile(ticker)
            }
            oneMin.setOnClickListener {
                time = "1min"
                getHistoricPrice(time,ticker,range)
            }
            fiveMin.setOnClickListener {
                time = "5min"
                range = 79
                getHistoricPrice(time,ticker,range)
            }
            fifteenMin.setOnClickListener {
                time = "15min"
                range = 27
                getHistoricPrice(time,ticker,range)
            }
        }catch (exception : Exception){
            Toasty.error(context as MainActivity,exception.toString(),Toast.LENGTH_LONG).show()
        }

        swipe.setOnRefreshListener {
            getHistoricPrice(time,ticker,range)
            swipe.isRefreshing = false
        }
    }
 fun getHistoricPrice(time: String,ticker : String,range : Int){
     companyProfileViewModel.getHistoricalPrices(time,ticker).observe(viewLifecycleOwner,
         Observer {
             chartItems = it
             Log.e("date", chartItems[0].date)
             displayGraph(range)
             displayBar()
         })
 }

    fun getProfile(ticker: String){
        companyProfileViewModel.getCompanyProfile(ticker)
            .observe(viewLifecycleOwner, Observer {
                Picasso.get().load(it[0].image).into(companyImage)
                companyTicker.text = it[0].symbol
                companyName.text = it[0].companyName
                companyPrice.text = it[0].price.toString()
                companyCeo.text = "CEO: "+it[0].ceo
                companySector.text = "Sector: "+it[0].sector
                companyOverview.text = "Overview: \n"+it[0].description
            })
    }
    fun displayGraph(range : Int){
        val description : Description = Description()
        description.text = "On Date :"+chartItems[0].date.split(" ")[0]
        lineChart.description = description
        lineChart.xAxis.labelCount = 3
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.valueFormatter = AxisValueFomatter()
        lineChart.animateX(3000)
        lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
            override fun onNothingSelected() {

            }
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val balloon = createBalloon(appContext){
                    setText(chartItems[0].date.split(" ")[1])
                    setBalloonAnimation(BalloonAnimation.CIRCULAR)
                    setArrowPosition(0.5F)
                    setArrowOrientation(ArrowOrientation.BOTTOM)
                }
                balloon.show(mView)
                balloon.dismissWithDelay(2000L)
            }
        })
        val lineDataSet : LineDataSet = LineDataSet(dataValues(range),ticker)
        lineDataSet.setDrawFilled(true)
        //lineDataSet.setCircleColor(R.color.black)
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawCircleHole(false)
        val datasets : ArrayList<ILineDataSet> = ArrayList()
        datasets.add(lineDataSet)
        val data : LineData = LineData(datasets)
        lineChart.data = data
        lineChart.invalidate()
    }

    fun displayBar(){
        val description : Description = Description()
        description.text = "On Date :"+chartItems[0].date.split(" ")[0]
        barChart.description = description
        barChart.xAxis.labelCount = 3
        barChart.axisRight.isEnabled = false
        barChart.xAxis.valueFormatter = AxisValueFomatter()
        val color : Int = ContextCompat.getColor(appContext,R.color.aqua)
        val barDataSet : BarDataSet = BarDataSet(barData,"price")
        barDataSet.color = color
        barDataSet.barBorderWidth = 6f
        barDataSet.barBorderColor = R.color.white
        barDataSet.setDrawValues(false)
        val bar : BarData = BarData(barDataSet)
        barChart.data = bar
        barChart.setFitBars(true)
        barChart.animateY(3000)
        barChart.invalidate()
    }

    private fun dataValues(range: Int) : List<Entry>{
        val data : ArrayList<Entry> = ArrayList()
        if(chartItems.isNotEmpty()) {
            for (i in 0 until range) {
                //val time: Float = getDate(chartItems[i].date)
                val date = chartItems[i].date.split(" ")[1]
                //val date = chartItems[i].date
                var sdf : SimpleDateFormat = SimpleDateFormat("HH:mm:ss")
                var newDate : Date = sdf.parse(date)
                var milliSeconds = newDate.time.toFloat()
                val highPrice = chartItems[i].high.toFloat()
                data.add(Entry(milliSeconds, highPrice))
                barData.add(BarEntry(milliSeconds,highPrice))
            }
        }

        val sortedData = data.sortedWith(compareBy {
            it.x
        })
        Log.e("data size",data.size.toString())
        return sortedData
    }
}
