package apps.eduraya.genius.ui.dashboard.educational_level

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.responses.DataAllContent
import apps.eduraya.genius.data.responses.DataContent
import apps.eduraya.genius.databinding.ActivityEducationalLevelBinding
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.ui.content.ContentActivity
import apps.eduraya.genius.ui.content.ListContentAdapter
import apps.eduraya.genius.ui.content.detail_content.DetailContentActivity
import apps.eduraya.genius.ui.dashboard.ListAllContentAdapter
import apps.eduraya.genius.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EducationalLevelActivity : AppCompatActivity() {
    private val viewModel by viewModels<EducationalLevelViewModel>()
    private lateinit var binding: ActivityEducationalLevelBinding
    lateinit var adapter: ListAllContentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationalLevelBinding.inflate(layoutInflater)
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
        //binding.tvNoCourse.visible(false)

        userPreferences.accessToken.asLiveData()
            .observe(this, androidx.lifecycle.Observer { token ->
                viewModel.educationalLevel.observe(this, Observer { educationalLevel ->
                    binding.tvTitle.text = educationalLevel
                    viewModel.getEducationalLevelContent("Bearer $token", educationalLevel)
                })

            })

        viewModel.getEducationalLevelContentResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        if (it.value.data?.data?.isNotEmpty() == true){
                            adapter = ListAllContentAdapter(it.value.data?.data)
                            binding.rvListClass.layoutManager =
                                GridLayoutManager(this@EducationalLevelActivity, 4, GridLayoutManager.VERTICAL, false)
                            binding.rvListClass.adapter =adapter
                            binding.rvListClass.setHasFixedSize(true)
                            adapter.setOnItemClickCallback(object :
                                ListAllContentAdapter.OnItemClickCallback {
                                override fun onItemClicked(dataAllContent: DataAllContent) {
                                    startActivity(Intent(this@EducationalLevelActivity, DetailContentActivity::class.java).apply {
                                        putExtra("KEY_ID_CONTENT", dataAllContent.id.toString())
                                    })
                                }

                            })
                        }else{
                            binding.noData.visible(true)
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