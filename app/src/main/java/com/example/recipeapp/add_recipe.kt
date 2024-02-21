package com.example.recipeapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.recipeapp.databinding.FragmentAddRecipeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class add_recipe : Fragment() {
    private lateinit var _binding: FragmentAddRecipeBinding
    private val binding get() = _binding!!
    private lateinit var imageUri : Uri

    private lateinit var progressBar : ProgressBar
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var usersRecipe: Recipes
    private var imageLink : String?=null
    private lateinit var  recipeArr : List<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddRecipeBinding.inflate(
            inflater,container,false
        )



        progressBar = binding.progBar
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth= FirebaseAuth.getInstance()

        binding.selectImg.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,101)

        }

        binding.saveRecipe.setOnClickListener {

            val recipeName = binding.recipeName.text.toString()
            val ingredients = binding.ingradients.text.toString()
            val instrustions = binding.instructions.text.toString()

            if (recipeName.isNotEmpty() && ingredients.isNotEmpty() && instrustions.isNotEmpty() && imageUri!=null){
                saveRecipeToDb(recipeName,ingredients,instrustions,imageLink!!)
            }else{
                Toast.makeText(activity, "all fields mandatory", Toast.LENGTH_SHORT).show()
            }



        }
        return binding.root

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==101 && resultCode == Activity.RESULT_OK && data != null){
            binding.recipeImg.setImageURI(data.data)
            imageUri = data.data!!

            uploadImageToStorage(imageUri)

        }


    }

    private fun uploadImageToStorage(imageUri: Uri) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val firebaseStorage = FirebaseStorage.getInstance().reference
            .child("images/$fileName")
        firebaseStorage.putFile(imageUri).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {

                imageLink = it.toString()

//                Log.i("imageLink",it.toString())
            }
        }

    }


    private fun saveRecipeToDb( recipeName: String, recipeIngredients: String, recipeInstrustions: String,imgLink:String) {
        val currUser = firebaseAuth.currentUser?.email
        progressBar.visibility = View.VISIBLE



        val recipe = hashMapOf(
            "name" to recipeName,
            "ingredients" to recipeIngredients,
            "instruction" to recipeInstrustions,
            "link" to imgLink,
            "user" to currUser
//            "userID" to firebaseAuth.currentUser?.uid.toString()
        )
        firestore.collection("recipes").add(recipe).addOnSuccessListener {
            progressBar.visibility = View.GONE
            binding.recipeName.text?.clear()
            binding.ingradients.text?.clear()
            binding.instructions.text?.clear()
            binding.recipeImg.setImageResource(R.drawable.ic_launcher_background)
            Toast.makeText(activity, "recipe uploaded", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Toast.makeText(activity, "some error occured", Toast.LENGTH_SHORT).show()
            }






//        firestore.collection("users").document(currUser).set(
//            hashMapOf(
//                "email" to firebaseAuth.currentUser?.email!!,
//                "recipeAdded" to recipeArr
//            )
//        )



    }


}