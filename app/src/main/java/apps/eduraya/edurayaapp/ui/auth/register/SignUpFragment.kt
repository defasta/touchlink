package apps.eduraya.edurayaapp.ui.auth.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import apps.eduraya.edurayaapp.*
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.databinding.FragmentSignUpBinding
import apps.eduraya.edurayaapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {
    private val viewModel by viewModels<SignUpViewModel>()
    val authentionNavController: NavController? by lazy { view?.findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.progressbar.visible(false)
        binding.buttonRegister.enable(false)

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {
                        Log.d("TOKEN USER", it.value.data?.token.toString())
                        requireView().snackbar("Buat akun berhasil! Silakan melakukan Login untuk melanjutkan")
                        val direction = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                        authentionNavController?.navigate(direction)
                    }
                }
                is Resource.Failure -> handleApiError(it){signUp()}
            }
        })

        binding.editTextTextPasswordC.addTextChangedListener {
            val name = binding.editTextTextPersonName.text.toString().trim()
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()
            binding.buttonRegister.enable(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonRegister.setOnClickListener {
            val password = binding.editTextTextPassword.text.toString()
            val passwordC = binding.editTextTextPasswordC.text.toString()
            if (password != passwordC){
                binding.editTextTextPasswordC.requestFocus()
                binding.editTextTextPasswordC.setError("Password tidak valid!")
            } else signUp()
        }

        binding.textViewSignUpNow.setOnClickListener{
            val direction = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            authentionNavController?.navigate(direction)
        }

    }

    private fun signUp(){
        val name = binding.editTextTextPersonName.text.toString().trim()
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()
        val passwordC = binding.editTextTextPasswordC.text.toString().trim()
        viewModel.signup(name,email, password, passwordC)
    }

}