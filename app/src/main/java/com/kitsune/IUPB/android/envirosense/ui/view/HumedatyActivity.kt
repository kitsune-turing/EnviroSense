package com.kitsune.IUPB.android.envirosense.ui.view


import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.google.firebase.firestore.FirebaseFirestore
import com.kitsune.IUPB.android.envirosense.R
import com.kitsune.IUPB.android.envirosense.data.model.SensorData
import com.kitsune.IUPB.android.envirosense.data.repository.SensorRepository
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodel.SensorViewModel
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodelfactory.SensorViewModelFactory


class HumedatyActivity : AppCompatActivity() {
    private val sensorRepository = SensorRepository(FirebaseFirestore.getInstance())
    private val sensorViewModel: SensorViewModel by viewModels { SensorViewModelFactory(sensorRepository) }

    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var lineChart: LineChart
    private lateinit var radarChart: RadarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)

        // Referencias a los gráficos
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        lineChart = findViewById(R.id.lineChart)
        radarChart = findViewById(R.id.radarChart)

        // Configurar el observador de datos
        sensorViewModel.sensorDataByMunicipality.observe(this) { groupedData ->
            updateCharts(groupedData)
        }

        // Cargar los datos de temperatura (SensorType: "Temperature")
        sensorViewModel.fetchAndGroupData("Humidity")
    }

    private fun updateCharts(groupedData: Map<String, List<SensorData>>) {
        val barEntries = mutableListOf<BarEntry>()
        val pieEntries = mutableListOf<PieEntry>()
        val lineEntries = mutableListOf<Entry>()
        val radarEntries = mutableListOf<RadarEntry>()
        val colors = listOf(Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED)


        var index = 0
        for ((municipality, sensorDataList) in groupedData) {
            val totalValue = sensorDataList.sumOf { it.value.toDouble() }.toFloat()
            val entryIndex = index.toFloat()

            // Asignar entradas a cada gráfico
            barEntries.add(BarEntry(entryIndex, totalValue))
            pieEntries.add(PieEntry(totalValue, municipality))
            lineEntries.add(Entry(entryIndex, totalValue))
            radarEntries.add(RadarEntry(totalValue))

            index++
        }


        // Configurar BarChart
        val barDataSet = BarDataSet(barEntries, "Total Humedad por Municipio").apply {
            setColors(*colors.toIntArray())
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }
        barChart.data = BarData(barDataSet)
        barChart.description.isEnabled = false
        barChart.animateY(1000)
        barChart.invalidate()


        // Configurar PieChart
        val pieDataSet = PieDataSet(pieEntries, "Distribución de Humedad").apply {
            setColors(*colors.toIntArray())
            valueTextColor = Color.WHITE
            valueTextSize = 12f
        }
        pieChart.data = PieData(pieDataSet)
        pieChart.isDrawHoleEnabled = true
        pieChart.holeRadius = 30f
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.animateY(1000)
        pieChart.invalidate()


        // Configurar LineChart
        val lineDataSet = LineDataSet(lineEntries, "Humedad por Municipio").apply {
            color = Color.BLUE
            lineWidth = 2f
            circleRadius = 5f
            setCircleColor(Color.RED)
            valueTextSize = 12f
            setDrawFilled(true)
            fillColor = Color.CYAN
        }
        lineChart.data = LineData(lineDataSet)
        lineChart.description.isEnabled = false
        lineChart.animateY(1000)
        lineChart.invalidate()


        // Configurar RadarChart
        val radarDataSet = RadarDataSet(radarEntries, "Humedad en Radar").apply {
            color = Color.MAGENTA
            fillColor = Color.GREEN
            setDrawFilled(true)
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }
        radarChart.data = RadarData(radarDataSet)
        radarChart.description.isEnabled = false
        radarChart.animateY(1000)
        radarChart.invalidate()
    }
}