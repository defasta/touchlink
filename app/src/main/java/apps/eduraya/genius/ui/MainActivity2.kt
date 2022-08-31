package apps.eduraya.genius.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import apps.eduraya.genius.R
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.startNewActivity
import apps.eduraya.genius.ui.auth.AuthActivity
import apps.eduraya.genius.ui.classroom.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
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