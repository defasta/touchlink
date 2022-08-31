package apps.eduraya.genius.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.responses.DataClass
import apps.eduraya.genius.databinding.HomeFragmentBinding
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.ui.base.BaseFragment
import apps.eduraya.genius.ui.classroom.adapter.ListClassroomAdapter
import apps.eduraya.genius.ui.course.CourseActivity
import apps.eduraya.genius.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var adapter: ListClassroomAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPreferences = UserPreferences(requireContext())

        binding.progressbar.visible(false)
        binding.noIntenet.visible(false)
        binding.tvNoInternet.visible(false)
        binding.noData.visible(false)
        binding.tvNoData.visible(false)

        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
            viewModel.getListClassroom("Bearer $token")
        })

        viewModel.getListClassroomResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        adapter = ListClassroomAdapter(it.value.data?.data)
                        binding.rvListClass.layoutManager =GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
                        binding.rvListClass.adapter =adapter
                        binding.rvListClass.setHasFixedSize(true)
                        adapter.setOnItemClickCallback(object :ListClassroomAdapter.OnItemClickCallback{
                            override fun onItemClicked(dataClassroom: DataClass) {
                                requireActivity().startActivity(Intent(context, CourseActivity::class.java).apply {
                                    putExtra("KEY_ID_CLASSROOM", dataClassroom.id.toString())
                                })
                            }

                        })
                    }
                }
                is Resource.Failure -> {
                    if(it.isNetworkError){
                        requireView().snackbar("Mohon cek koneksi internet Anda!")
                        binding.noIntenet.visible(true)
                        binding.tvNoInternet.visible(true)
                    }else{
                        requireView().snackbar("Gagal memuat data. Silahkan tunggu beberapa saat")
                        binding.noData.visible(true)
                        binding.tvNoData.visible(true)
                    }
                }
            }
        })


    }

}