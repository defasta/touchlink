package apps.eduraya.genius.ui.classroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import apps.eduraya.genius.R
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.databinding.ActivityHomeBinding
import apps.eduraya.genius.startNewActivity
import apps.eduraya.genius.ui.auth.AuthActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var alertBuilder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.actionDashboard, R.id.actionHome, R.id.actionProfile), binding.drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        alertBuilder = AlertDialog.Builder(this)

        val userPreferences = UserPreferences(this)
        userPreferences.accessToken.asLiveData().observe(this, androidx.lifecycle.Observer { token ->
            userPreferences.userId.asLiveData().observe(this, Observer { userId ->
                if(userId != null){
                    viewModel.getUserInfo("Bearer $token", userId!!)
                }
            })
        })

        viewModel.userInfoResponse.observe(this, Observer {
            //binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Loading ->{}
                is Resource.Success -> {
                    lifecycleScope.launch {
                        if (it.value.data?.photoUrl != null){
                            Glide.with(this@HomeActivity).load(it.value.data.photoUrl).into(binding.drawerLayout.findViewById(R.id.headerImage))
                        }
                        binding.drawerLayout.findViewById<TextView>(R.id.tv_name).text = it.value.data?.name.toString()
                    }
                    Log.d("USER INFO", it.value.data.toString())
                }
                is Resource.Failure -> Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.logoutResponse.observe(this, Observer {
            when(it){
                is Resource.Loading -> {}
                is Resource.Success ->
                    lifecycleScope.launch {
                        startNewActivity(AuthActivity::class.java)
                    }
                is Resource.Failure -> Toast.makeText(this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show()

            }
        })
        binding.navView.menu.findItem(R.id.actionLogout).setOnMenuItemClickListener {
//            val userPreferences = UserPreferences(this)
//            userPreferences.accessToken.asLiveData().observe(this, Observer {token->
//
//            })
            alertBuilder.setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin melakukan Logout?")
                .setCancelable(true)
                .setPositiveButton("Ya"){dialogInterface, it ->
                    lifecycleScope.launch{
                        viewModel.logoutAccount()
                        startNewActivity(AuthActivity::class.java)
                    }
                }
                .setNegativeButton("Tidak"){dialogInterface, it ->

                }
                .show()
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer_list, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}