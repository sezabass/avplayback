package com.looke.avplayback.videos.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.looke.avplayback.R
import com.looke.avplayback.core.domain.ScreenState
import com.looke.avplayback.core.extension.showSnackBar
import com.looke.avplayback.databinding.ActivityVideosBinding
import com.looke.avplayback.playback.presentation.PlaybackActivity
import com.looke.avplayback.videos.domain.LookeVideo
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
        listAdapter = VideosAdapter { index ->
            viewModel.onUserRequestedToOpenVideo(index)
        }
        binding.videosList.adapter = listAdapter
    }

    private fun setupObservers() {
        viewModel.videosListState.observe(this, Observer { state ->
            when (state) {
                is ScreenState.Success -> listAdapter.setData(state.data.map { video ->
                    VideoUIModel((video))
                })
                is ScreenState.Error -> binding.root.showSnackBar(
                    getString(R.string.videos_load_error), Snackbar.LENGTH_LONG
                )
            }
        })
        viewModel.openVideo.observe(this, Observer {
            openPlaybackScreen(it)
        })
    }

    private fun openPlaybackScreen(video: LookeVideo) {
        val intent = PlaybackActivity.createIntent(this, video)
        startActivity(intent)
    }

}
