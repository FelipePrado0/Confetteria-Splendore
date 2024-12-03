package com.example.confetteria_splendore.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.confetteria_splendore.controller.AuthenticationController
import com.example.confetteria_splendore.databinding.ActivityEsqueceuSenhaBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEsqueceuSenhaBinding
    private lateinit var authController: AuthenticationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authController = AuthenticationController()

        binding.btnEnviar.setOnClickListener {
            val email = binding.txtEmail.text.toString()

            authController.resetPassword(email) { success, error ->
                if (success) {
                    Toast.makeText(this, "Email sent successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "ERROR: $error", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
