package com.blez.aniplex_clone.Presentation.common

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.ActivityVideoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class VideoActivity : AppCompatActivity() {
    val TAG = "VideoActivity"
    lateinit var binding : ActivityVideoBinding
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video)
        Toast.makeText(this, "Rotate Your Phone👌", Toast.LENGTH_LONG).show()
        hideSystemUi()
        binding.videoProgess.visibility = View.VISIBLE
        val videoView = findViewById<VideoView>(R.id.videoView)
        val controller = MediaController(this)
        videoView.setMediaController(controller)
        val episodeId = intent.getStringExtra("episodeId")
       val recentAnimeViewModel = ViewModelProvider(this).get(RecentAnimeViewModel::class.java)
        CoroutineScope(Dispatchers.Main).async { val it = recentAnimeViewModel.getVideoLink(episodeId!!).await()

            val file = it
            val uri = Uri.parse(file?.sources?.get(0)?.file.toString())
            videoView.setVideoURI(uri)
            videoView.setOnPreparedListener {
                Log.e(TAG, "Changed");
                binding.videoProgess.visibility = View.INVISIBLE
                it.setScreenOnWhilePlaying(true)
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                hideSystemUi()

            }
            return@async


              /* val videoRatio = it.videoWidth / it.videoHeight.toFloat()
               val screenRatio = videoView.width / videoView.height.toFloat()
               val scaleX = videoRatio / screenRatio
               if (scaleX >= 1f) {
                   videoView.scaleX = scaleX
               } else {
                   videoView.scaleY = 1f / scaleX
               }*/

           }
           videoView.start()







    }
    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


        }
    }

    }



