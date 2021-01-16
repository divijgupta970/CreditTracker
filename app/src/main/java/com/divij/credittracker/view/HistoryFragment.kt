package com.divij.credittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.divij.credittracker.R
import com.divij.credittracker.adapter.SessionsAdapter
import com.divij.credittracker.adapter.TransactionsAdapter
import com.divij.credittracker.databinding.FragmentHistoryBinding
import com.divij.credittracker.viewmodel.MainViewModel


class HistoryFragment : Fragment() {

    private lateinit var adapter: SessionsAdapter
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        initRecyclerView()
        getAllSessions()
        return binding.root
    }

    private fun getAllSessions() {
        viewModel.getSessions().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(ArrayList(it))
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvSessions.layoutManager = LinearLayoutManager(context)
        adapter = SessionsAdapter()
        binding.rvSessions.adapter = adapter
        binding.rvSessions.itemAnimator = DefaultItemAnimator()
    }
}