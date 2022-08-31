package apps.eduraya.genius.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.responses.DataCourse
import apps.eduraya.genius.databinding.ActivityCourseBinding
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.ui.content.ContentActivity
import apps.eduraya.genius.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CourseActivity : AppCompatActivity() {
    private val viewModel by viewModels<CourseViewModel>()
    private lateinit var binding: ActivityCourseBinding
    lateinit var adapter: ListCourseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userPreferences = UserPreferences(this)

        binding.progressbar.visible(false)
        binding.noIntenet.visible(false)
        binding.tvNoInternet.visible(false)
        binding.noData.visible(false)
        binding.tvNoData.visible(false)
        binding.tvNoCourse.visible(false)

        binding.navBack.setOnClickListener {
            onBackPressed()
        }

        userPreferences.accessToken.asLiveData()
            .observe(this, androidx.lifecycle.Observer { token ->
                viewModel.idClassroom.observe(this, Observer { classroomId ->
                    viewModel.getListCourse("Bearer $token", classroomId)
                })
            })

        viewModel.getListCourseResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        if (it.value.data?.data?.isNotEmpty() == true){
                            adapter = ListCourseAdapter(it.value.data?.data)
                            binding.rvListClass.layoutManager =
                                GridLayoutManager(this@CourseActivity, 4, GridLayoutManager.VERTICAL, false)
                            binding.rvListClass.adapter =adapter
                            binding.rvListClass.setHasFixedSize(true)
                            adapter.setOnItemClickCallback(object :
                                ListCourseAdapter.OnItemClickCallback {
                                override fun onItemClicked(dataCourse: DataCourse) {
                                    startActivity(Intent(this@CourseActivity, ContentActivity::class.java).apply {
                                        putExtra("KEY_ID_COURSE", dataCourse.id.toString())
                                    })
                                }

                            })
                        }else{
                            binding.noData.visible(true)
                            binding.tvNoCourse.visible(true)
                        }

                    }
                }
                is Resource.Failure -> {
                    if(it.isNetworkError){
                        view.snackbar("Mohon cek koneksi internet Anda!")
                        binding.noIntenet.visible(true)
                        binding.tvNoInternet.visible(true)
                    }else{
                        view.snackbar("Gagal memuat data. Silahkan tunggu beberapa saat")
                        binding.noData.visible(true)
                        binding.tvNoData.visible(true)
                    }
                }
            }
        })
    }
}
