package apps.eduraya.e_parking.ui.profile

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.e_parking.R
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.databinding.ActivityEditProfileBinding
import apps.eduraya.e_parking.startAnActivity
import apps.eduraya.e_parking.ui.home.HomeActivity
import apps.eduraya.e_parking.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<EditProfileViewModel>()
    private lateinit var binding: ActivityEditProfileBinding
    private var mUri: Uri? = null
    private val OPERATION_CHOOSE_PHOTO = 2
    var imagePath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressbar.visible(false)

        binding.navBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.userName.observe(this, Observer {
            binding.editTextTextPersonName.setText(it.toString())
        })

        viewModel.userEmail.observe(this, Observer {
            binding.editTextTextEmailAddress.setText(it.toString())
        })

        viewModel.userPhone.observe(this, Observer {
            binding.editTextTextPhone.setText(it.toString())
        })

        binding.btnChangePict.setOnClickListener {
            val checkSelfPermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                //Requests permissions to be granted to this application at runtime
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            else{
                openGallery()
            }
        }

        binding.btnSave.setOnClickListener {
            val userPreferences = UserPreferences(this)
            userPreferences.accessToken.asLiveData().observe(this, Observer {
                val name = binding.editTextTextPersonName.text.toString().trim()
                val email = binding.editTextTextEmailAddress.text.toString().trim()
                val avatar = imagePath!!
                val phone = binding.editTextTextPhone.text.toString().trim()

                viewModel.setEditProfileResponse("Bearer $it",name, email, avatar, phone )

            })
        }

        viewModel.getEditProfileResponse.observe(this, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->
                    lifecycleScope.launch {
                        startAnActivity(HomeActivity::class.java)
                        finishAffinity()
                    }

                is Resource.Failure -> Toast.makeText(this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
                                            , grantedResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when(requestCode){
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
                    PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }else {
                    show("Unfortunately You are Denied Permission to Perform this Operataion.")
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
//            OPERATION_CAPTURE_PHOTO ->
//                if (resultCode == Activity.RESULT_OK) {
//                    val bitmap = BitmapFactory.decodeStream(
//                        getContentResolver().openInputStream(mUri))
//                    mImageView!!.setImageBitmap(bitmap)
//                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }
    }

    private fun show(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(this, uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri?.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion)
            }
            else if ("com.android.providers.downloads.documents" == uri?.authority){
                val contentUri = ContentUris.withAppendedId(Uri.parse(
                    "content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = getImagePath(contentUri, null)
            }
        }
        else if ("content".equals(uri?.scheme, ignoreCase = true)){
            imagePath = getImagePath(uri, null)
        }
        else if ("file".equals(uri?.scheme, ignoreCase = true)){
            imagePath = uri?.path
        }
        renderImage(imagePath)
    }

    private fun renderImage(imagePath: String?){
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            binding.imgProfile?.setImageBitmap(bitmap)
        }
        else {
            show("ImagePath is null")
        }
    }

    @SuppressLint("Range")
    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = contentResolver.query(uri!!, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

}