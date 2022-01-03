package apps.eduraya.e_parking.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.databinding.FragmentProfileBinding
import apps.eduraya.e_parking.ui.base.BaseFragment
import apps.eduraya.e_parking.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserInfoDBObserver().observe(this,object : Observer<List<UserInfo>> {
            override fun onChanged(t: List<UserInfo>?) {
                t?.forEach {
                    binding.tvUsername.text = it.name.toString()
                    binding.tvEmail.text = it.email.toString()
                    binding.tvPhone.text = it.phone.toString()
                }
            }

        })
    }

}