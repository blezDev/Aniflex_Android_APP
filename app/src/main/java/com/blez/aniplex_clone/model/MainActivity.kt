package com.blez.aniplex_clone.model


 import android.content.Intent
 import android.os.Bundle
 import androidx.appcompat.app.AppCompatActivity
 import androidx.lifecycle.LiveData
 import androidx.lifecycle.Observer
 import androidx.lifecycle.liveData
 import androidx.recyclerview.widget.GridLayoutManager
 import androidx.recyclerview.widget.RecyclerView
 import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
 import com.blez.aniplex_clone.R
 import com.blez.aniplex_clone.`interface`.AnimeInterface
 import com.blez.aniplex_clone.data.ReleaseAnimes
 import com.blez.aniplex_clone.network.RetrofitInstance
 import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RecentAnimeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retService = RetrofitInstance.getRetrofitInstance()
            .create(AnimeInterface::class.java)
        val responseLiveData : LiveData<Response<ReleaseAnimes>> = liveData {
            val response = retService.getRecentRelease()
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val releaseAnimesList = it.body()//iterates the list in an proper sequence
            if(releaseAnimesList!= null){
                adapter = RecentAnimeAdapter(this, releaseAnimesList)
                val animeView = findViewById<RecyclerView>(R.id.animeView)
                animeView.adapter = adapter
                animeView.layoutManager = GridLayoutManager(this,1)
                adapter.onItemClick ={
                    val intent = Intent(this,VideoActivity::class.java)
                    intent.putExtra("episodeId",it?.episodeId)
                    startActivity(intent)

                }

            }

        })
    }
}