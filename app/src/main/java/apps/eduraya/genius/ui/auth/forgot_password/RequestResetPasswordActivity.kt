package apps.eduraya.genius.ui.auth.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.databinding.ActivityRequestResetPasswordBinding
import apps.eduraya.genius.enable
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RequestResetPasswordActivity : AppCompatActivity() {
    private val viewModel by viewModels<RequestResetPasswordViewModel>()
    private lateinit var binding: ActivityRequestResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestResetPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressbar.visible(false)
        binding.buttonRequestReset.enable(false)

        binding.etEmailAddress.addTextChangedListener {
            binding.buttonRequestReset.enable(it.toString().isNotEmpty())
        }

        binding.buttonRequestReset.setOnClickListener {
            val email = binding.etEmailAddress.text.toString().trim()
            viewModel.requestResetPassword(email)
        }

        binding.navBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.requestResetPasswordResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {
                        Toast.makeText(this@RequestResetPasswordActivity, it.value.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RequestResetPasswordActivity, ResetPasswordActivity::class.java).apply {
                            putExtra("KEY_EMAIL", binding.etEmailAddress.text.toString().trim())
                        })
                        finishAffinity()
                    }
                }
//                is Resource.Failure -> handleApiErrorActivity(it)
                is Resource.Failure -> {
                    if (it.isNetworkError){
                        Snackbar.make(view, "Mohon cek koneksi internet anda", Snackbar.LENGTH_LONG).show()
                    }else{
                        Snackbar.make(view, "Gagal memuat data", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })
    }


}