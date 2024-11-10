package com.kitsune.IUPB.android.envirosense.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kitsune.IUPB.android.envirosense.R
import com.kitsune.IUPB.android.envirosense.ui.viewmodel.So2ViewModel
import com.kitsune.IUPB.android.envirosense.ui.viewmodel.So2ViewModelFactory
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
import com.kitsune.IUPB.android.envirosense.data.repository.FirestoreRepository
import com.kitsune.IUPB.android.envirosense.model.SensorData

class So2Activity : AppCompatActivity() {

    private val sensorRepository = FirestoreRepository()
    private val so2ViewModel: So2ViewModel by viewModels {
        So2ViewModelFactory(sensorRepository)
    }

    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var lineChart: LineChart
    private lateinit var radarChart: RadarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_so2)

        // Inicializar los gráficos
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        lineChart = findViewById(R.id.lineChart)
        radarChart = findViewById(R.id.radarChart)


        // Observar los datos de SO2 antes de hacer la carga
        so2ViewModel.so2Data.observe(this) { data ->
            if (!data.isNullOrEmpty()) {
                data.forEachIndexed { index, sensorData ->
                    Log.d("So2Activity", "Data item $index - Municipality: ${sensorData.municipality}, Value: ${sensorData.value}")
                }
                updateChartsWithData(data)
            } else {
                Log.d("So2Activity", "No data received")
                Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show()
            }
        }

        // Iniciar la carga de datos después de la observación
        so2ViewModel.fetchSo2Data()
    }

    private fun updateChartsWithData(data: List<SensorData>) {
        setBarChartData(data)
        setPieChartData(data)
        setLineChartData(data)
        setRadarChartData(data)
    }

    private fun setBarChartData(data: List<SensorData>) {
        val entries = data.mapIndexed { index, sensorData ->
            BarEntry(index.toFloat(), sensorData.value.toFloat())
        }

        val dataSet = BarDataSet(entries, "SO2").apply {
            color = resources.getColor(R.color.purple, theme)
        }
        barChart.data = BarData(dataSet)
        barChart.invalidate()
    }

    private fun setPieChartData(data: List<SensorData>) {
        val totalValue = data.sumOf { it.value.toDouble() }

        val entries = data.map { sensorData ->
            val percentage = (sensorData.value.toDouble() / totalValue) * 100
            PieEntry(percentage.toFloat(), sensorData.municipality)
        }

        val dataSet = PieDataSet(entries, "Distribución SO2").apply {
            setColors(resources.getColor(R.color.purple, theme))
        }
        pieChart.data = PieData(dataSet)
        pieChart.invalidate()
    }

    private fun setLineChartData(data: List<SensorData>) {
        val entries = data.mapIndexed { index, sensorData ->
            Entry(index.toFloat(), sensorData.value.toFloat())
        }

        val dataSet = LineDataSet(entries, "SO2").apply {
            color = resources.getColor(R.color.green, theme)
        }
        lineChart.data = LineData(dataSet)
        lineChart.invalidate()
    }

    private fun setRadarChartData(data: List<SensorData>) {
        val entries = data.map { sensorData ->
            RadarEntry(sensorData.value.toFloat())
        }

        val dataSet = RadarDataSet(entries, "Datos Radar SO2").apply {
            color = resources.getColor(R.color.purple, theme)
        }
        radarChart.data = RadarData(dataSet)
        radarChart.invalidate()
    }
}