package com.blez.aniplex_clone.Presentation.exoplayer

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.data.VideoFormat
import com.blez.aniplex_clone.databinding.ActivityVideoPlayerExoBinding
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.RendererCapabilities
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverride
import com.google.android.exoplayer2.ui.TrackSelectionView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private lateinit var binding: ActivityVideoPlayerExoBinding
    private lateinit var exoViewModel: ExoViewModel
    private val isPlaying get() = player?.isPlaying ?: false
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player_exo)
        exoViewModel = ViewModelProvider(this)[ExoViewModel::class.java]
        val episodeId = intent.getStringExtra("episodeId") ?: ""
        supportActionBar?.hide()
        exoViewModel.getVideoData(episodeId)
        subscribeToUI()
    }

    private fun subscribeToUI() {
        lifecycleScope.launch(Dispatchers.Main) {
            exoViewModel.videoFlow.collect { events ->
                when (events) {
                    is ExoViewModel.SetupEventForVideo.VideoLinkLoading -> {
                        binding.videoProgressBar.isVisible = true
                    }

                    is ExoViewModel.SetupEventForVideo.VideoLink -> {

                        binding.videoProgressBar.isVisible = false
                        player?.release()
                        /*   videoPlayerInitalize(events.data)*/
                        try {
                            videoPlayerInitalize(events.data)

                        } catch (e: Exception) {


                            Toast.makeText(
                                this@VideoPlayerActivity,
                                e.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        hideSystemUi()
                    }

                    is ExoViewModel.SetupEventForVideo.Failure -> {

                        Toast.makeText(
                            this@VideoPlayerActivity,
                            events.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }

            }
        }

    }


    private fun videoPlayerInitalize(data: VideoFormat) {
        try {
            inititalizePlayer(data, 0)
        } catch (e: Exception) {
            player?.release()
            inititalizePlayer(data, 1)
            Toast.makeText(this@VideoPlayerActivity, "Switching Source Type", Toast.LENGTH_SHORT)
                .show()
        }

        hideSystemUi()
    }


    private fun inititalizePlayer(data: VideoFormat, sourceType: Int) {


        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            .setUserAgent(getString(R.string.app_name))
            .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
            .setReadTimeoutMs(1800000)
            .setAllowCrossProtocolRedirects(true)

        dataSourceFactory.createDataSource()
        val trackSelection = DefaultTrackSelector(this)
        trackSelection.setParameters(
            trackSelection.buildUponParameters()
                .setPreferredAudioLanguage(null)
                .build()
        )
        val qualityUrls = data.sources.map { it.url }
        val qualityLabels = data.sources.map { it.quality }
        val qualityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, qualityLabels)
        qualityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.qualitySpinner.adapter = qualityAdapter

        binding.qualitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedQualityUrl = qualityUrls[position]
                val mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(selectedQualityUrl))
                player?.release()
                player = ExoPlayer.Builder(this@VideoPlayerActivity)
                    .setTrackSelector(trackSelection)
                    .build()
                    .also { exoPlayer ->
                        exoPlayer.setMediaSource(mediaSource)
                        exoPlayer.prepare()
                        exoPlayer.seekTo(0, 0L)
                        exoPlayer.playWhenReady = true
                        binding.exoPlayerPlayer.player = exoPlayer
                        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        /*   Util.getUserAgent(this,"Exo Player"),null,
           DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,1800000,
           true*/


 /*       val hlsMediaSource = if (sourceType == 0) HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(data.sources.get(3).url)) else HlsMediaSource.Factory(
            dataSourceFactory
        ).createMediaSource(MediaItem.fromUri(data.sources.get(4).url))*/


    }


    override fun onStart() {
        super.onStart()
        Log.e("TAG", "onStart is called from exoplayer Fragment")
    }

    override fun onResume() {
        super.onResume()
        player?.play()
        Log.e("TAG", "onResume is called from exoplayer Fragment")
    }

    override fun onPause() {
        super.onPause()

        Log.e("TAG", "OnPause is called from exoplayer Fragment")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("TAG", "OnRestart is called from exoplayer Fragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        Log.e("TAG", "OnDestroy is called from exoplayer Fragment")
    }


    override fun onStop() {
        Log.e("TAG", "OnStop is called from exoplayer Fragment")
        player?.release()
        super.onStop()
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.exoPlayerPlayer).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }


    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player?.playWhenReady!!
            playbackPosition = player?.currentPosition!!
            player = null
        }
    }

    private fun createMediaItem(videoData: VideoData?, uri: String): MediaItem {
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
        C.CONTENT_TYPE_HLS -> {
            MimeTypes.APPLICATION_M3U8
        }

        C.CONTENT_TYPE_DASH -> {
            MimeTypes.APPLICATION_MPD
        }

        C.CONTENT_TYPE_SS -> {
            MimeTypes.APPLICATION_SS
        }

        C.CONTENT_TYPE_OTHER -> {
            MimeTypes.APPLICATION_MP4
        }

        else -> {
            throw IllegalStateException("Unsupported type: $type")
        }
    }

}