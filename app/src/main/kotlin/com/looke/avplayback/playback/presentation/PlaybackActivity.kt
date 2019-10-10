package com.looke.avplayback.playback.presentation

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.looke.avplayback.R
import com.looke.avplayback.core.extension.hide
import com.looke.avplayback.databinding.ActivityPlaybackBinding
import com.looke.avplayback.videos.domain.LookeVideo
import kotlinx.android.synthetic.main.activity_playback.*

class PlaybackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaybackBinding

    private var audioMediaPlayer: MediaPlayer? = null
    private var videoMediaPlayer: MediaPlayer? = null

    private val video by lazy {
        intent.getParcelableExtra<LookeVideo>(KEY_VIDEO)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        binding.playbackTitle.text = video.name
    }

    override fun onResume() {
        super.onResume()
        setupVideo()
        setupAudio()
    }

    override fun onPause() {
        super.onPause()
        stopVideo()
        stopAudio()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playback)
        binding.lifecycleOwner = this
    }

    private fun setupVideo() {
        vv.setVideoURI(video.videoURL)
        vv.setOnCompletionListener {
            it.start()
        }
        vv.setOnPreparedListener {
            videoMediaPlayer = it
            binding.playbackLoading.hide()
            it.start()
            audioMediaPlayer?.start()
        }
    }

    private fun setupAudio() {
        audioMediaPlayer = MediaPlayer.create(this, video.audioURL)
        audioMediaPlayer?.setOnCompletionListener {
            stopVideo()
            stopAudio()
        }
    }

    private fun stopVideo() {
        vv.stopPlayback()
        videoMediaPlayer?.release()
    }

    private fun stopAudio() {
        audioMediaPlayer?.let{
            it.stop()
            it.release()
        }
    }

    companion object {
        private const val KEY_VIDEO = "KEY_VIDEO"

        @JvmStatic
        internal fun createIntent(context: Context, @NonNull video: LookeVideo): Intent {
            val intent = Intent(context, PlaybackActivity::class.java)
            val extras = Bundle().apply {
                putParcelable(KEY_VIDEO, video)
            }
            intent.putExtras(extras)
            return intent
        }
    }
}
