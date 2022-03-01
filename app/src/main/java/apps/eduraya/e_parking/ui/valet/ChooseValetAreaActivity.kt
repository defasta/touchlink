package apps.eduraya.e_parking.ui.valet

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.responses.valet.DataValetArea
import apps.eduraya.e_parking.databinding.ActivityChooseValetAreaBinding
import apps.eduraya.e_parking.ui.home.HomeActivity
import apps.eduraya.e_parking.ui.valet.adapter.ValetAreaAdapter
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ChooseValetAreaActivity : AppCompatActivity() {
    private val viewModel by viewModels<ChooseValetAreaViewModel>()
    private lateinit var binding : ActivityChooseValetAreaBinding
    private lateinit var alertBuilder: AlertDialog.Builder
    lateinit var valetAreaAdapter: ValetAreaAdapter

    var formate = SimpleDateFormat("YYYY-MM-dd", Locale.US)
    var timeFormat = SimpleDateFormat("HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseValetAreaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.navBack.setOnClickListener {
            onBackPressed()
        }

        val userPreferences = UserPreferences(this)
        alertBuilder = AlertDialog.Builder(this)

        viewModel.timePicked.observe(this, Observer { time->
            viewModel.datePicked.observe(this, Observer { date ->
                if(time != null && date != null){
                    binding.btnChooseTime.visible(false)
                    binding.layoutTime.visible(true)
                    binding.rvValetArea.visible(true)
                    userPreferences.accessToken.asLiveData().observe(this, Observer {
                        viewModel.idPlace.observe(this, Observer { placeId ->
                            viewModel.idVehicle.observe(this, Observer { vehicleId ->
                                viewModel.setAllValetAreaResponse("Bearer $it", placeId.toString())
                                viewModel.getAllValetAreaResponse.observe(this, Observer {
                                    binding.progressbar.visible(it is Resource.Loading)
                                    when(it){
                                        is Resource.Success ->
                                            lifecycleScope.launch {
                                                it.value.data.sortBy { item -> item.floor }
                                                val sortedListValetArea = ArrayList<DataValetArea>()
                                                it.value.data.forEach { valet ->
                                                    if (valet.vehicleId.toString() == vehicleId){
                                                        sortedListValetArea.addAll(listOf(valet))
                                                    }
                                                }
                                                valetAreaAdapter = ValetAreaAdapter(sortedListValetArea)
                                                binding.tvNoTime.visible(false)
                                                binding.rvValetArea.visible(true)
                                                binding.rvValetArea.layoutManager = LinearLayoutManager(this@ChooseValetAreaActivity)
                                                binding.rvValetArea.adapter = valetAreaAdapter
//                                                userPreferences.isCheckin.asLiveData().observe(this@ChooseValetAreaActivity, Observer { isCheckin ->
//                                                    valetAreaAdapter.setOnItemClickCallback(object :ValetAreaAdapter.OnItemClickCallback{
//                                                        override fun onItemClicked(dataValetArea: DataValetArea) {
//                                                            if (isCheckin == "1"){
//                                                                createReservation(dataValetArea)
//                                                            }else if(isCheckin == "0"){
//                                                                Toast.makeText(this@ChooseValetAreaActivity, "Anda hanya diizinkan memesan satu valet",Toast.LENGTH_SHORT).show()
//                                                            }
//                                                        }
//                                                    })
//                                                })
                                                valetAreaAdapter.setOnItemClickCallback(object :ValetAreaAdapter.OnItemClickCallback{
                                                    override fun onItemClicked(dataValetArea: DataValetArea) {
//                                                        if (isCheckin == "1"){
//
//                                                        }else if(isCheckin == "0"){
//                                                            Toast.makeText(this@ChooseValetAreaActivity, "Anda hanya diizinkan memesan satu valet",Toast.LENGTH_SHORT).show()
//                                                        }
                                                        createReservation(dataValetArea)
                                                    }
                                                })


                                            }
                                        is Resource.Failure -> Toast.makeText(this, "Gagal memuat data.",Toast.LENGTH_SHORT).show()
                                    }
                                })
                            })
                        })
                    })
                }else {
                    binding.rvValetArea.visible(false)
                }
            })

        })

        binding.progressbar.visible(false)
        binding.layoutTime.visible(false)

        binding.btnChooseTime.setOnClickListener {
            getCheckinTime()
        }

        binding.btnChangeTime.setOnClickListener {
            getCheckinTime()
        }

    }

    private fun getCheckinTime(){
        val now = Calendar.getInstance()

        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)
            val timePicked = timeFormat.format(selectedTime.time)
            viewModel.setTimePicked(timePicked)
            viewModel.timePicked.observe(this, Observer {
                binding.tvTime.text = timePicked
            })

        }, now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
        timePicker.show()

        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR,year)
            selectedDate.set(Calendar.MONTH,month)
            selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val date = formate.format(selectedDate.time)
            viewModel.setDatePicked(date)
            viewModel.datePicked.observe(this, Observer {
                binding.tvDate.text = it
            }) },
            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

    }

    private fun createReservation(dataValetArea: DataValetArea){
        val userPreferences = UserPreferences(this)
        userPreferences.accessToken.asLiveData().observe(this, Observer { tokenAccess ->
            viewModel.datePicked.observe(this, Observer { date ->
                viewModel.timePicked.observe(this, Observer { time ->
                    viewModel.setReservationResponse("Bearer $tokenAccess", dataValetArea.id.toString(), "$date $time")
                    viewModel.getReservationResponse.observe(this, Observer {
                        binding.progressbar.visible(it is Resource.Loading)
                        when(it){
                            is Resource.Success ->
                                lifecycleScope.launch {
                                    Toast.makeText(this@ChooseValetAreaActivity, "Berhasil melakukan reservasi", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@ChooseValetAreaActivity, HomeActivity::class.java))
                                    finishAffinity()
                                }
                            is Resource.Failure -> Toast.makeText(this, "Terjadi kesalahan",Toast.LENGTH_SHORT).show()
                        }
                    })
                })
            })

        })

    }
}