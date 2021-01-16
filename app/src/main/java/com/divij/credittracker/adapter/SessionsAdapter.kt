package com.divij.credittracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.divij.credittracker.R
import com.divij.credittracker.databinding.CardMainBinding
import com.divij.credittracker.databinding.CardSessionBinding
import com.divij.credittracker.model.Session
import com.divij.credittracker.model.Transaction
import com.divij.credittracker.util.Utils

class SessionsAdapter() :
    RecyclerView.Adapter<SessionViewHolder>() {
    private val sessionsList = ArrayList<Session>();
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardSessionBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.card_session, parent, false)
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(sessionsList[position])
    }

    fun setList(sessions: ArrayList<Session>) {
        sessionsList.clear()
        sessionsList.addAll(sessions)
    }

    override fun getItemCount() = sessionsList.size
}

class SessionViewHolder(private val binding: CardSessionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(session: Session) {
        binding.tvAmount.text = "₹" + session.total.toString()
        val duration =
            "${Utils.dateForSession(session.startDate)} - ${Utils.dateForSession(session.endDate)}"
        binding.tvDuration.text = duration
    }

}