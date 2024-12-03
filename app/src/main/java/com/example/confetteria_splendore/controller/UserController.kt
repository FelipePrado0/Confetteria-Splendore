package com.example.confetteria_splendore.controller

import com.example.confetteria_splendore.controller.AuthenticationController
import com.example.confetteria_splendore.model.Usuario
import com.google.firebase.firestore.FirebaseFirestore

class UserController {
    private val db = FirebaseFirestore.getInstance()
    private val authController = AuthenticationController()

    fun addUser(user: Usuario) {
        val collection = db.collection("users")
        collection.add(user)
    }

    fun getUserName(onResult: (String?) -> Unit) {
        val userEmail = authController.getUserEmail()
        val collection = db.collection("users")

        collection.whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    val userDocument = querySnapshot.documents[0]
                    val name = userDocument.getString("name")
                    onResult(name)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                onResult(null)
                exception.printStackTrace()
            }
    }
}
