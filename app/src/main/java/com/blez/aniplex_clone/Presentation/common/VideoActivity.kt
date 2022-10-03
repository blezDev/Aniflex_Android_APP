package com.blez.aniplex_clone.Presentation.common

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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.databinding.ActivityVideoBinding
import retrofit2.Response


class VideoActivity : AppCompatActivity() {
    val TAG = "VideoActivity"
    lateinit var binding : ActivityVideoBinding
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video)
        Toast.makeText(this, "Rotate Your Phone👌", Toast.LENGTH_LONG).show()

        binding.videoProgess.visibility = View.VISIBLE
        val videoView = findViewById<VideoView>(R.id.videoView)
        val controller = MediaController(this)
        videoView.setMediaController(controller)
        val episodeId = intent.getStringExtra("episodeId")
       val recentAnimeViewModel = ViewModelProvider(this).get(RecentAnimeViewModel::class.java)
        val pathResponse : LiveData<Response<VideoData>> = liveData {
            val response = episodeId?.let { recentAnimeViewModel.retService.getVideoLink(episodeId = it) }
            if (response != null) {
                Log.d("TAG", response.toString())
                emit(response)
            }
            else
            {
                Toast.makeText(this@VideoActivity,"not able to fetch",Toast.LENGTH_LONG).show()

            }
        }
       pathResponse.observe(this,Observer{
           val file = it.body()
           val uri = Uri.parse(file?.sources?.get(0)?.file.toString())
           videoView.setVideoURI(uri)
           videoView.setOnPreparedListener {
               Log.e(TAG, "Changed");
               binding.videoProgess.visibility = View.INVISIBLE
               it.setScreenOnWhilePlaying(true)
               window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

               val videoRatio = it.videoWidth / it.videoHeight.toFloat()
               val screenRatio = videoView.width / videoView.height.toFloat()
               val scaleX = videoRatio / screenRatio
               if (scaleX >= 1f) {
                   videoView.scaleX = scaleX
               } else {
                   videoView.scaleY = 1f / scaleX
               }

           }
           videoView.start()




       })


    }

    }



