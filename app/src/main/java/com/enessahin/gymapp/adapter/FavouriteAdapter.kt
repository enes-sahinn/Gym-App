package com.enessahin.gymapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enessahin.gymapp.databinding.RecyclerRowBinding
import com.enessahin.gymapp.model.Exercise
import com.enessahin.gymapp.view.ExerciseActivity
import com.squareup.picasso.Picasso

class FavouriteAdapter(val favouriteList : ArrayList<Exercise>) : RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder>() {

    class FavouriteHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        holder.binding.rVtextView.text = favouriteList.get(position).name
        Picasso.get().load(favouriteList.get(position).url).into(holder.binding.rVimageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ExerciseActivity::class.java)
            intent.putExtra("exercise", favouriteList.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

}