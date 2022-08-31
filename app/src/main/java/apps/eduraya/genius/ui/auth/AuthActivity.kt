package apps.eduraya.genius.ui.auth

import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import apps.eduraya.genius.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

//        val checkSelfPermission = ContextCompat.checkSelfPermission(this,
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
//            //Requests permissions to be granted to this application at runtime
//            ActivityCompat.requestPermissions(this,
//                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
//        }
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
//                                            , grantedResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
//        when(requestCode){
//            1 ->
//                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
//                    PackageManager.PERMISSION_GRANTED){
//                    //openGallery()
//                }else {
//                    Toast.makeText(this, "Unfortunately You are Denied Permission to Perform this Operataion.", Toast.LENGTH_SHORT).show()
//                }
//        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}