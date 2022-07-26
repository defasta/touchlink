package apps.eduraya.edurayaapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import apps.eduraya.edurayaapp.R
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