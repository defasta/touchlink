package apps.eduraya.e_parking.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.databinding.ActivityHomeBinding
import apps.eduraya.e_parking.startNewActivity
import apps.eduraya.e_parking.ui.scan.ScanQrActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment )
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.menu.getItem(2).isEnabled = false
        binding.bottomNav.background = null
        binding.fab.setOnClickListener {
            startActivity(Intent(this, ScanQrActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

}