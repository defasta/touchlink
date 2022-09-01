package apps.eduraya.genius.ui.content.detail_content

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.genius.R
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.databinding.ActivityDetailContentBinding
import apps.eduraya.genius.snackbar
import apps.eduraya.genius.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailContentActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailContentViewModel>()
    private lateinit var binding: ActivityDetailContentBinding
    lateinit var videoView: VideoView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailContentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userPreferences = UserPreferences(this)
        val mediaController = MediaController(this)
        binding.webView.webViewClient = WebViewClient()

        binding.navBack.setOnClickListener {
            onBackPressed()
        }

        binding.progressbar.visible(false)

        userPreferences.accessToken.asLiveData()
            .observe(this, androidx.lifecycle.Observer { token ->
                viewModel.idContent.observe(this, Observer { contentId ->
                    viewModel.getDetailContent("Bearer $token", contentId)
                })
            })

        viewModel.getDetailContentResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        if (it.value.data?.type == "video"){
                            val uri: Uri = Uri.parse(it.value.data.videoUrl)
                            binding.videoView.visible(true)
                            binding.webView.visible(false)
                            binding.videoView.setVideoURI(uri)
                            mediaController.setAnchorView(binding.videoView)
                            mediaController.setMediaPlayer(binding.videoView)
                            binding.videoView.setMediaController(mediaController)
                            binding.videoView.start()
                        }else if(it.value.data?.type == "url"){
                            // this will enable the javascript settings
                                binding.videoView.visible(false)
                            binding.webView.settings.javaScriptEnabled = true

                            // if you want to enable zoom feature
                            binding.webView.settings.setSupportZoom(true)
                            binding.webView.loadUrl(it.value.data.contentUrl!!)
//                            val openURL = Intent(Intent.ACTION_VIEW)
//                            openURL.data = Uri.parse(it.value.data?.contentUrl)
//                            startActivity(openURL)
                        }
                    }
                }
                is Resource.Failure -> {
                    if(it.isNetworkError){
                        view.snackbar("Mohon cek koneksi internet Anda!")
                    }else{
                        view.snackbar("Gagal memuat data. Silahkan tunggu beberapa saat")
                    }
                }
            }
        })
    }
}