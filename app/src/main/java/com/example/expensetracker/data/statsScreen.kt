package com.example.expensetracker.data

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.room.Entity
import com.example.expensetracker.R
import com.example.expensetracker.TransactionItem
import com.example.expensetracker.TransactionList
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.ui.theme.ExpenseTextView
import com.example.expensetracker.viewmodel.StatsViewModel
import com.example.expensetracker.viewmodel.StatsViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.Utils
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun StatsScreen(navController: NavController){
   Scaffold (
       topBar = {
           Box(modifier= Modifier
               .fillMaxWidth()
               .padding(top = 60.dp, start = 16.dp, end = 16.dp)
           ){
               ExpenseTextView(
                   text="Statistics",
                   fontSize=20.sp,
                   fontWeight = FontWeight.Bold,
                   color= Color.White,
                   modifier = Modifier
                       .padding(16.dp)
                       .align(Alignment.Center)

               )
               Icon(
                   Icons.Filled.MoreVert, null, modifier = Modifier
                       .align(Alignment.CenterEnd)
                       .padding(end = 18.dp),
                   )


               Icon(
                   Icons.Filled.ArrowBack, null, modifier = Modifier
                       .align(Alignment.CenterStart)
                       .padding(end = 18.dp))
           }
       }){
            val viewModel=StatsViewModelFactory(navController.context).create(StatsViewModel::class.java)
            val dataState= viewModel.enteries.collectAsState(initial = emptyList())
            val topExpense= viewModel.topenteries.collectAsState(initial = emptyList())
            Column(modifier = Modifier.padding(it)) {
                   val enteries= viewModel.getEntriesForChart(dataState.value)
                  LineChart(entries = enteries)

                Spacer(modifier = Modifier.height(16.dp))
                TransactionList(modifier = Modifier, list =topExpense.value,"Top Spending" )
            }
   }

}

@Composable
fun LineChart(entries: List<Entry>) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            val view = LayoutInflater.from(context).inflate(R.layout.stats_line_chart, null)
            view
        }, modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) { view ->
        val lineChart = view.findViewById<LineChart>(R.id.lineChart)

        val dataSet = LineDataSet(entries, "Expenses").apply {
            color = android.graphics.Color.parseColor("#FF2F7E79")
            valueTextColor = android.graphics.Color.BLACK
            lineWidth = 3f
            axisDependency = YAxis.AxisDependency.RIGHT
            setDrawFilled(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = 12f
            valueTextColor = android.graphics.Color.parseColor("#FF2F7E79")
            val drawable = ContextCompat.getDrawable(context, R.drawable.char_gradient)
            drawable?.let {
                fillDrawable = it
            }

        }

        lineChart.xAxis.valueFormatter =
            object : com.github.mikephil.charting.formatter.ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return formatDateForChart(value.toLong())
                }
            }
        lineChart.data = com.github.mikephil.charting.data.LineData(dataSet)
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.invalidate()
    }
}



fun formatDateForChart(dateInMillis: Long): String {
    val dateFormatter = SimpleDateFormat("dd-MMM", Locale.getDefault())
    return dateFormatter.format(dateInMillis)
}