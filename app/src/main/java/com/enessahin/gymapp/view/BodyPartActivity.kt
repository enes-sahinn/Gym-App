package com.enessahin.gymapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.enessahin.gymapp.R
import com.enessahin.gymapp.adapter.ExerciseAdapter
import com.enessahin.gymapp.databinding.ActivityBodyPartBinding
import com.enessahin.gymapp.model.BodyPart
import com.enessahin.gymapp.model.Exercise
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BodyPartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBodyPartBinding
    private lateinit var exerciseList: ArrayList<Exercise>
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var selectedBodyPart: BodyPart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyPartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent = intent
        // casting
        selectedBodyPart = intent.getSerializableExtra("bodyPart") as BodyPart
        binding.bodyPartText.text = selectedBodyPart.name
        exerciseList = ArrayList<Exercise>()
        db = Firebase.firestore
        auth = Firebase.auth

        getData()

        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        exerciseAdapter = ExerciseAdapter(exerciseList)
        binding.recyclerView2.adapter = exerciseAdapter
    }

    private fun getData() {
        db.collection(selectedBodyPart.name.lowercase()).get().addOnSuccessListener { result ->
            for (document in result) {
                val name = document.get("name") as String
                val url = document.get("url") as String
                val exercise = Exercise(name, url)

                exerciseList.add(exercise)
            }
            exerciseAdapter.notifyDataSetChanged()
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
        } else if (item.itemId == R.id.favExercises) {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.homePage) {
            val intent = Intent(this, BodyActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}