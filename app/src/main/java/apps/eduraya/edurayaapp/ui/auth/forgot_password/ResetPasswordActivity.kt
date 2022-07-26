package apps.eduraya.edurayaapp.ui.auth.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.databinding.ActivityResetPasswordBinding
import apps.eduraya.edurayaapp.enable
import apps.eduraya.edurayaapp.handleApiErrorActivity
import apps.eduraya.edurayaapp.ui.auth.AuthActivity
import apps.eduraya.edurayaapp.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {
    private val viewModel by viewModels<ResetPasswordViewModel>()
    private lateinit var binding : ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressbar.visible(false)
        binding.buttonReset.enable(false)

        viewModel.email.observe(this, Observer {
            binding.textView16.text = "Masukkan token yang dikirimkan ke\n$it"
        })

        binding.etNewPasswordC.addTextChangedListener {
            val token = binding.etToken.text.toString().trim()
            val password = binding.etNewPassword.text.toString().trim()
            binding.buttonReset.enable(token.isNotEmpty() && password.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonReset.setOnClickListener {
            val password = binding.etNewPassword.text.toString()
            val passwordC = binding.etNewPasswordC.text.toString()
            if (password != passwordC){
                binding.etNewPasswordC.requestFocus()
                binding.etNewPasswordC.setError("Password tidak valid!")
            }else{
                chagePassword()
            }
        }

        viewModel.resetPasswordResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->
                    lifecycleScope.launch {
                        Toast.makeText(this@ResetPasswordActivity, it.value.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@ResetPasswordActivity, AuthActivity::class.java))
                        finishAffinity()
                    }
                is Resource.Failure -> handleApiErrorActivity(it)
            }
        })

    }

    private fun chagePassword(){
        val token = binding.etToken.text.toString().trim()
        val password = binding.etNewPassword.text.toString().trim()
        val passwordC = binding.etNewPasswordC.text.toString().trim()
        viewModel.email.observe(this, Observer {email->
            viewModel.resetPassword(email, token, password, passwordC)
        })
    }
}