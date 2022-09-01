package apps.eduraya.genius.ui.auth.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.databinding.ActivityResetPasswordBinding
import apps.eduraya.genius.enable
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.startNewActivity
import apps.eduraya.genius.ui.auth.AuthActivity
import apps.eduraya.genius.visible
import com.google.android.material.snackbar.Snackbar
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

        binding.etNewPasswordC.addTextChangedListener {
            val password = binding.etNewPassword.text.toString().trim()
            binding.buttonReset.enable(password.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonReset.setOnClickListener {
                changePassword()
        }

        binding.navBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.resetPasswordResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->
                    lifecycleScope.launch {
                        Toast.makeText(this@ResetPasswordActivity, "Berhasil mengubah password", Toast.LENGTH_SHORT).show()
                        onBackPressed()
                    }
                is Resource.Failure -> {
                    if(it.isNetworkError){
                        view.snackbar("Mohon cek koneksi internet Anda!")
                    }else{
                        view.snackbar("Password lama salah!")
                    }
                }
            }
        })
    }

    private fun changePassword(){
        val oldPassword = binding.etNewPassword.text.toString().trim()
        val newPassword = binding.etNewPasswordC.text.toString().trim()
        val userPreferences = UserPreferences(this)
        userPreferences.accessToken.asLiveData().observe(this, androidx.lifecycle.Observer { token ->
            userPreferences.userId.asLiveData().observe(this, Observer { userId ->
                if(userId != null){
                    viewModel.resetPassword("Bearer $token", userId, oldPassword, newPassword)
                }
            })
        })

    }
}