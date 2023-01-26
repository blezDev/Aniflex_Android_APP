package com.blez.aniplex_clone.Presentation


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var adapter: RecentAnimeAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.recent_release)


   /*     binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.action_recentReleaseFragment_to_searchFragment)
            navController.navigate(R.id.action_movieFragment_to_searchFragment)
            navController.navigate(R.id.action_topAiringFragment_to_searchFragment)
            navController.navigate(R.id.action_popularAnimeFragment_to_searchFragment)
            navController.navigate(R.id.action_recentReleaseFragment_to_searchFragment)
        }*/

        navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            ?.findNavController()!!

        binding.navMenu.setupWithNavController(navController)
        binding.navMenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.recentReleaseFragment -> {
                    navController?.navigate(R.id.recentReleaseFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    supportActionBar?.setTitle(R.string.recent_release)
                }
                R.id.popularAnimeFragment -> {
                    navController?.navigate(R.id.popularAnimeFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    supportActionBar?.setTitle(R.string.popular_anime)
                }
                R.id.movieFragment -> {
                    navController?.navigate(R.id.movieFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    supportActionBar?.setTitle(R.string.upcoming_movies)
                }
                R.id.topAiringFragment -> {
                    navController?.navigate(R.id.topAiringFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    supportActionBar?.setTitle(R.string.top_airing)
                }
                R.id.settingsFragment -> {
                    navController?.navigate(R.id.settingsFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    supportActionBar?.setTitle(R.string.settings)
                }

            }
            true
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.detailsFragment -> {
                    supportActionBar?.hide()

                }
                R.id.searchFragment->{
                    supportActionBar?.hide()
                }


                else -> {

                    supportActionBar?.show()
                }

            }
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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    /* fun isDarkMode(context: Context): Boolean {
         val darkModeFlag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
         return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
     }*/


}