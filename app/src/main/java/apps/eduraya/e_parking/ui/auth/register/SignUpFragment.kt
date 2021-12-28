package apps.eduraya.e_parking.ui.auth.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.graphics.red
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import apps.eduraya.e_parking.*
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.databinding.FragmentSignUpBinding
import apps.eduraya.e_parking.ui.base.BaseFragment
import apps.eduraya.e_parking.ui.home.HomeActivity
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
//                        viewModel.saveAccessTokens(
//                            it.value.data?.token!!
//                        )
                        Log.d("TOKEN USER", it.value.data?.token.toString())
                        //requireActivity().startNewActivity(HomeActivity::class.java)
                        requireView().snackbar("Berhasil membuat akun!")
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
                binding.editTextTextPasswordC.setError("Invalid password!")
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