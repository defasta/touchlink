package apps.eduraya.e_parking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import apps.eduraya.e_parking.data.UserPreferences
import apps.eduraya.e_parking.ui.auth.AuthActivity
import apps.eduraya.e_parking.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)

        userPreferences.accessToken.asLiveData().observe(this, Observer {
           if (it == null) {
                Handler().postDelayed({
                    val i = Intent(this, AuthActivity::class.java)
                    startActivity(i)
                }, 5000)
            } else startNewActivity(HomeActivity::class.java)
        })
    }
}