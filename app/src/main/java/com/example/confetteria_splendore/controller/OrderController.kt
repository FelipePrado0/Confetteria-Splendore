package com.example.confetteria_splendore.controller

import com.example.confetteria_splendore.model.MenuItem
import com.example.confetteria_splendore.model.Pedido
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class OrderController {
    private val db = FirebaseFirestore.getInstance()

    fun addOrUpdateOrder(userName: String?, newItem: MenuItem, onResult: (Boolean, String?) -> Unit) {
        val collection = db.collection("orders")

        collection.whereEqualTo("userName", userName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    val document = querySnapshot.documents[0]
                    val existingOrder = document.toObject(Pedido::class.java)

                    existingOrder?.items?.add(newItem)

                    document.reference.set(existingOrder!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful)
                                onResult(true, null)
                            else
                                onResult(false, task.exception?.message)
                        }
                } else {
                    val newOrder = Pedido(userName = userName, items = mutableListOf(newItem))
                    collection.add(newOrder)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful)
                                onResult(true, null)
                            else
                                onResult(false, task.exception?.message)
                        }
                }
            }
            .addOnFailureListener { exception ->
                onResult(false, exception.message)
            }
    }

    private fun Pedido(userName: String?, items: MutableList<MenuItem>): Any {
        TODO("Not yet implemented")
    }

    fun fetchOrders(): Query {
        return db.collection("orders")
    }
}
