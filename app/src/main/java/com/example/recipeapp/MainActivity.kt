package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openFragment(home())

        binding.bottomNavigation.setOnItemSelectedListener {
            val id = it.itemId

            when(id){
                R.id.home ->{
                    openFragment(home())
                }
                R.id.add ->{
                    openFragment(add_recipe())
                }
                R.id.profile ->{
                    openFragment(profile())
                }

                else -> {

                }

            }
            return@setOnItemSelectedListener true

        }


    }
    private fun openFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_view,fragment)
            .commit()
    }

//    override fun onStart() {
//        super.onStart()
//    }
}