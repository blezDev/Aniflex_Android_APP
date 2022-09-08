package com.blez.aniplex_clone.Presentation


 import android.os.Bundle
 import android.view.MenuItem
 import androidx.appcompat.app.ActionBarDrawerToggle
 import androidx.appcompat.app.AppCompatActivity
 import androidx.databinding.DataBindingUtil
 import androidx.navigation.fragment.findNavController
 import androidx.navigation.ui.setupWithNavController
 import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
 import com.blez.aniplex_clone.R
 import com.blez.aniplex_clone.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var adapter: RecentAnimeAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.nav_open,R.string.nav_close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        val navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            ?.findNavController()
        if (navController != null) {
            binding.navMenu.setupWithNavController(navController)
        }













        /*val retService = RetrofitInstance.getRetrofitInstance()
            .create(AnimeInterface::class.java)
        val responseLiveData : LiveData<Response<ReleaseAnimes>> = liveData {
            val response = retService.getRecentRelease()
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val releaseAnimesList = it.body()//iterates the list in an proper sequence
            if(releaseAnimesList!= null){
                adapter = RecentAnimeAdapter(this, releaseAnimesList)
                val animeView = findViewById<RecyclerView>(R.id.fragmentContainerView)
                animeView.adapter = adapter
                animeView.layoutManager = LinearLayoutManager(this)
                adapter.onItemClick ={
                    val intent = Intent(this, VideoActivity::class.java)
                    intent.putExtra("episodeId",it?.episodeId)
                    startActivity(intent)
                }

            }

        })*/

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}