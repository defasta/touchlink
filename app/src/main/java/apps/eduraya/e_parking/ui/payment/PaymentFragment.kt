package apps.eduraya.e_parking.ui.payment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.responses.deposit.DataDeposit
import apps.eduraya.e_parking.databinding.FragmentPaymentBinding
import apps.eduraya.e_parking.ui.base.BaseFragment
import apps.eduraya.e_parking.ui.deposit.PayDepositActivity
import apps.eduraya.e_parking.ui.payment.adapter.PaymentAdapter
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate){
    private val viewModel by viewModels<PaymentViewModel>()
    lateinit var paymentAdapter: PaymentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressbar.visible(false)
        binding.tvNo.visible(false)
        getPaymentViewModel()
    }

    private fun getPaymentViewModel(){
        val userPreferences = UserPreferences(context!!)
        userPreferences.accessToken.asLiveData().observe(this, androidx.lifecycle.Observer { token ->
            viewModel.setAllDepositResponse("Bearer $token")
            viewModel.getAllDepositResponse.observe(this, Observer {
                binding.progressbar.visible(it is Resource.Loading)
                when(it){
                    is Resource.Success ->
                        lifecycleScope.launch {
                            it.value.data.data.sortByDescending { item -> item.createdAt }
                            paymentAdapter = PaymentAdapter(it.value.data.data)
                            binding.rvListDeposit.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                            binding.rvListDeposit.adapter = paymentAdapter
                            binding.rvListDeposit.setHasFixedSize(true)
                            binding.tvNo.visible(false)
                            paymentAdapter.setOnItemClickCallback(object :PaymentAdapter.OnItemClickCallback{
                                override fun onItemClicked(dataDeposit: DataDeposit) {
                                    startActivity(Intent(context, PayDepositActivity::class.java).apply {
                                        putExtra("KEY_ID", dataDeposit.id.toString())
                                        putExtra("KEY_AMOUNT", dataDeposit.amount.toString())
                                        putExtra("KEY_STATUS", dataDeposit.status)
                                    })
                                }

                            })

                        }
                    is Resource.Failure -> Toast.makeText(context, "Gagal memuat data.",Toast.LENGTH_SHORT).show()
                }
            })
        })
    }
}