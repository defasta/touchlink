package apps.eduraya.e_parking.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.startNewActivity
import apps.eduraya.e_parking.ui.auth.AuthActivity
import apps.eduraya.e_parking.ui.home.HomeActivity
import apps.eduraya.e_parking.ui.maps.MapsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)

        userPreferences.accessToken.asLiveData().observe(this, Observer {token ->
           if (token == null) {
                Handler().postDelayed({
                    val i = Intent(this, AuthActivity::class.java)
                    startActivity(i)
                }, 5000)
            } else {
                startNewActivity(HomeActivity::class.java)
            }
        })
    }
}