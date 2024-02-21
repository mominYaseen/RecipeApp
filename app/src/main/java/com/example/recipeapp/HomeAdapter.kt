package com.example.recipeapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

open class HomeAdapter(
    private val listOfRecipe :  List<Recipes>,
    private val context :Context
) :RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView:View):ViewHolder(itemView){
        val recipeName:TextView = itemView.findViewById(R.id.recipe_item_name)
        val recipeIngredients:TextView = itemView.findViewById(R.id.recipe_item_ingredients)
        val recipeInstructions:TextView = itemView.findViewById(R.id.recipe_item_instructions)
        val recipeUser:TextView = itemView.findViewById(R.id.recipe_item_user)
        val recipeImg : ImageView = itemView.findViewById(R.id.recipe_item_img)
        val delete :Button = itemView.findViewById(R.id.delete_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_layout_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfRecipe.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentRecipe = listOfRecipe[position]

        holder.recipeName.text = currentRecipe.recipeName
        holder.recipeInstructions.text = currentRecipe.instructions
        holder.recipeIngredients.text = currentRecipe.ingredients
        holder.recipeUser.text = currentRecipe.userName
        val imgLink = currentRecipe.imgLink
        addImageToRecipe(imgLink,holder)
        holder.delete.setOnClickListener {
            onDeleteButtonClick()
        }
    }

    private fun addImageToRecipe(imgLink: String,holder:HomeViewHolder) {
            Glide.with(context)
                .load(imgLink).into(holder.recipeImg)
    }

    fun onDeleteButtonClick() {
        // Replace "your_collection" and "your_document_id" with your actual collection and document ID


        // Delete the document from Firestore
        FirebaseFirestore.getInstance().collection("recipes")
            .document()
            .delete()
            .addOnSuccessListener {
                // Document successfully deleted
                // Add any additional actions if neededL

            }
            .addOnFailureListener {

            }
    }
}

















