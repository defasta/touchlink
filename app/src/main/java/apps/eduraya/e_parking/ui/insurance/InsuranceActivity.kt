package apps.eduraya.e_parking.ui.insurance

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.databinding.ActivityInsuranceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsuranceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsuranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsuranceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userPreferences = UserPreferences(this)
        userPreferences.insuranceDetail.asLiveData().observe(this, Observer {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.htmlInsurance.text =
                    Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
            } else {
                binding.htmlInsurance.text = Html.fromHtml(it)
            }
        })

        binding.navBack.setOnClickListener {
            onBackPressed()
        }

    }


}