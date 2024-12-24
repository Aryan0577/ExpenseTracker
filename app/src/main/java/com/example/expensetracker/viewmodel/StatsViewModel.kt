package com.example.expensetracker.viewmodel


import android.content.Context
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.data.model.ExpenseSummary
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.Utils
import java.text.SimpleDateFormat
import java.util.Locale

class StatsViewModel(dao: ExpenseDao): ViewModel(){
    val enteries= dao.getAllExpenseByDate()

    val topenteries= dao.getTopExpense()
    fun getEntriesForChart(entries: List<ExpenseSummary>): List<Entry> {
        val list = mutableListOf<Entry>()
        for (entry in entries) {
            val formattedDate = getMillisFromDate(entry.date)
            if (formattedDate != null) {
                list.add(Entry(formattedDate.toFloat(), entry.total_amount.toFloat()))
            }
        }
        return list
    }

    private fun getMillisFromDate(dateString: String, format: String = "dd-MM-yyyy"): Long? {
        return try {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = dateFormat.parse(dateString)
            date?.time
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}



class StatsViewModelFactory(private val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            val dao=ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}