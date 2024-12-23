package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity

class HomeViewModel(dao: ExpenseDao): ViewModel(){
    val expenses= dao.getAllExpenses()

    fun getBalance(list:List<ExpenseEntity>): String{
          var total=0.0
          list.forEach{
              if(it.type=="Income"){
                  total += it.amount
              }
              else total-= it.amount
          }
        return total.toString()
    }

    fun getTotalIncome(list:List<ExpenseEntity>): String{
        var total=0.0
        list.forEach{
            if(it.type=="Income"){
                total += it.amount
            }
        }
        return total.toString()
    }
    fun getTotalExpense(list:List<ExpenseEntity>): String{
        var total=0.0
        list.forEach{
            if(it.type=="Expense"){
                total += it.amount
            }
        }
        return total.toString()
    }

    fun getItemIcon(item:ExpenseEntity):Int {
        if (item.category == "Salary") {
            return R.drawable.ic_upwork
        } else if (item.category == "Income") {
            return R.drawable.ic_upwork
        } else if (item.category == "Salary") {
            return R.drawable.ic_upwork
        } else if (item.category == "Income") {
            return R.drawable.ic_upwork
        } else if (item.category == "Income") {
            return R.drawable.ic_upwork
        } else if (item.category == "Salary") {
            return R.drawable.ic_upwork
        } else if (item.category == "Income") {
            return R.drawable.ic_upwork
        } else if (item.category == "Income") {
           return R.drawable.ic_upwork
        } else if (item.category == "Salary") {
            return R.drawable.ic_upwork
        } else if (item.category == "Income") {
            return R.drawable.ic_upwork
        } else return R.drawable.ic_upwork
    }
}



class HomeViewModelFactory(private val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao=ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}