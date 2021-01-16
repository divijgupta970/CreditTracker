package com.divij.credittracker.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.divij.credittracker.R
import com.divij.credittracker.adapter.TransactionsAdapter
import com.divij.credittracker.databinding.FragmentHomeBinding
import com.divij.credittracker.model.Session
import com.divij.credittracker.model.Transaction
import com.divij.credittracker.util.Utils
import com.divij.credittracker.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TransactionsAdapter
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.fabAdd.setOnClickListener {
            addTransaction()
        }
        binding.btnDone.setOnClickListener{
            endSession()
        }
        initRecyclerView()
        getRecentSession()
        return binding.root
    }

    private fun endSession() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("End Session")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes") { dialog, _ ->
                val currSession: Session? = viewModel.recentSession.value
                if (currSession != null) {
                    currSession.endSession()
                    viewModel.saveSessions(currSession)
                    viewModel.saveSessions(Session(Date()))
                }
                dialog.dismiss()
            }.setNegativeButton("No") {dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun initRecyclerView() {
        binding.rvTransactions.layoutManager = LinearLayoutManager(context)
        adapter = TransactionsAdapter()
        binding.rvTransactions.adapter = adapter
        binding.rvTransactions.itemAnimator = DefaultItemAnimator()
    }

    private fun getRecentSession() {
        viewModel.getRecentSession().observe(viewLifecycleOwner, {
            if (it != null) {
                getTransactionsForSession(it)
            }
        })
    }

    private fun addTransaction() {
        val customView: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_add_transaction, null, false)
        val etName = customView.findViewById<TextInputEditText>(R.id.etName)
        val etAmount = customView.findViewById<TextInputEditText>(R.id.etAmount)
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        materialAlertDialogBuilder.setView(customView)
            .setTitle("Add new item")
            .setNegativeButton("Cancel"
            ) { dialog, which -> dialog.dismiss() }
            .setPositiveButton("Add"
            ) { dialog, which ->
                if (etAmount.text.isNullOrBlank()) {
                    etAmount.error = "Enter Amount!"
                    return@setPositiveButton
                }
                if (etName.text.isNullOrBlank()) {
                    etName.error = "Enter Name!"
                    return@setPositiveButton
                }
                val transaction = Transaction(etName.text.toString(), etAmount.text.toString().toInt())
                var session : Session? = null
                if (viewModel.recentSession.value == null) {
                    session = Session(Date())
                    viewModel.saveSessions(session)
                    viewModel.saveTransaction(transaction, session.id)
                } else {
                    session = viewModel.recentSession.value
                    viewModel.saveTransaction(
                        transaction,
                        session!!.id
                    )
                }
                session.total += etAmount.text.toString().toInt()
                viewModel.saveSessions(session)
                dialog.dismiss()

            }.show()
    }

    private fun getTransactionsForSession(session: Session) {
        viewModel.getTransactions(session.id).observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(ArrayList(it))
                binding.tvAmount.text = Utils.getTotalForTransactions(it)
                adapter.notifyDataSetChanged()
            }

        })
    }
}