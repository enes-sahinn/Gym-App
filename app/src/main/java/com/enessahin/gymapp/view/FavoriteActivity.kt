package com.enessahin.gymapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.enessahin.gymapp.R
import com.enessahin.gymapp.adapter.FavouriteAdapter
import com.enessahin.gymapp.databinding.ActivityFavoriteBinding
import com.enessahin.gymapp.model.Exercise
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var favouriteList: ArrayList<Exercise>
    private lateinit var favoriteAdapter: FavouriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Firebase.firestore
        auth = Firebase.auth
        favouriteList = ArrayList<Exercise>()

        getData()

        binding.rVFavoritesExercises.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavouriteAdapter(favouriteList)
        binding.rVFavoritesExercises.adapter = favoriteAdapter
    }

    private fun getData() {
        val currentUserId = auth.currentUser!!.uid
        db.collection(currentUserId).get().addOnSuccessListener { result ->
            for (document in result) {
                val name = document.get("name") as String
                val url = document.get("url") as String
                val exercise = Exercise(name, url)

                favouriteList.add(exercise)
            }
            favoriteAdapter.notifyDataSetChanged()
        }.addOnFailureListener{
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.gym_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.signOut) {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else if (item.itemId == R.id.homePage) {
            val intent = Intent(this, BodyActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}