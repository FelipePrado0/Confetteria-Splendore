package com.example.confetteria_splendore.controller

import com.example.confetteria_splendore.model.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MenuController {
    private val db = FirebaseFirestore.getInstance()

    fun listItems(): Query {
        return db.collection("menu")
    }
}
