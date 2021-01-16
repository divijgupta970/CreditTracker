package com.divij.credittracker.util

import com.divij.credittracker.model.Transaction
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun dateForTransaction(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        return simpleDateFormat.format(date)
    }

    fun dateForSession(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("dd MMM")
        return simpleDateFormat.format(date)
    }

    fun getTotalForTransactions(transactionList: List<Transaction?>): String {
        return "₹" + transactionList.stream().mapToInt { it!!.amount }.sum()
    }
}