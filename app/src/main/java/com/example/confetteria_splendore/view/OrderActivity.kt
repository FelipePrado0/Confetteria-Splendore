package com.example.confetteria_splendore.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.confetteria_splendore.controller.OrderAdapter
import com.example.confetteria_splendore.databinding.ActivityPedidoBinding
import com.example.confetteria_splendore.model.MenuItem
import com.example.confetteria_splendore.model.Pedido
import com.example.confetteria_splendore.view.ItemDetailsActivity
import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPedidoBinding


    // Na sua atividade de destino (CarrinhoActivity)

    var cartItems = ItemDetailsActivity().cartItems
    private lateinit var adapter: OrderAdapter
    private var orderList = mutableListOf<Pedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = OrderAdapter(orderList)
        binding.rvItensPedido.layoutManager = LinearLayoutManager(this)
        binding.rvItensPedido.adapter = adapter

        addItemToCart(MenuItem(cartItems.toString()))

        updateTotalPrice()

        binding.tvTotalPrice.text = "R$ ${calculateTotal()}"

    }

    private fun addItemToCart(item: MenuItem) {
        cartItems.add(item)
    }

    private fun calculateTotal(): Double {
        var totalPrice = 0.0
        cartItems.forEach { item ->
            totalPrice += item.price?.toDoubleOrNull() ?: 0.0
        }
        return totalPrice
    }

    private fun updateTotalPrice() {
        val totalPrice = calculateTotal()
        binding.tvTotalPrice.text = "R$ %.2f".format(totalPrice)
    }

    private fun updateOrderList(newOrders: List<Pedido>) {
        orderList.clear()
        orderList.addAll(newOrders)
        adapter.notifyDataSetChanged()
    }
}

