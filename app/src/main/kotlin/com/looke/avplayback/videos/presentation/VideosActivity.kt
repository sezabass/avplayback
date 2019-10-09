package com.looke.avplayback.videos.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.looke.avplayback.R
import com.looke.avplayback.databinding.ActivityVideosBinding
import com.looke.avplayback.videos.presentation.adapter.VideosAdapter
import com.looke.avplayback.videos.presentation.model.VideoUIModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class VideosActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityVideosBinding
    private val viewModel by lazy {
        ViewModelProviders
            .of(this, viewModelFactory)
            .get(VideosActivityViewModel::class.java)
    }

    private lateinit var listAdapter: VideosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setupBinding()
        setupAdapter()
        setupObservers()

        viewModel.fetchVideos()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_videos)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setupAdapter() {
        listAdapter = VideosAdapter {
            openVideo(it)
        }
        binding.videosList.adapter = listAdapter
    }

    private fun setupObservers() {
        viewModel.videos.observe(this, Observer {
            listAdapter.setData(it.map { video -> VideoUIModel((video)) })
        })
    }

    private fun openVideo(videoName: String) {
        Log.d("Activity", videoName)
    }

}
