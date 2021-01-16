package com.divij.credittracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.divij.credittracker.model.Session
import com.divij.credittracker.model.Transaction
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query

class MainViewModel : ViewModel() {
    val TAG = "MainViewModel"
    var fireStoreRepository = FireStoreRepository()
    var sessions = MutableLiveData<List<Session>>()
    var transactions = MutableLiveData<List<Transaction>>()
    var recentSession = MutableLiveData<Session>()

    fun saveSessions(session: Session) {
        fireStoreRepository.saveSession(session).addOnFailureListener {
            Log.d(TAG, "Save session failed")
        }
    }

    fun saveTransaction(transaction: Transaction, documentId: String) {
        fireStoreRepository.saveTransaction(transaction, documentId).addOnFailureListener {
            Log.d(TAG, "Save transaction failed")
        }
    }

    fun getSessions(): LiveData<List<Session>> {
        fireStoreRepository.getAllSessions().orderBy("endDate")
            .addSnapshotListener(EventListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    sessions.value = null
                    return@EventListener
                }

                val sessionsList: MutableList<Session> = mutableListOf()
                for (doc in value!!) {
                    val sessionItem = doc.toObject(Session::class.java)
                    sessionsList.add(sessionItem)
                }
                sessions.value = sessionsList
            })

        return sessions
    }

    fun getTransactions(documentId: String): LiveData<List<Transaction>> {
        fireStoreRepository.getAllTransactions(documentId)
            .orderBy("time")
            .addSnapshotListener(EventListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    transactions.value = null
                    return@EventListener
                }

                val transactionsList: MutableList<Transaction> = mutableListOf()
                for (doc in value!!) {
                    val transactionItem = doc.toObject(Transaction::class.java)
                    transactionsList.add(transactionItem)
                }
                transactions.value = transactionsList
            })

        return transactions
    }

    fun getRecentSession(): LiveData<Session> {
        fireStoreRepository.getAllSessions().orderBy("endDate", Query.Direction.DESCENDING).limit(1)
            .addSnapshotListener(EventListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    recentSession.value = null
                    return@EventListener
                }

                val sessionsList: MutableList<Session> = mutableListOf()
                for (doc in value!!) {
                    val transactionItem = doc.toObject(Session::class.java)
                    sessionsList.add(transactionItem)
                }
                if (sessionsList.isNotEmpty())
                    recentSession.value = sessionsList[0]
            })

        return recentSession
    }
}
