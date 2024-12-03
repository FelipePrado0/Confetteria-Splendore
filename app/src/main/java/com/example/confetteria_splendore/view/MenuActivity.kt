package com.example.confetteria_splendore.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.confetteria_splendore.controller.AuthenticationController
import com.example.confetteria_splendore.controller.MenuAdapter
import com.example.confetteria_splendore.controller.MenuController
import com.example.confetteria_splendore.databinding.ActivityCardapioBinding
import com.example.confetteria_splendore.`interface`.OnDetailsClickListener
import com.example.confetteria_splendore.model.MenuItem
import com.google.firebase.firestore.ListenerRegistration

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardapioBinding
    private lateinit var authController: AuthenticationController

    private lateinit var adapter: MenuAdapter
    private var itemList = mutableListOf<MenuItem>()
    private var listener: ListenerRegistration? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MenuActivity", "onCreate chamado")

        try {
            binding = ActivityCardapioBinding.inflate(layoutInflater)
            setContentView(binding.root)

            adapter = MenuAdapter(itemList, object : OnDetailsClickListener {
                override fun onDetailsClick(item: MenuItem) {
                    val intent = Intent(this@MenuActivity, ItemDetailsActivity::class.java)
                    intent.putExtra("item_id", item.id)
                    intent.putExtra("item_name", item.name)
                    intent.putExtra("item_description", item.description)
                    // Removido o item_image
                    intent.putExtra("item_price", item.price)
                    startActivity(intent)
                }
            })
            binding.rvItems.layoutManager = LinearLayoutManager(this)
            binding.rvItems.adapter = adapter
            initListener()

            binding.btnLogout.setOnClickListener {
                authController.logout()
            }

            binding.btnOrder.setOnClickListener {
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }

        } catch (e: Exception) {
            Log.e("MenuActivity", "Erro no onCreate: ${e.message}")
        }
    }

    private fun initListener() {
        val itemController = MenuController()
        listener = itemController.listItems().addSnapshotListener { querySnapshot, error ->

            if (error != null) {
                Log.e("MenuActivity", "Erro ao buscar itens do menu: $error")
                return@addSnapshotListener
            }

            if (querySnapshot != null) {
                val newItems = mutableListOf<MenuItem>()
                Log.d("MenuActivity", "Documentos encontrados: ${querySnapshot.size()}")

                for (document in querySnapshot) {
                    Log.d("MenuActivity", "Documento: ${document.id}, Dados: ${document.data}")

                    val nome = document.getString("nome")
                    val descricao = document.getString("descricao")
                    val valor = document.get("valor")

                    // Verifica se os campos estão presentes e não são nulos
                    if (nome.isNullOrEmpty() || descricao.isNullOrEmpty() || valor == null) {
                        Log.e("MenuActivity", "Documento inválido: id=${document.id}, dados=${document.data}")
                        continue
                    }

                    // Adiciona o item à lista somente se os campos forem válidos
                    newItems.add(
                        MenuItem(
                            document.id,
                            nome,
                            descricao,
                            valor.toString()  // Converte o valor para String para compatibilidade
                        )
                    )
                }

                if (newItems.isEmpty()) {
                    Log.d("MenuActivity", "Nenhum item foi adicionado à lista.")
                } else {
                    Log.d("MenuActivity", "Novos itens adicionados à lista: ${newItems.size}")
                }

                adapter.updateList(newItems)
            } else {
                Log.d("MenuActivity", "Nenhum documento encontrado")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.remove()
    }
}
