package apps.eduraya.genius.ui.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.databinding.FragmentProfileBinding
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.ui.base.BaseFragment
import apps.eduraya.genius.ui.classroom.HomeViewModel
import apps.eduraya.genius.visible
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userInfoResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        if (it.value.data?.photoUrl != null){
                            Glide.with(requireContext()).load(it.value.data.photoUrl).into(binding.imgProfile)
                        }
                        if(it.value.data?.name.isNullOrEmpty()){
                            binding.tvUsername.text = "-"
                        }else{
                            binding.tvUsername.text = it.value.data?.name
                        }

                        if (it.value.data?.email.isNullOrEmpty()){
                            binding.tvEmail.text = "-"
                        }else{
                            binding.tvEmail.text = it.value.data?.email
                        }

                        if (it.value.data?.address.isNullOrEmpty()){
                            binding.tvAddress.text ="-"
                        }else{
                            binding.tvAddress.text = it.value.data?.address
                        }

                        if (it.value.data?.birthDate.isNullOrEmpty()){
                            binding.tvBirthDate.text = "-"
                        }else{
                            binding.tvBirthDate.text = it.value.data?.birthDate
                        }

                        if(it.value.data?.birthPlace.isNullOrEmpty()){
                            binding.tvBirthPlace.text = "-"
                        }else{
                            binding.tvBirthPlace.text = it.value.data?.birthPlace
                        }

                        if (it.value.data?.educationalLevel.isNullOrEmpty()){
                            binding.tvEducation.text = "-"
                        }else{
                            binding.tvEducation.text = it.value.data?.educationalLevel
                        }

                        binding.buttonEditProfile.setOnClickListener {view ->
                            requireActivity().startActivity(Intent(context,EditProfileActivity::class.java).apply {
                                putExtra("KEY_NAME_USER", it.value.data?.name)
                                putExtra("KEY_EMAIL_USER", it.value.data?.email)
                                putExtra("KEY_ADDRESS_USER", it.value.data?.address )
                                putExtra("KEY_BIRTH_DATE_USER", it.value.data?.birthDate)
                                putExtra("KEY_BIRTH_PLACE_USER", it.value.data?.birthPlace)
                                putExtra("KEY_EDUCATION_USER", it.value.data?.educationalLevel)
                                putExtra("KEY_PHOTO_URL_USER", it.value.data?.photoUrl)
                            })

                        }
                    }

                    Log.d(TAG, "profile: ${it.value.data}")
                }
                is Resource.Failure ->{
                    if(it.isNetworkError){
                        requireView().snackbar("Mohon cek koneksi internet Anda!")
                    }else{
                        requireView().snackbar("Gagal memuat data. Silahkan tunggu beberapa saat")
                    }
                }

            }
        })
        val userPreferences = UserPreferences(requireContext())
        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
            if (token != null) {
                userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer {userId ->
                    if(userId != null){
                        viewModel.getUserInfo("Bearer $token", userId)
                    }
                })
            }
        })

    }

}