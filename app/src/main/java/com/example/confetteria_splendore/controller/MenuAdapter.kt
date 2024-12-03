package com.example.confetteria_splendore.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.confetteria_splendore.databinding.ItensPedidoBinding
import com.example.confetteria_splendore.`interface`.OnDetailsClickListener
import com.example.confetteria_splendore.model.MenuItem

class MenuAdapter(private val pedidoList: MutableList<MenuItem>, private val listener: OnDetailsClickListener) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(val binding: ItensPedidoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItensPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pedidoList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        if (getItemCount() > 0) {
            val pedido = pedidoList[position]

            // Configurando o nome do item
            holder.binding.txtNomeItem.text = pedido.name

            // Configurando o preço do item
            holder.binding.txtPrecoItem.text = buildString {
                append("RS ")
                append(pedido.price)
            }

            // Configurando o clique no item
            holder.binding.details.setOnClickListener {
                listener.onDetailsClick(pedido)
            }
        }
    }

    fun updateList(newPedido: MutableList<MenuItem>) {
        pedidoList.clear()
        pedidoList.addAll(newPedido)
        notifyDataSetChanged()
    }
}
