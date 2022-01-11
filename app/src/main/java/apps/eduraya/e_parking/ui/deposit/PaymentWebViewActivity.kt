package apps.eduraya.e_parking.ui.deposit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.databinding.ActivityPaymentWebViewBinding
import apps.eduraya.e_parking.ui.home.HomeActivity
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentWebViewActivity : AppCompatActivity() {
    private val viewModel by viewModels<PaymentWebViewViewModel>()
    private lateinit var binding: ActivityPaymentWebViewBinding
    private lateinit var webview: WebView
    private lateinit var alertBuilder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentWebViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        alertBuilder = AlertDialog.Builder(this)
        webview = binding.wv

        viewModel.tokenDeposit.observe(this, Observer {
            webview.webViewClient = WebViewClient()
            webview.loadUrl("https://api.e-parkingjogja.com/api/midtrans-snap/$it")
            webview.settings.javaScriptEnabled = true
            webview.settings.domStorageEnabled = true
        })

        binding.home.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }
    }

    override fun onBackPressed() {
//        if (webview.canGoBack()) {
//            webview.goBack()
//        }else {
            alertBuilder.setTitle("Konfirmasi Pembayaran")
                .setMessage("Akhiri pembayaran?")
                .setCancelable(true)
                .setPositiveButton("Ya"){dialogInterface, it ->
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                }
                .setNegativeButton("Tidak"){dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }
//    }
}