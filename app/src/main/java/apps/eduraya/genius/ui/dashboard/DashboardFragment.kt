package apps.eduraya.genius.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.responses.DataAllContent
import apps.eduraya.genius.data.responses.DataClass
import apps.eduraya.genius.databinding.FragmentDashboardBinding
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.ui.base.BaseFragment
import apps.eduraya.genius.ui.content.detail_content.DetailContentActivity
import apps.eduraya.genius.ui.course.CourseActivity
import apps.eduraya.genius.ui.dashboard.educational_level.EducationalLevelActivity
import apps.eduraya.genius.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate){
    private val viewModel by viewModels<DashboardViewModel>()
    lateinit var adapter: ListAllContentAdapter
    lateinit var adapter2: ListHorizontalClassroomAdapter
    private var isSearch = false
    private var isAllContent = false
    private var isByEducationalLevel = false
    var listAllContentLoaded = ArrayList<DataAllContent>()
    var listAllSearchContentLoaded = ArrayList<DataAllContent>()
    var listAllContentByEducationalLevelLoaded = ArrayList<DataAllContent>()
    private var page = 1
    private var pageSearched = 1
    private var pageByEducationalLevel = 1
    private lateinit var layoutManager: GridLayoutManager
    private var totalPage: Int = 1
    private var totalPageSearched: Int = 1
    private var totalPageByEducationalLevel: Int = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPreferences = UserPreferences(requireContext())
        viewModel.setIsAllContent(true)

        layoutManager = GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
        binding.progressbar.visible(false)
        binding.noIntenet.visible(false)
        binding.tvNoInternet.visible(false)
        binding.noData.visible(false)
        binding.tvNoData.visible(false)
        //binding.btnLoadMore.visible(false)

        binding.llSearch.setOnClickListener {
            binding.searchView.onActionViewExpanded()
        }

        binding.cvSd.setOnClickListener {
            viewModel.setIsAllContent(false)
            isAllContent = false
            isByEducationalLevel = true
            loadContentByEducationalLevel("SD")
//            requireActivity().startActivity(Intent(context, EducationalLevelActivity::class.java).apply {
//                putExtra("KEY_EDUCATIONAL_LEVEL", "SD")
//            })
        }

        binding.cvSmp.setOnClickListener {
            viewModel.setIsAllContent(false)
            isAllContent = false
            isByEducationalLevel = true
            loadContentByEducationalLevel("SMP")
//            requireActivity().startActivity(Intent(context, EducationalLevelActivity::class.java).apply {
//                putExtra("KEY_EDUCATIONAL_LEVEL", "SMP")
//            })
        }

        binding.cvSma.setOnClickListener {
            isAllContent = false
            isByEducationalLevel = true
            loadContentByEducationalLevel("SMA")
//            requireActivity().startActivity(Intent(context, EducationalLevelActivity::class.java).apply {
//                putExtra("KEY_EDUCATIONAL_LEVEL", "SMA")
//            })
        }

        binding.cvSmk.setOnClickListener {
            isAllContent = false
            isByEducationalLevel = true
            loadContentByEducationalLevel("SMK")
//            requireActivity().startActivity(Intent(context, EducationalLevelActivity::class.java).apply {
//                putExtra("KEY_EDUCATIONAL_LEVEL", "SMK")
//            })
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                isSearch = true
                viewModel.setIsAllContent(false)
                binding.rvListClassHorizontal.visible(false)
                getSearchedContent(p0!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
            viewModel.isAllContent.observe(viewLifecycleOwner, Observer { isAllContent ->
                if (isAllContent == true){
                    viewModel.getListAllContent("Bearer $token",page.toString())
                    viewModel.getListAllContentResponse.observe(viewLifecycleOwner, Observer {listAllContent ->
                        binding.progressbar.visible(listAllContent is Resource.Loading)
                        when(listAllContent){
                            is Resource.Success -> {
                                lifecycleScope.launch {
                                    totalPage = listAllContent.value.data?.lastPage.toString().toInt()
                                    val contentSize = listAllContent.value.data?.data?.size
                                    listAllContent.value.data?.data?.forEach {
                                        listAllContentLoaded.add(it)
                                    }

                                    adapter = ListAllContentAdapter(listAllContentLoaded)
                                    //adapter = ListAllContentAdapter(listAllContent.value.data?.data)
                                    binding.rvListClass.layoutManager = layoutManager
                                    binding.rvListClass.adapter =adapter
                                    binding.rvListClass.setHasFixedSize(true)

                                    viewModel.isAllContent.observe(viewLifecycleOwner, Observer { isAllContent ->
                                        if(isAllContent == true){
                                            binding.nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                                                // on scroll change we are checking when users scroll as bottom.
                                                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                                                    // in this method we are incrementing page number,
                                                    // making progress bar visible and calling get data method.
                                                    page++
                                                    // on below line we are making our progress bar visible.
                                                    //loadingPB.setVisibility(View.VISIBLE)
                                                    if (page < totalPage) {
                                                        // on below line we are again calling
                                                        // a method to load data in our array list.
                                                        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
                                                            viewModel.getListAllContent("Bearer $token",page.toString() )
                                                            //viewModel.getListClassroom("Bearer $token")
                                                        })
                                                    }
                                                }
                                            })
                                        }
                                    })


                                    adapter.setOnItemClickCallback(object :ListAllContentAdapter.OnItemClickCallback{
                                        override fun onItemClicked(dataAllContent: DataAllContent) {
                                            requireActivity().startActivity(Intent(context, DetailContentActivity::class.java).apply {
                                                putExtra("KEY_ID_CONTENT", dataAllContent.id.toString())
                                            })
                                        }

                                    })
                                }
                            }
                            is Resource.Failure -> {
                                if(listAllContent.isNetworkError){
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
            })
        })

    }

    private fun getSearchedContent(title:String){
        val userPreferences = UserPreferences(requireContext())

        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
            viewModel.searchContent("Bearer $token",title )
        })

        viewModel.searchContentResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        //binding.btnLoadMore.isVisible = false
                        if (it.value.data?.data?.isNotEmpty() == true){

                            adapter.clear()
                            listAllSearchContentLoaded.clear()

                            totalPageSearched = it.value.data?.lastPage.toString().toInt()
                            it.value.data?.data?.forEach { dataAllContent ->
                                listAllSearchContentLoaded.add(dataAllContent)
                            }


                            adapter = ListAllContentAdapter(listAllSearchContentLoaded)
                            binding.rvListClass.layoutManager =
                                GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
                            binding.rvListClass.adapter =adapter
                            binding.rvListClass.setHasFixedSize(true)

                            binding.nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                                // on scroll change we are checking when users scroll as bottom.
                                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                                    // in this method we are incrementing page number,
                                    // making progress bar visible and calling get data method.
                                    pageSearched++
                                    // on below line we are making our progress bar visible.
                                    //loadingPB.setVisibility(View.VISIBLE)
                                    if (pageSearched < totalPageSearched) {
                                        // on below line we are again calling
                                        // a method to load data in our array list.
                                        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
                                            viewModel.searchContent("Bearer $token",title )
                                        })
                                    }
                                }
                            })

                            adapter.setOnItemClickCallback(object :
                                ListAllContentAdapter.OnItemClickCallback {
                                override fun onItemClicked(dataAllContent: DataAllContent) {
                                    startActivity(Intent(requireContext(), DetailContentActivity::class.java).apply {
                                        putExtra("KEY_ID_CONTENT", dataAllContent.id.toString())
                                    })
                                }

                            })
                        }else{
                            adapter.clear()
                            binding.noData.visible(true)
                            binding.tvNoData.visible(true)
                        }

                    }
                }
                is Resource.Failure -> {
                    //binding.btnLoadMore.isVisible = false
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

    private fun loadContentByEducationalLevel(level:String){
        val userPreferences = UserPreferences(requireContext())
        userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
            viewModel.getEducationalLevelContent("Bearer $token",level.toString() )
        })

        viewModel.getEducationalLevelContentResponse.observe(viewLifecycleOwner, Observer {listAllContent ->
            binding.progressbar.visible(listAllContent is Resource.Loading)
            when(listAllContent){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        adapter.clear()
                        listAllContentByEducationalLevelLoaded.clear()

                        totalPage = listAllContent.value.data?.lastPage.toString().toInt()
                        val contentSize = listAllContent.value.data?.data?.size
                        listAllContent.value.data?.data?.forEach {
                            listAllContentByEducationalLevelLoaded.add(it)
                        }

                        adapter = ListAllContentAdapter(listAllContentByEducationalLevelLoaded)
                        //adapter = ListAllContentAdapter(listAllContent.value.data?.data)
                        binding.rvListClass.layoutManager = layoutManager
                        binding.rvListClass.adapter =adapter
                        binding.rvListClass.setHasFixedSize(true)


                        binding.nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                            // on scroll change we are checking when users scroll as bottom.
//                            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
//                                // in this method we are incrementing page number,
//                                // making progress bar visible and calling get data method.
//                                pageByEducationalLevel++
//                                // on below line we are making our progress bar visible.
//                                //loadingPB.setVisibility(View.VISIBLE)
//                                if (pageByEducationalLevel < totalPageByEducationalLevel) {
//                                    // on below line we are again calling
//                                    // a method to load data in our array list.
//                                    userPreferences.accessToken.asLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer { token ->
//                                        viewModel.getEducationalLevelContent("Bearer $token", level )
//                                        //viewModel.getListClassroom("Bearer $token")
//                                    })
//                                }
//                            }
                        })

                        adapter.setOnItemClickCallback(object :ListAllContentAdapter.OnItemClickCallback{
                            override fun onItemClicked(dataAllContent: DataAllContent) {
                                requireActivity().startActivity(Intent(context, DetailContentActivity::class.java).apply {
                                    putExtra("KEY_ID_CONTENT", dataAllContent.id.toString())
                                })
                            }

                        })
                    }
                }
                is Resource.Failure -> {
                    if(listAllContent.isNetworkError){
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
