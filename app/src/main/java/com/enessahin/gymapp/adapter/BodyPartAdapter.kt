package com.enessahin.gymapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enessahin.gymapp.view.BodyPartActivity
import com.enessahin.gymapp.databinding.RecyclerRowBinding
import com.enessahin.gymapp.model.BodyPart

class BodyPartAdapter(val bodyPartList : ArrayList<BodyPart>) : RecyclerView.Adapter<BodyPartAdapter.BodyPartHolder>() {
    class BodyPartHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyPartHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BodyPartHolder(binding)
    }

    override fun onBindViewHolder(holder: BodyPartHolder, position: Int) {

        holder.binding.rVtextView.text = bodyPartList.get(position).name
        val resId = holder.itemView.context.getResources().getIdentifier(bodyPartList.get(position).name.lowercase(), "drawable", holder.itemView.context.getPackageName())
        holder.binding.rVimageView.setImageResource(resId)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, BodyPartActivity::class.java)
            intent.putExtra("bodyPart", bodyPartList.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bodyPartList.size
    }
}