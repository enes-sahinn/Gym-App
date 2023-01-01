package com.enessahin.gymapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.enessahin.gymapp.R
import com.enessahin.gymapp.adapter.VideoAdapter
import com.enessahin.gymapp.databinding.ActivityExerciseBinding
import com.enessahin.gymapp.model.Exercise
import com.google.android.exoplayer2.ExoPlayer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var videoArrayList: ArrayList<Exercise>
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var currentUser: FirebaseUser
    private lateinit var selectedExercise : Exercise


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Firebase.firestore
        auth = Firebase.auth
        currentUser = auth.currentUser!!
        val intent = intent
        selectedExercise = intent.getSerializableExtra("exercise") as Exercise
        videoArrayList = ArrayList<Exercise>()

        getData()
        videoAdapter = VideoAdapter(this, videoArrayList)
        binding.videosRV.adapter = videoAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        db.collection("exercise-videos")
            .whereEqualTo("name", selectedExercise.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var videoUrl = document.get("url") as String
                    val name = document.get("name") as String
                    val exercise = Exercise(name, videoUrl)
                    videoArrayList.add(exercise)
                }
                videoAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.gym_menu2,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addFavourite) {
            val data = hashMapOf(
                "name" to selectedExercise.name,
                "url" to selectedExercise.url
            )
            db.collection(currentUser.uid).document(selectedExercise.name.lowercase()).set(data)

            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.deleteFavourite) {
            db.collection(currentUser.uid).document(selectedExercise.name.lowercase())
                .delete()
                .addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()}

            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}