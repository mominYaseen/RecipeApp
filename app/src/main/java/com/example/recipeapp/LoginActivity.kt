package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.recipeapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signUpLink.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.login.setOnClickListener {
            val email = binding.emailLayout.text.toString()
            val password = binding.passwordLayout.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->

                    if (task.isSuccessful){
                        Toast.makeText(this, "logged in ", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(
                                this,MainActivity::class.java
                            )
                        )
                        finish()
                    }else{
                        Toast.makeText(this, "logged failed", Toast.LENGTH_SHORT).show()
                    }


                }

            }else{
                Toast.makeText(this, "all fields mandatory", Toast.LENGTH_SHORT).show()
            }

        }

    }
}