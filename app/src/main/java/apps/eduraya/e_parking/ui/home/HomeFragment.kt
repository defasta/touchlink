package apps.eduraya.e_parking.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.databinding.HomeFragmentBinding
import apps.eduraya.e_parking.startNewActivity
import apps.eduraya.e_parking.ui.base.BaseFragment
import apps.eduraya.e_parking.ui.maps.MapsActivity

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemValet.setOnClickListener {
            requireActivity().startNewActivity(MapsActivity::class.java)
        }
    }

}