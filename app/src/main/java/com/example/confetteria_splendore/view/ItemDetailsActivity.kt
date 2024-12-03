package com.example.confetteria_splendore.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.confetteria_splendore.databinding.ActivityItemDetalhesBinding
import com.example.confetteria_splendore.model.MenuItem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ItemDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetalhesBinding

    // Lista para armazenar os itens adicionados ao carrinho
    public val cartItems = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperando os dados passados pela Intent
        val itemId = intent.getStringExtra("item_id")
        val itemName = intent.getStringExtra("item_name")
        val itemDescription = intent.getStringExtra("item_description")
        val itemPrice = intent.getStringExtra("item_price")

        // Atualizando os campos com os dados (sem usar imagens)
        binding.txtNomeItem.text = itemName
        binding.txtDescricaoItem.text = itemDescription
        binding.txtPrecoItem.text = "R$ $itemPrice"

        // Ação de adicionar o item ao carrinho
        binding.btnAddCarrinho.setOnClickListener {
            val item = MenuItem().apply {
                id = itemId
                name = itemName
                description = itemDescription
                price = itemPrice.toString()
            }

            // Adicionando o item à lista do carrinho
            cartItems.add(item)

            try {
                // Crie ou abra o arquivo no armazenamento interno
                val file = File(this.filesDir, "cart_items.txt")

                // Cria ou abre o arquivo para escrita
                val fileOutputStream = FileOutputStream(file)

                // Converte a lista de MenuItems em um formato legível (como uma String)
                val dataToWrite = cartItems.joinToString("\n") { item ->
                    "${item.name} - ${item.price}"  // Personalize a formatação conforme necessário
                }

                // Escreve os dados no arquivo
                fileOutputStream.write(dataToWrite.toByteArray())

                // Fechar o fluxo de escrita
                fileOutputStream.close()

                // Exibe uma mensagem de confirmação
                Toast.makeText(this, "Items saved to file", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show()
            }

            // Exibindo uma mensagem de confirmação
            Toast.makeText(this, "Item added to cart!", Toast.LENGTH_SHORT).show()

            // Opcional: Você pode exibir a lista ou realizar outra ação aqui, como navegar para a tela do carrinho
            // Exemplo:
            // Log.d("Cart", cartItems.toString())
        }
    }
}
