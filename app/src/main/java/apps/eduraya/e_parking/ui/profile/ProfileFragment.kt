package apps.eduraya.e_parking.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.databinding.FragmentProfileBinding
import apps.eduraya.e_parking.startAnActivity
import apps.eduraya.e_parking.ui.MainActivity
import apps.eduraya.e_parking.ui.base.BaseFragment
import apps.eduraya.e_parking.ui.home.HomeViewModel
import apps.eduraya.e_parking.ui.my_qr.MyQrActivity
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getUserInfoDBObserver().observe(this,object : Observer<List<UserInfo>> {
//            override fun onChanged(t: List<UserInfo>?) {
//                t?.forEach {
//                    binding.tvUsername.text = it.name.toString()
//                    binding.tvEmail.text = it.email.toString()
//                    binding.tvPhone.text = it.phone.toString()
//                }
//            }
//        })

        val userPreferences = UserPreferences(context!!)
        userPreferences.accessToken.asLiveData().observe(this, androidx.lifecycle.Observer { token ->
            viewModel.setUserInfoResponse("Bearer $token")
            viewModel.getUserInfoResponse.observe(this, Observer {
                binding.progressbar.visible(it is Resource.Loading)
                when(it){
                    is Resource.Success -> {
                        lifecycleScope.launch {
                            binding.tvUsername.text = it.value.data?.user?.name
                            binding.tvEmail.text = it.value.data?.user?.email
                            binding.tvPhone.text = it.value.data?.user?.phone
                            binding.ivEditProfile.setOnClickListener {view ->
                                requireActivity().startActivity(Intent(context,EditProfileActivity::class.java).apply {
                                    putExtra("KEY_NAME_USER", it.value.data?.user?.name)
                                    putExtra("KEY_EMAIL_USER", it.value.data?.user?.email)
                                    putExtra("KEY_PHONE_USER", it.value.data?.user?.phone.toString() )
                                })

                            }
                        }
                    }
                    is Resource.Failure -> Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()

                }
            })
        })


        binding.cvLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logoutAccount()
                requireActivity().startActivity(Intent(context,MainActivity::class.java))
                requireActivity().finishAffinity()
            }
        }
    }

}