package apps.eduraya.edurayaapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.edurayaapp.data.db.UserPreferences
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.databinding.FragmentProfileBinding
import apps.eduraya.edurayaapp.handleApiError
import apps.eduraya.edurayaapp.ui.auth.AuthActivity
import apps.eduraya.edurayaapp.ui.base.BaseFragment
import apps.eduraya.edurayaapp.ui.home.HomeViewModel
import apps.eduraya.edurayaapp.visible
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPreferences = UserPreferences(requireContext())
        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
            viewModel.getUserInfo("Bearer $token")
            viewModel.userInfoResponse.observe(viewLifecycleOwner, Observer {
                binding.progressbar.visible(it is Resource.Loading)
                when(it){
                    is Resource.Success -> {
                        lifecycleScope.launch {
                            if (it.value.data?.user?.avatar != null){
                                Glide.with(requireContext()).load(it.value.data?.user?.avatar).into(binding.imgProfile)
                            }
                            binding.tvUsername.text = it.value.data?.user?.name
                            binding.tvEmail.text = it.value.data?.user?.email
                            binding.tvPhone.text = it.value.data?.user?.phone
                            binding.ivEditProfile.setOnClickListener {view ->
                                requireActivity().startActivity(Intent(context,EditProfileActivity::class.java).apply {
                                    putExtra("KEY_NAME_USER", it.value.data?.user?.name)
                                    putExtra("KEY_EMAIL_USER", it.value.data?.user?.email)
                                    putExtra("KEY_PHONE_USER", it.value.data?.user?.phone.toString() )
                                    putExtra("KEY_AVATAR_USER", it.value.data?.user?.avatar)
                                })

                            }
                        }
                    }
                    is Resource.Failure -> handleApiError(it)

                }
            })
        })


        binding.cvLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logoutAccount()
                requireActivity().startActivity(Intent(context, AuthActivity::class.java))
                requireActivity().finishAffinity()
            }
        }
    }

}