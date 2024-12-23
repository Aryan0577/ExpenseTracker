package com.example.expensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expense_Table")
data class ExpenseEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String?,
    val category: String,
    val type: String,
    val amount: Double,
    val date: Long,
)

data class ExpenseSummary(
    val type: String,
    val date: String,
    val total_amount: Double
)