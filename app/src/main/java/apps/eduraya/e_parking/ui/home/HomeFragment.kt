package apps.eduraya.e_parking.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.databinding.HomeFragmentBinding
import apps.eduraya.e_parking.startNewActivity
import apps.eduraya.e_parking.ui.base.BaseFragment
import apps.eduraya.e_parking.ui.maps.MapsActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvValet.setOnClickListener {
            requireActivity().startNewActivity(MapsActivity::class.java)
        }

        viewModel.getUserInfoDBObserver().observe(this,object : Observer<List<UserInfo>>{
            override fun onChanged(t: List<UserInfo>?) {
                t?.forEach {
                    binding.username.text = it.name
                }
            }

        })
    }

}