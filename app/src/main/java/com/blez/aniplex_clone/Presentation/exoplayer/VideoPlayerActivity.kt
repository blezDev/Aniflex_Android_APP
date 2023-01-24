package com.blez.aniplex_clone.Presentation.exoplayer

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.databinding.ActivityVideoPlayerExoBinding
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class VideoPlayerActivity : AppCompatActivity() {
    private val channel = Channel<Int>()
    private var player : ExoPlayer? = null
    private lateinit var binding : ActivityVideoPlayerExoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_player_exo)


    }




    private fun inititalizePlayer(){
        player = ExoPlayer.Builder(this)
            .build()
            .also {
                val mediaItem = createMediaItem(uri = Uri.parse("https://www11x.gofcdn.com/videos/hls/0E0-AhPe_bsxiDzifRd-3Q/1673051579/25615/027e9529af2b06fe7b4f47e507a787eb/ep.220.1656255387.m3u8"), videoData = null)
                it.setMediaItem(mediaItem)
                binding.exoPlayerPlayer.player = it
            }
            }




    }
  private  fun createMediaItem(videoData: VideoData?,uri: Uri ): MediaItem {
   /* val file = videoData
    val uri = Uri.parse(file?.sources?.get(0)?.file.toString())*/
        return MediaItem.Builder()
            .setUri(uri)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()
    }

    private fun getUriMimeType(uri: Uri): String {
        return when (val type = Util.inferContentType(uri)) {
            C.CONTENT_TYPE_HLS  -> {
                MimeTypes.APPLICATION_M3U8
            }
            C. CONTENT_TYPE_DASH -> {
                MimeTypes.APPLICATION_MPD
            }
            C. CONTENT_TYPE_SS  -> {
                MimeTypes.APPLICATION_SS
            }
            C.CONTENT_TYPE_OTHER  -> {
                MimeTypes.APPLICATION_MP4
            }
            else -> {
                throw IllegalStateException("Unsupported type: $type")
            }
        }

}