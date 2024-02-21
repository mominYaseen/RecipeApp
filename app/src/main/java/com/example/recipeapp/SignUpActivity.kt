package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.recipeapp.databinding.ActivityLoginBinding
import com.example.recipeapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginLink.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
            finish()
        }

        binding.signUp.setOnClickListener {
            val email = binding.emailLayout.text.toString()
            val password = binding.passwordLayout.text.toString()

            val confirmPassword = binding.confirmPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword){
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->

                    if (task.isSuccessful){
                        Toast.makeText(this, "account created", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(
                                this,LoginActivity::class.java
                            )
                        )
                        finish()
                    }else{
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }

                }
            }else{
                Toast.makeText(this, "password dont match", Toast.LENGTH_SHORT).show()
            }


        }


    }
}