package com.enessahin.gymapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enessahin.gymapp.databinding.RecyclerRowBinding
import com.enessahin.gymapp.model.Exercise
import com.enessahin.gymapp.view.ExerciseActivity
import com.squareup.picasso.Picasso

class ExerciseAdapter(val exerciseList: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {

    class ExerciseHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.binding.rVtextView.text = exerciseList.get(position).name
        Picasso.get().load(exerciseList.get(position).url).into(holder.binding.rVimageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ExerciseActivity::class.java)
            intent.putExtra("exercise", exerciseList.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

}