package com.example.confetteria_splendore.model
import java.io.Serializable


data class MenuItem(
    var id: String? = null,
    //var uid: String? = null,
    var name: String? = null,
    var description: String? = null,
    var price: String? = null,
) : Serializable
