package apps.eduraya.e_parking.ui.vehicle_type

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.databinding.ActivityChooseVehicleBinding
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseVehicleActivity : AppCompatActivity() {
    private val viewModel by viewModels<ChooseVehicleViewModel>()
    private lateinit var binding : ActivityChooseVehicleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseVehicleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.namePlace.observe(this, Observer {
            binding.placeName.text = it
        })

        viewModel.addressPlace.observe(this, Observer {
            binding.tvAddress.text = it
        })

        viewModel.totalMotor.observe(this, Observer {
            binding.tvKuotaMotor.text = it
        })

        viewModel.totalCar.observe(this, Observer {
            binding.tvKuotaMobil.text = it
        })

        val userPreferences = UserPreferences(this)
        userPreferences.accessToken.asLiveData().observe(this, Observer {
            viewModel.setAllVehicleResponse("Bearer $it")
        })

        viewModel.getAllVehicleResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->
                    lifecycleScope.launch {
                        it.value.data.forEach { dataVehicle ->
                            if (dataVehicle.id == 1){
                                binding.tvBasicMotor.text = "Tarif ${dataVehicle.basicDuration} jam pertama : ${dataVehicle.basicPrice}"
                                binding.tvProgressiveMotor.text = "Tiap ${dataVehicle.progressiveDuration} jam berikutnya : ${dataVehicle.progressivePrice}"
                            }
                            if (dataVehicle.id == 2){
                                binding.tvBasicCar.text = "Tarif ${dataVehicle.basicDuration} jam pertama : ${dataVehicle.basicPrice}"
                                binding.tvProgressiveCar.text = "Tarif ${dataVehicle.basicDuration} jam pertama : ${dataVehicle.basicPrice}"
                            }
                        }

                    }
                is Resource.Failure -> Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

}