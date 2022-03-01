package apps.eduraya.e_parking.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import apps.eduraya.e_parking.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}