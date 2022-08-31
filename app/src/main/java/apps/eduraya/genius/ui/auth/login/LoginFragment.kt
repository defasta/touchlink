package apps.eduraya.genius.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import apps.eduraya.genius.*
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.databinding.FragmentLoginBinding
import apps.eduraya.genius.ui.base.BaseFragment
import apps.eduraya.genius.ui.classroom.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding:: inflate
) {
    private val viewModel by viewModels<AuthViewModel>()
    val authentionNavController: NavController? by lazy { view?.findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressbar.visible(false)
        binding.buttonLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
                            it.value.data?.token!!
                        )
                        viewModel.saveUserId(
                            it.value.data.user?.id.toString()
                        )
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                    Log.d("TOKEN USER", it.value.data?.token!!.toString())
                    Log.d("USER INFO", it.value.data.user.toString())
                }
                is Resource.Failure -> handleApiError(it){ login()}
            }
        })

        binding.editTextTextPassword.addTextChangedListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonLogin.setOnClickListener {
            login()
        }

        binding.textViewRegisterNow.setOnClickListener {
            val direction =
                apps.eduraya.genius.ui.auth.login.LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            authentionNavController?.navigate(direction)
        }

//        binding.textViewForgotPassword.setOnClickListener {
//            requireActivity().startAnActivity(RequestResetPasswordActivity::class.java)
//        }

    }

    private fun login(){
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()
        viewModel.login(email, password)
        //requireActivity().startNewActivity(HomeActivity::class.java)
    }

}