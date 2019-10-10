package com.looke.avplayback.playback.presentation

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.looke.avplayback.videos.domain.LookeVideo

class PlaybackActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private val video by lazy {
        intent.getParcelableExtra<LookeVideo>(KEY_VIDEO)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayerStart()
    }

    private fun mediaPlayerStart() {
        mediaPlayer = MediaPlayer.create(this, Uri.parse(video.videoURL))
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
