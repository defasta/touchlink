package apps.eduraya.e_parking.ui.deposit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.databinding.ActivityPayDepositBinding
import apps.eduraya.e_parking.enable
import apps.eduraya.e_parking.ui.home.HomeActivity
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class PayDepositActivity : AppCompatActivity() {
    private val viewModel by viewModels<PayDepositViewModel>()
    private lateinit var binding: ActivityPayDepositBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayDepositBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressbar.visible(false)
        binding.ivStatus.visible(false)

        val userPreferences = UserPreferences(this)

        viewModel.statusDeposit.observe(this, Observer {
            if (it == "PENDING"){
                binding.ivStatus.setImageDrawable(getDrawable(R.drawable.ic_baseline_pending_24))
                binding.ivStatus.visible(true)
            }else if (it == "SUCCESS"){
                binding.ivStatus.setImageDrawable(getDrawable(R.drawable.ic_baseline_done_24))
                binding.ivStatus.visible(true)
                binding.buttonConfirm.visible(false)
            }else if (it == "EXPIRED"){
                binding.ivStatus.setImageDrawable(getDrawable(R.drawable.ic_baseline_warning_24))
                binding.ivStatus.visible(true)
                binding.buttonConfirm.enable(false)
            }
            binding.tvStatus.text = it.toString()

        })
        viewModel.amountDeposit.observe(this, Observer {
            binding.tvNominal.text = "Rp. $it"
        })

        viewModel.payDepositResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {
                        startActivity(Intent(this@PayDepositActivity, PaymentWebViewActivity::class.java).apply {
                            putExtra("KEY_TOKEN", it.value.data?.token.toString())
                        })
                    }
                }
                is Resource.Failure -> Toast.makeText(this, "Gagal melakukan transaksi pembayaran", Toast.LENGTH_SHORT).show()
            }
        })

        binding.buttonConfirm.setOnClickListener {
            userPreferences.accessToken.asLiveData().observe(this, Observer {
                viewModel.idDeposit.observe(this, Observer { id ->
                    viewModel.payDeposit("Bearer $it",id)
                })
            })
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
        super.onBackPressed()
    }

}