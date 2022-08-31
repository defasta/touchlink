package apps.eduraya.genius.ui.content

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import apps.eduraya.genius.R
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.responses.DataContent
import apps.eduraya.genius.data.responses.DataCourse
import apps.eduraya.genius.databinding.ActivityContentBinding
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.ui.course.ListCourseAdapter
import apps.eduraya.genius.ui.content.detail_content.DetailContentActivity
import apps.eduraya.genius.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentActivity : AppCompatActivity() {
    private val viewModel by viewModels<ContentViewModel>()
    private lateinit var binding: ActivityContentBinding
    lateinit var adapter: ListContentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userPreferences = UserPreferences(this)

        binding.navBack.setOnClickListener {
            onBackPressed()
        }

        binding.progressbar.visible(false)
        binding.noIntenet.visible(false)
        binding.tvNoInternet.visible(false)
        binding.noData.visible(false)
        binding.tvNoData.visible(false)
        binding.tvNoCourse.visible(false)

        userPreferences.accessToken.asLiveData()
            .observe(this, androidx.lifecycle.Observer { token ->
                viewModel.idCourse.observe(this, Observer { courseId ->
                    viewModel.getListContent("Bearer $token", courseId)
                })
            })

        viewModel.getListContentResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        if (it.value.data?.data?.isNotEmpty() == true){
                            adapter = ListContentAdapter(it.value.data?.data)
                            binding.rvListClass.layoutManager =
                                GridLayoutManager(this@ContentActivity, 4, GridLayoutManager.VERTICAL, false)
                            binding.rvListClass.adapter =adapter
                            binding.rvListClass.setHasFixedSize(true)
                            adapter.setOnItemClickCallback(object :
                                ListContentAdapter.OnItemClickCallback {
                                override fun onItemClicked(dataContent: DataContent) {
                                    startActivity(Intent(this@ContentActivity, DetailContentActivity::class.java).apply {
                                        putExtra("KEY_ID_CONTENT", dataContent.id.toString())
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