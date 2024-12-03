package com.example.confetteria_splendore.model

class Pedido {
    var userName: String? = null
    var items: MutableList<MenuItem> = mutableListOf()

    constructor()

    constructor(userName: String?, items: MutableList<MenuItem>) {
        this.userName = userName
        this.items = items
    }
}
