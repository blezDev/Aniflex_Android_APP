package com.blez.aniplex_clone.model.common

import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.`interface`.AnimeInterface
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.network.RetrofitInstance
import retrofit2.Response


class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val videoView = findViewById<VideoView>(R.id.videoView)
        val controller = MediaController(this)
        videoView.setMediaController(controller)
        val episodeId = intent.getStringExtra("episodeId")
        val retService = RetrofitInstance.getRetrofitInstance()
            .create(AnimeInterface::class.java)
        val pathResponse : LiveData<Response<VideoData>> = liveData {
            val response = episodeId?.let { retService.getVideoLink(episodeId = it) }
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
           videoView.start()



       })


    }

}