package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipeapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class profile : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(
            inflater,container,false
        )

        firebaseAuth = FirebaseAuth.getInstance()

        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(
                Intent(
                    activity,LoginActivity::class.java
                )
            )
            requireActivity().finish()

        }

        return binding.root
    }


}