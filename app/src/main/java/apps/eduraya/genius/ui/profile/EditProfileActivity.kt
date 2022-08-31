package apps.eduraya.genius.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import apps.eduraya.genius.*
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.databinding.ActivityEditProfileBinding
import apps.eduraya.genius.ui.classroom.HomeActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<EditProfileViewModel>()
    private lateinit var binding: ActivityEditProfileBinding
    private val OPERATION_CHOOSE_PHOTO = 2
    private var queryImageUrl: String = ""
    private var imgPath: String = ""
    private var imageUri: Uri? = null
    private val permissions = arrayOf(Manifest.permission.CAMERA)
    companion object{
//        private const val OPEN_FILE_REQUEST_CODE = 1
//        private const val OPEN_FOLDER_REQUEST_CODE = 2
        private const val RES_IMAGE = 100
        private const val MEDIA_LOCATION_PERMISSION_REQUEST_CODE = 3
        //private const val CHOOSE_FILE = 4
        private const val PERMISSION_READ_EXTERNAL_STORAGE = 5
    }
    var permissionArrays = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var imagePath: String? = null
    var formate = SimpleDateFormat("YYYY-MM-dd", Locale.US)
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

        viewModel.userAddress.observe(this, Observer {
            if(it != null){
                binding.editTextAddress.setText(it.toString())
            }
        })

        viewModel.userEducationalLevel.observe(this, Observer {
            if(it != null){
                binding.editTextEducation.setText(it.toString())
            }
        })

        viewModel.userBirthDate.observe(this, Observer {
            if(it != null){
                binding.tvBirthDate.text = it.toString()
            }
        })

        viewModel.userBirthPlace.observe(this, Observer {
            if(it != null){
                binding.editTextBirthPlace.setText(it.toString())
            }
        })

        viewModel.userPhotoUrl.observe(this, Observer {
            if (it != null){
                Glide.with(this).load(it).into(binding.imgProfile)
            }
        })

        binding.btnChangePict.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (checkPermissionForReadWrite(this)) {
                    openGallery()
                } else {
                    requestPermissionForReadWrite(this)
                }

            } else {
                if (isPermissionGrantedForMediaLocationAccess(this)) {
                    getPickImageIntent()
                } else {
                    Log.i("Tag", "else chooseFile")
                    requestPermissionForAccessMediaLocation(this)
                }
            }
//            val checkSelfPermission = ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
//                //Requests permissions to be granted to this application at runtime
//                ActivityCompat.requestPermissions(this,
//                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
//            }
//            else{
//                openGallery()
//            }
        }

        binding.btnChooseDate.setOnClickListener {
            getChoosenDate()
        }

        binding.btnSave.setOnClickListener {
            imagePath = ""
            if(imagePath != null){
                val userPreferences = UserPreferences(this)
                userPreferences.accessToken.asLiveData().observe(this, Observer {token->
                    userPreferences.userId.asLiveData().observe(this, Observer { id ->
                        val name = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(), binding.editTextTextPersonName.text.toString().trim())

                        val address = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.editTextAddress.text.toString().trim())

                        val educationalLevel = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.editTextEducation.text.toString().trim())

                        val birthDate = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.tvBirthDate.text.toString().trim())

                        val birthPlace = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.editTextBirthPlace.text.toString().trim())

                        val photoBody = okhttp3.RequestBody.create("image/*".toMediaTypeOrNull(), File(imagePath))

                        val avatar = MultipartBody.Part.createFormData("photo", File(imagePath).name, photoBody)

                        viewModel.editProfile(token!!, id!! ,name, birthDate, birthPlace,  address, avatar,educationalLevel)
                    })


                })

                viewModel.editProfileResponse.observe(this, Observer {
                    binding.progressbar.visible(it is Resource.Loading)
                    when(it){
                        is Resource.Success ->
                            lifecycleScope.launch {
                                startAnActivity(HomeActivity::class.java)
                                finishAffinity()
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
            }else{
                Toast.makeText(this, "Silakan upload foto terlebih dahulu", Toast.LENGTH_SHORT).show()
            }

        }


//        val setPermission = Build.VERSION.SDK_INT
//        if (setPermission > Build.VERSION_CODES.LOLLIPOP_MR1) {
//            if (checkIfAlreadyhavePermission()) {
//            } else {
//                requestPermissions(permissionArrays, 101)
//            }
//        }
    }

    override fun onResume() {
        super.onResume()

        if (queryImageUrl.isNotEmpty()) {

//                Glide.with(this@EditProfileActivity)
//                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .load(queryImageUrl)
//                    .into(binding.imgProfile)

            val bitmap = BitmapFactory.decodeFile(queryImageUrl)

            binding.imgProfile.setImageBitmap(bitmap)
        }

        GlobalScope.launch(Dispatchers.Main) {
            if (imagePath != null) {
                val bitmap = BitmapFactory.decodeFile(imagePath)
                binding.imgProfile.setImageBitmap(bitmap)
            }
            else {
                show("ImagePath is null")
            }
        }
    }

    private fun checkPermissionForReadWrite(context: Context): Boolean {
        val result: Int =
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )

        return result == PackageManager.PERMISSION_GRANTED
    }

    //Request Permission For Read Storage
    private fun requestPermissionForReadWrite(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), PERMISSION_READ_EXTERNAL_STORAGE
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestPermissionForAccessMediaLocation(context: Context) {
        Log.i("Tag", "requestPermissionForAML")

        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(android.Manifest.permission.ACCESS_MEDIA_LOCATION),
            MEDIA_LOCATION_PERMISSION_REQUEST_CODE
        )

    }

    private fun isPermissionGrantedForMediaLocationAccess(context: Context): Boolean {
        Log.i("Tag", "checkPermissionForAML")
        val result: Int =
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_MEDIA_LOCATION
            )
        return result == PackageManager.PERMISSION_GRANTED
    }


    private fun getChoosenDate() {
        val now = Calendar.getInstance()

        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR,year)
            selectedDate.set(Calendar.MONTH,month)
            selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val date = formate.format(selectedDate.time)
            viewModel.setDatePicked(date)
            viewModel.datePicked.observe(this, Observer {
                binding.tvBirthDate.text = it
            }) },
            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
        datePicker.show()

    }

    private fun openGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }

//    private fun checkIfAlreadyhavePermission(): Boolean {
//        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        return result == PackageManager.PERMISSION_GRANTED
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
//                                            , grantedResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
//        when(requestCode){
//            101 ->
//                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
//                    PackageManager.PERMISSION_GRANTED){
//                    openGallery()
//                }else {
//                    show("Unfortunately You are Denied Permission to Perform this Operataion.")
//                }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        handleImageOnKitkat(data)
                    }else{
                        handleImageRequest(data)
                    }
                }
        }
    }

    //Scoped storage

    private fun getPickImageIntent(): Intent? {
        var chooserIntent: Intent? = null

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())

        intentList = addIntentsToList(this, intentList, pickIntent)
        intentList = addIntentsToList(this, intentList, takePhotoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
                "Pilih foto dari galeri atau buka kamera"
            )
            chooserIntent!!.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    private fun setImageUri(): Uri {
        val folder = File("${getExternalFilesDir(Environment.DIRECTORY_DCIM)}")
        folder.mkdirs()

        val file = File(folder, "Image_Tmp.jpg")
        if (file.exists())
            file.delete()
        file.createNewFile()
        imageUri = FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID + ".fileProvider",
            file
        )
        imgPath = file.absolutePath
        return imageUri!!
    }


    private fun addIntentsToList(
        context: Context,
        list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    private fun handleImageRequest(data: Intent?) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
//            progressBar.visibility = View.GONE
//            Toast.makeText(
//                this,
//                t.localizedMessage ?: getString(R.string.some_err),
//                Toast.LENGTH_SHORT
//            ).show()
        }

        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            //progressBar.visibility = View.VISIBLE

            if (data?.data != null) {     //Photo from gallery
                imageUri = data.data
                queryImageUrl = imageUri?.path!!
                //queryImageUrl = compressImageFile(queryImageUrl, false, imageUri!!)
            } else {
                queryImageUrl = imgPath ?: ""
                //compressImageFile(queryImageUrl, uri = imageUri!!)
            }
            imageUri = Uri.fromFile(File(queryImageUrl))

            if (queryImageUrl.isNotEmpty()) {

//                Glide.with(this@EditProfileActivity)
//                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .load(queryImageUrl)
//                    .into(binding.imgProfile)

                val bitmap = BitmapFactory.decodeFile(queryImageUrl)

                binding.imgProfile.setImageBitmap(bitmap)
            }
            //progressBar.visibility = View.GONE
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
                val fileName = getDataColumn(applicationContext, uri, null, null)
                var uriToReturn: String? = null
                if(fileName != null){
                    uriToReturn = Uri.withAppendedPath(Uri.parse(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).absolutePath), fileName).toString()
                }
                //return uriToReturn
                imagePath = uriToReturn
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
        GlobalScope.launch(Dispatchers.Main) {
            if (imagePath != null) {
                val bitmap = BitmapFactory.decodeFile(imagePath)
                binding.imgProfile.setImageBitmap(bitmap)
            }
            else {
                show("ImagePath is null")
            }
        }

    }

    private fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(uri, null, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_DISPLAY_NAME) //_display_name
                return cursor.getString(columnIndex) //returns file name
            }
        }catch (ex: Exception){
            Log.e( "PathUtils", "Error getting uri for cursor to read file: ${ex.message}")
        } finally {
            if (cursor != null)
                cursor.close()
        }
        return null
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