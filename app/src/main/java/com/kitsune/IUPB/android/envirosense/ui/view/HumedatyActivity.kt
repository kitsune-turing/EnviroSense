package com.kitsune.IUPB.android.envirosense.ui.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kitsune.IUPB.android.envirosense.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry


class HumedatyActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var lineChart: LineChart
    private lateinit var radarChart: RadarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_humedaty)

        // Referencias a los gráficos
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        lineChart = findViewById(R.id.lineChart)
        radarChart = findViewById(R.id.radarChart)

        // Llenar los gráficos con datos
        setBarChartData()
        setPieChartData()
        setLineChartData()
        setRadarChartData()
    }

    private fun setBarChartData() {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, 120f))
        entries.add(BarEntry(2f, 180f))
        entries.add(BarEntry(3f, 150f))

        val dataSet = BarDataSet(entries, "Humedad")
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.invalidate()
    }

    private fun setPieChartData() {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(40f, "Elemento A"))
        entries.add(PieEntry(30f, "Elemento B"))
        entries.add(PieEntry(30f, "Elemento C"))

        val dataSet = PieDataSet(entries, "Distribución Humedad")
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate()
    }

    private fun setLineChartData() {
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 120f))
        entries.add(Entry(2f, 180f))
        entries.add(Entry(3f, 150f))

        val dataSet = LineDataSet(entries, "Humedad")
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    private fun setRadarChartData() {
        val entries = ArrayList<RadarEntry>()
        entries.add(RadarEntry(120f))
        entries.add(RadarEntry(180f))
        entries.add(RadarEntry(150f))
        entries.add(RadarEntry(200f))
        entries.add(RadarEntry(130f))

        val dataSet = RadarDataSet(entries, "Datos Radar Humedad")
        val radarData = RadarData(dataSet)
        radarChart.data = radarData
        radarChart.invalidate()
    }
}