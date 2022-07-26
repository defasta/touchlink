package apps.eduraya.edurayaapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.edurayaapp.data.db.UserPreferences
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.databinding.HomeFragmentBinding
import apps.eduraya.edurayaapp.rupiah
import apps.eduraya.edurayaapp.startAnActivity
import apps.eduraya.edurayaapp.ui.base.BaseFragment
import apps.eduraya.edurayaapp.visible
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val userPreferences = UserPreferences(requireContext())
        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
            viewModel.getUserInfo("Bearer $token")
        })
        viewModel.userInfoResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        binding.username.text = it.value.data?.user?.name
                        binding.tvSaldo.text = rupiah(it.value.data?.user?.balance!!.toDouble())
                        Glide.with(requireContext()).load(it.value.data?.user?.avatar).into(binding.imgProfile)
                    }
                }
                is Resource.Failure -> Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

}