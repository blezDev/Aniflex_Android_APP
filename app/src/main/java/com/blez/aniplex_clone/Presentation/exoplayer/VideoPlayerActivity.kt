package com.blez.aniplex_clone.Presentation.exoplayer

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.databinding.ActivityVideoPlayerExoBinding
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {

    private var player : ExoPlayer? = null
    private lateinit var binding : ActivityVideoPlayerExoBinding
    private lateinit var exoViewModel: ExoViewModel

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_player_exo)
        exoViewModel = ViewModelProvider(this)[ExoViewModel::class.java]
        val episodeId  = intent.getStringExtra("episodeId") ?: ""
        supportActionBar?.hide()

        CoroutineScope(Dispatchers.Main).launch {
            val data = exoViewModel.getVideoData(episodeId).await()

            try {
                inititalizePlayer(data!!)

            }catch (e:Exception){
                Handler(Looper.getMainLooper()).post {
                    // write your code here
                    Toast.makeText(this@VideoPlayerActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
                }

            }

            hideSystemUi()
        }


    }




    private fun inititalizePlayer(data : VideoData) {

        val dataSourceFactory : DataSource.Factory = DefaultHttpDataSource.Factory()
            .setUserAgent(getString(R.string.app_name))
            .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
            .setReadTimeoutMs(1800000)
            .setAllowCrossProtocolRedirects(true)


     /*   Util.getUserAgent(this,"Exo Player"),null,
        DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,1800000,
        true*/

        dataSourceFactory.createDataSource()
        val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(data.sources_bk.get(0).file))


        player = ExoPlayer.Builder(this@VideoPlayerActivity)

            .build()
            .also { exoPlayer ->
                exoPlayer.setMediaSource(hlsMediaSource)
                binding.exoPlayerPlayer.player = exoPlayer
                exoPlayer.seekTo(playbackPosition)
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }

    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG","onStart is called from exoplayer Fragment")
    }

    override fun onResume() {
        super.onResume()
    player?.play()
        Log.e("TAG","onResume is called from exoplayer Fragment")
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
        Log.e("TAG","OnPause is called from exoplayer Fragment")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("TAG","OnRestart is called from exoplayer Fragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        Log.e("TAG","OnDestroy is called from exoplayer Fragment")
    }


    override fun onStop() {
        Log.e("TAG","OnStop is called from exoplayer Fragment")
       player?.pause()



        super.onStop()
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window,binding.exoPlayerPlayer ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


        }


    }
    private fun releasePlayer()
    {
        if(player!= null){
            playWhenReady = player?.playWhenReady!!
            playbackPosition = player?.currentPosition!!
            player = null
        }
    }

  private  fun createMediaItem(videoData: VideoData?,uri: String ): MediaItem {
   /* val file = videoData
    val uri = Uri.parse(file?.sources?.get(0)?.file.toString())*/
        return MediaItem.Builder()
            .setUri(uri)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()
    }

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