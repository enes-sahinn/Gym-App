package com.enessahin.gymapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.enessahin.gymapp.R
import com.enessahin.gymapp.databinding.ActivityBodyBinding
import com.enessahin.gymapp.model.BodyPart
import com.enessahin.gymapp.adapter.BodyPartAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBodyBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bodyPartList: ArrayList<BodyPart>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        bodyPartList = ArrayList<BodyPart>()

        val chest = BodyPart("Chest", R.drawable.chest)
        val biceps = BodyPart("Biceps", R.drawable.biceps)
        val back = BodyPart("Back", R.drawable.back)
        val triceps = BodyPart("Triceps", R.drawable.triceps)
        val shoulder = BodyPart("Shoulder", R.drawable.shoulder)
        val leg = BodyPart("Leg", R.drawable.leg)

        bodyPartList.add(chest)
        bodyPartList.add(biceps)
        bodyPartList.add(back)
        bodyPartList.add(triceps)
        bodyPartList.add(shoulder)
        bodyPartList.add(leg)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val bodyPartAdapter = BodyPartAdapter(bodyPartList)
        binding.recyclerView.adapter = bodyPartAdapter
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
        }
        return super.onOptionsItemSelected(item)
    }
}