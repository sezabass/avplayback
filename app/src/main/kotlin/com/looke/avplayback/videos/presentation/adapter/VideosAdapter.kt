package com.looke.avplayback.videos.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.looke.avplayback.R
import com.looke.avplayback.databinding.ListItemVideosBinding
import com.looke.avplayback.videos.presentation.model.VideoUIModel

internal class VideosAdapter(
    private val rowClickCallback: (index: Int) -> Unit
) : RecyclerView.Adapter<VideosAdapter.VideosViewHolder>() {

    private val videos = mutableListOf<VideoUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemVideosBinding>(
            layoutInflater, R.layout.list_item_videos, parent, false
        )
        return VideosViewHolder(binding)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    inner class VideosViewHolder(private val binding: ListItemVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: VideoUIModel) {
            binding.video = data
            binding.videoItem.setOnClickListener { rowClickCallback(adapterPosition) }
        }
    }

    fun setData(newData: List<VideoUIModel>) {
        videos.clear()
        videos.addAll(newData)
        notifyDataSetChanged()
    }
}
