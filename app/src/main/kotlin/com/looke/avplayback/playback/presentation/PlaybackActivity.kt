package com.looke.avplayback.playback.presentation

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.looke.avplayback.R
import com.looke.avplayback.databinding.ActivityPlaybackBinding
import com.looke.avplayback.videos.domain.LookeVideo
import kotlinx.android.synthetic.main.activity_playback.*

class PlaybackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaybackBinding

    private var mediaPlayer: MediaPlayer? = null
    private val video by lazy {
        intent.getParcelableExtra<LookeVideo>(KEY_VIDEO)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        binding.playbackTitle.text = video.name

        setupVideo()
        setupAudio()
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
            it.start()
        }
    }

    private fun setupAudio() {
        mediaPlayer = MediaPlayer.create(this, video.audioURL)
        mediaPlayer?.setOnCompletionListener {
            vv.stopPlayback()
        }
        mediaPlayer?.start()
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
