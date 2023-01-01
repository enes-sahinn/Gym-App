package com.enessahin.gymapp.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.enessahin.gymapp.R
import com.enessahin.gymapp.model.Exercise

class VideoAdapter(private var context : Context, private var videoArrayList : ArrayList<Exercise>?)
    : RecyclerView.Adapter<VideoAdapter.HolderVideo>() {

        class HolderVideo(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var videoView:VideoView = itemView.findViewById(R.id.videoView)
            var titleTv:TextView = itemView.findViewById(R.id.titleTv)
            var progressBar:ProgressBar = itemView.findViewById(R.id.progressBar)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderVideo {
        val view = LayoutInflater.from(context).inflate(R.layout.row_video, parent, false)
        return HolderVideo(view)
    }

    override fun onBindViewHolder(holder: HolderVideo, position: Int) {
        // get data
        val exerciseVideo = videoArrayList!![position]
        // get specific data
        val name: String? = exerciseVideo.name
        val videoUrl = exerciseVideo.url
        // set data
        holder.titleTv.text = name
        setVideoUrl(exerciseVideo, holder)
    }

    private fun setVideoUrl(exerciseVideo: Exercise, holder: HolderVideo) {
        // show progress
        holder.progressBar.visibility = View.VISIBLE
        // get video url
        val videoUrl: String? = exerciseVideo.url
        // MediaController for play/pause
        val mediaController = MediaController(context)
        mediaController.setAnchorView(holder.videoView)
        val videoUri = Uri.parse(videoUrl)

        holder.videoView.setMediaController(mediaController)
        holder.videoView.setVideoURI(videoUri)
        holder.videoView.requestFocus()

        holder.videoView.setOnPreparedListener{ mediaPlayer ->
            // video is prepared to play
            mediaPlayer.start()
        }
        holder.videoView.setOnInfoListener(MediaPlayer.OnInfoListener{mp, what, extra->
            // check if buffering/rendering etc
            when(what) {
                MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START ->{
                    // rendering started
                    holder.progressBar.visibility = View.GONE
                    return@OnInfoListener true
                }
                MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                    // buffering started
                    holder.progressBar.visibility = View.GONE
                    return@OnInfoListener true
                }
                MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                    // buffering ended
                    holder.progressBar.visibility = View.GONE
                    return@OnInfoListener true
                }
            }
            false
        })
        holder.videoView.setOnCompletionListener { mediaPlayer ->
            // restart video when completed | loop video
            mediaPlayer.start()
        }
    }

    override fun getItemCount(): Int {
        return videoArrayList!!.size
    }
}