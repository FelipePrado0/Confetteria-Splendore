package com.example.confetteria_splendore.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.confetteria_splendore.databinding.ItensPedidoBinding
import com.example.confetteria_splendore.model.Pedido

class OrderAdapter(private val orderList: MutableList<Pedido>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: ItensPedidoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItensPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]

        if (order.items.isNotEmpty()) {
            val firstItem = order.items.first()

            // Remover tudo relacionado a imagens
            holder.binding.txtNomeItem.text = firstItem.name
            holder.binding.txtPrecoItem.text = buildString {
                append("R$ ")
                append(firstItem.price)
            }
        } else {
            // Caso não haja itens, exibe informações de "sem itens"
            holder.binding.txtNomeItem.text = "Sem itens"
            holder.binding.txtPrecoItem.text = "R$ 0,00"
        }
    }

    fun updateList(newOrderList: List<Pedido>) {
        orderList.clear()
        orderList.addAll(newOrderList)
        notifyDataSetChanged()
    }
}
