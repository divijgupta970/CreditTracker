package com.divij.credittracker.viewmodel

import com.divij.credittracker.model.Session
import com.divij.credittracker.model.Transaction
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreRepository {
    var fireStoreDB = FirebaseFirestore.getInstance()

    fun saveTransaction(transaction: Transaction, documentId : String): Task<DocumentReference> {
        val documentReference = fireStoreDB.collection("sessions").document(documentId)
            .collection("transactions")
        return documentReference.add(transaction)
    }
    fun saveSession(session: Session): Task<Void> {
        val documentReference = fireStoreDB.collection("sessions").document(session.id)
        return documentReference.set(session)
    }

    fun getAllSessions(): CollectionReference {
        return fireStoreDB.collection("sessions")
    }

    fun getAllTransactions(documentId: String): CollectionReference {
        return fireStoreDB.collection("sessions").document(documentId).collection("transactions")
    }

}