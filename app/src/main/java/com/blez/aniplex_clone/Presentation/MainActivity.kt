package com.blez.aniplex_clone.Presentation


 import android.annotation.SuppressLint
 import android.os.Bundle
 import android.util.Log
 import android.view.Menu
 import android.view.MenuItem
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


class MainActivity : AppCompatActivity() {
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var adapter: RecentAnimeAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.nav_open,R.string.nav_close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.findNavController()!!

            binding.navMenu.setupWithNavController(navController)
        binding.navMenu.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.recentReleaseFragment->{
                    navController?.navigate(R.id.recentReleaseFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.popularAnimeFragment->{
                    navController?.navigate(R.id.popularAnimeFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.movieFragment->{
                    navController?.navigate(R.id.movieFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.topAiringFragment->{
                    navController?.navigate(R.id.topAiringFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.settingsFragment->{
                    navController?.navigate(R.id.settingsFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

            }
            true
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_option,menu)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        val menuItem = menu?.findItem(R.id.app_bar_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.queryHint = "Search Your Query"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val bundle = Bundle()
                bundle.putString("animeQuery",query)

                navController.navigate(R.id.searchFragment,bundle)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val bundle = Bundle()
                bundle.putString("animeQuery",newText)

                navController.navigate(R.id.searchFragment,bundle)
               return false
            }
        })

        searchView.setOnSearchClickListener {

            Log.e("TAG",it.toString())
        navController.navigate(R.id.searchFragment)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}