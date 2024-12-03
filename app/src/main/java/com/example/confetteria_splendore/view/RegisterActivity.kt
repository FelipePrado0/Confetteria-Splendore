package com.example.confetteria_splendore.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.confetteria_splendore.controller.AuthenticationController
import com.example.confetteria_splendore.controller.UserController
import com.example.confetteria_splendore.databinding.ActivityCadastrarBinding
import com.example.confetteria_splendore.model.Usuario
import java.time.LocalDate

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarBinding
    private lateinit var authController: AuthenticationController
    private lateinit var userController: UserController

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authController = AuthenticationController()
        userController = UserController()

        binding.btnSalvar.setOnClickListener {
            val name = binding.txtNome.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtSenha.text.toString()
            val confirmPassword = binding.txtConfirmaSenha.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                Toast.makeText(this, "Confirm password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authController.createUser(name, email, password) { success, error ->
                if (success) {
                    val user = Usuario().apply {
                        this.name = name
                        this.email = email
                        this.data = LocalDate.now().toString()
                    }

                    userController.addUser(user)

                    Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "ERROR: $error", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
