package apps.eduraya.e_parking.ui.home

import androidx.lifecycle.ViewModelProvider
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
import apps.eduraya.e_parking.databinding.HomeFragmentBinding
import apps.eduraya.e_parking.startAnActivity
import apps.eduraya.e_parking.startNewActivity
import apps.eduraya.e_parking.ui.base.BaseFragment
import apps.eduraya.e_parking.ui.deposit.CreateDepositActivity
import apps.eduraya.e_parking.ui.maps.MapsActivity
import apps.eduraya.e_parking.ui.myqr.MyQrActivity
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvSearch.setOnClickListener {
            requireActivity().startAnActivity(MapsActivity::class.java)
        }

        binding.cvMyqr.setOnClickListener {
            requireActivity().startAnActivity(MyQrActivity::class.java)
        }

        binding.btnDeposit.setOnClickListener {
            requireActivity().startAnActivity(CreateDepositActivity::class.java)
        }

//        viewModel.getUserInfoDBObserver().observe(this,object : Observer<List<UserInfo>>{
//            override fun onChanged(t: List<UserInfo>?) {
//                t?.forEach {
//                    binding.username.text = it.name
//                    binding.tvSaldo.text = "Rp. ${it.balance.toString()}"
//                }
//            }
//
//        })

        val userPreferences = UserPreferences(context!!)
        userPreferences.accessToken.asLiveData().observe(this, androidx.lifecycle.Observer { token ->
            viewModel.setUserInfoResponse("Bearer $token")
            viewModel.getUserInfoResponse.observe(this, Observer {
                binding.progressbar.visible(it is Resource.Loading)
                when(it){
                    is Resource.Success -> {
                        lifecycleScope.launch {
                            binding.username.text = it.value.data?.user?.name
                            binding.tvSaldo.text = "Rp. ${it.value.data?.user?.balance.toString()}"
                        }
                    }
                    is Resource.Failure -> Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()

                }
            })
        })
    }

}