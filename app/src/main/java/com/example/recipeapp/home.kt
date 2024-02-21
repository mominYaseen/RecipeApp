package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.recipeapp.databinding.FragmentAddRecipeBinding
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class home : Fragment() {
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding!!
    private lateinit var listOfRecipes : MutableList<Recipes>
    private lateinit var firebaseRef : FirebaseFirestore
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var rv : RecyclerView
    private lateinit var adapter : HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(
            inflater,container,false
        )

        firebaseRef = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        listOfRecipes = mutableListOf()


        rv = binding.rvHome
        adapter = HomeAdapter(listOfRecipes,requireContext())
        rv.adapter = adapter
         rv.layoutManager = LinearLayoutManager(activity)





        return binding.root
        }

     private fun showRv(){

        firebaseRef.collection("recipes")
            .get().addOnSuccessListener {documents->
                for(document in documents){
                    val recipeData = document.data
                    try {
                        if (recipeData!=null){
                            val name = recipeData["name"] as String
                            val ingredients = recipeData["ingredients"] as String
                            val instructions = recipeData["instruction"] as String
                            val userName = recipeData["user"] as String
                            val link = recipeData["link"] as String
                            val recipe = Recipes(
                                name,ingredients,instructions,userName,link
                            )

                            listOfRecipes.add(recipe)

                        }
                    }catch (e: Exception) {}


                }
                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener {
                Log.i("Failed loading rv",it.toString())
            }
    }





    }

