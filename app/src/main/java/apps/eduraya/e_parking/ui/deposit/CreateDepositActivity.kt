package apps.eduraya.e_parking.ui.deposit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.databinding.ActivityCreateDepositBinding
import apps.eduraya.e_parking.enable
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateDepositActivity : AppCompatActivity() {
    private val viewModelCreateDeposit by viewModels<CreateDepositViewModel>()
    private lateinit var binding: ActivityCreateDepositBinding
    private lateinit var alertBuilder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateDepositBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        alertBuilder = AlertDialog.Builder(this)

        binding.progressbar.visible(false)
        binding.buttonProceed.enable(false)


        viewModelCreateDeposit.createDepositResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {
                        startActivity(Intent(this@CreateDepositActivity, PayDepositActivity::class.java).apply {
                            putExtra("KEY_ID", it.value.data?.id.toString())
                            putExtra("KEY_AMOUNT", it.value.data?.amount)
                            putExtra("KEY_STATUS", it.value.data?.status)
                        })
                        finishAndRemoveTask()
                    }
                }
                is Resource.Failure -> Toast.makeText(this, "Gagal melakukan transaksi", Toast.LENGTH_SHORT).show()
            }

        })


        binding.editTextTextDeposit.addTextChangedListener {
            binding.buttonProceed.enable(it.toString().isNotEmpty())
        }

        binding.buttonProceed.setOnClickListener {
            val nominal = binding.editTextTextDeposit.text.toString().toInt()
            if(nominal < 10000){
                binding.editTextTextDeposit.requestFocus()
                binding.editTextTextDeposit.setError("Minimal deposit tidak terpenuhi!")
            }else confirmDeposit(nominal)
        }
    }

    private fun confirmDeposit(nominal: Int){
        val nominalString= nominal.toString()
        alertBuilder.setTitle("Konfirmasi Deposit")
            .setMessage("Anda akan melakukan deposit sebesar Rp. $nominalString ")
            .setCancelable(true)
            .setPositiveButton("Lanjutkan"){dialogInterface, it ->
                val userPreferences = UserPreferences(this)
                val nominalConfirmed = nominalString.toInt()

                userPreferences.accessToken.asLiveData().observe(this, Observer {
                    viewModelCreateDeposit.createDeposit("Bearer $it",nominalString)
                })
            }
            .setNegativeButton("Kembali"){dialogInterface, it ->
                dialogInterface.cancel()
            }
            .show()
    }
}