package apps.eduraya.genius.ui.auth.register

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import apps.eduraya.genius.*
import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.databinding.FragmentSignUpBinding
import apps.eduraya.genius.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {
    private val viewModel by viewModels<SignUpViewModel>()
    val authentionNavController: NavController? by lazy { view?.findNavController() }
    var formate = SimpleDateFormat("YYYY-MM-dd", Locale.US)
    private val OPERATION_CHOOSE_PHOTO = 2
    var imagePath: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbar.visible(false)
        binding.buttonRegister.enable(false)

        viewModel.datePicked.observe(viewLifecycleOwner, Observer { date ->
            if (date != null){
                binding.tvBirthDate.visible(true)
                binding.btnChooseDate.text = "Ubah Tanggal"
//                viewModel.imagePathPicked.observe(viewLifecycleOwner, Observer { imagePath ->
//                    if (imagePath != null){
//                        binding.tvIsPhotoUploaded.visible(true)
//                        binding.btnChoosePhoto.text = "Ubah Foto"
//                    }
//                })
            }

        })


        binding.btnChooseDate.setOnClickListener {
            getChoosenDate()
        }

        binding.btnChoosePhoto.setOnClickListener {
//            val checkSelfPermission = ContextCompat.checkSelfPermission(requireContext(),
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
//                //Requests permissions to be granted to this application at runtime
//                ActivityCompat.requestPermissions(requireActivity(),
//                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
//            }
//            else{
//                openGallery()
//            }
            openGallery()
        }

        binding.editTextTextPasswordC.addTextChangedListener {
            val name = binding.editTextTextPersonName.text.toString().trim()
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val birthDate = binding.tvBirthDate
            val birthPlace = binding.editTextBirthPlace.text.toString().trim()
            val address = binding.editTextAddress.text.toString().trim()
            val photo = binding.tvIsPhotoUploaded
            val educationalLevel = binding.editTextEducation.text.toString()
            val password = binding.editTextTextPassword.text.toString().trim()
            binding.buttonRegister.enable(name.isNotEmpty() && email.isNotEmpty() && birthDate.isVisible && birthPlace.isNotEmpty() && address.isNotEmpty() && educationalLevel.isNotEmpty() && password.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonRegister.setOnClickListener {
            val password = binding.editTextTextPassword.text.toString()
            val passwordC = binding.editTextTextPasswordC.text.toString()
            if (password != passwordC){
                binding.editTextTextPasswordC.requestFocus()
                binding.editTextTextPasswordC.setError("Password tidak valid!")
            } else signUp()
        }

        binding.textViewSignUpNow.setOnClickListener{
            requireActivity().onBackPressed()
        }

    }

    private fun getChoosenDate() {
        val now = Calendar.getInstance()

        val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR,year)
            selectedDate.set(Calendar.MONTH,month)
            selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val date = formate.format(selectedDate.time)
            viewModel.setDatePicked(date)
            viewModel.datePicked.observe(viewLifecycleOwner, Observer {
                binding.tvBirthDate.text = it
            }) },
            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
        datePicker.show()

    }

    private fun signUp(){
        val userPreferences = UserPreferences(requireContext())
//        viewModel.datePicked.observe(viewLifecycleOwner, Observer { date ->
//            viewModel.imagePathPicked.observe(viewLifecycleOwner, Observer { imagePath ->
//
//
//            })
//        })
        val name = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(), binding.editTextTextPersonName.text.toString().trim())

        val email = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.editTextTextEmailAddress.text.toString().trim())

        val birthDate = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(), binding.tvBirthDate.text.toString().trim())

        val birthPlace = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.editTextBirthPlace.text.toString().trim())

        val address = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.editTextAddress.text.toString().trim())

        val educationalLevel = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.editTextEducation.text.toString().trim())

        val password = okhttp3.RequestBody.create("text/plain".toMediaTypeOrNull(),binding.editTextTextPassword.text.toString().trim())

//        val photoBody = okhttp3.RequestBody.create("image/*".toMediaTypeOrNull(), File(imagePath))

      //  val photo = MultipartBody.Part.createFormData("photo", File(imagePath).name, photoBody)

        viewModel.signup(name,email, birthDate, birthPlace, address, educationalLevel, password)

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {
                        Log.d("ID USER", it.value.data?.id.toString())
                        requireView().snackbar("Buat akun berhasil! Silakan melakukan Login untuk melanjutkan")
                        val direction = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                        authentionNavController?.navigate(direction)
                    }
                }
                is Resource.Failure -> handleApiError(it){signUp()}
            }
        })



    }

    private fun openGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
//                                            , grantedResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
//        when(requestCode){
//            1 ->
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
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }
    }

    private fun show(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(requireContext(), uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri?.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion)
                viewModel.setImagePathPicked(getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion))
            }
            else if ("com.android.providers.downloads.documents" == uri?.authority){
                val fileName = getDataColumn(requireContext(), uri, null, null)
                var uriToReturn: String? = null
                if(fileName != null){
                    uriToReturn = Uri.withAppendedPath(Uri.parse(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).absolutePath), fileName).toString()
                }
                //return uriToReturn
                imagePath = uriToReturn
                viewModel.setImagePathPicked(uriToReturn!!)
            }
        }
        else if ("content".equals(uri?.scheme, ignoreCase = true)){
            imagePath = getImagePath(uri, null)
            viewModel.setImagePathPicked(getImagePath(uri, null))
        }
        else if ("file".equals(uri?.scheme, ignoreCase = true)){
            imagePath = uri?.path
            viewModel.setImagePathPicked(uri?.path!!)
        }
        renderImage(imagePath)
    }

    private fun renderImage(imagePath: String?){
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            //binding.imgProfile?.setImageBitmap(bitmap)
        }
        else {
            show("ImagePath is null")
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
        val cursor = requireActivity().contentResolver.query(uri!!, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }
}