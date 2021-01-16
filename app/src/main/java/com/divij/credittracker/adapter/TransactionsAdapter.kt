package com.divij.credittracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.divij.credittracker.R
import com.divij.credittracker.databinding.CardMainBinding
import com.divij.credittracker.model.Transaction
import com.divij.credittracker.util.Utils

class TransactionsAdapter() :
    RecyclerView.Adapter<TransactionViewHolder>() {
    private val transactionsList = ArrayList<Transaction>();
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardMainBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.card_main, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionsList[position])
    }

    fun setList(transactions: ArrayList<Transaction>) {
        transactionsList.clear()
        transactionsList.addAll(transactions)
    }

    override fun getItemCount() = transactionsList.size
}

class TransactionViewHolder(private val binding: CardMainBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(transaction: Transaction) {
        binding.tvDate.text = Utils.dateForTransaction(transaction.time)
        binding.tvDesc.text = transaction.description
        binding.tvAmount.text = "₹" + transaction.amount.toString()
    }

}