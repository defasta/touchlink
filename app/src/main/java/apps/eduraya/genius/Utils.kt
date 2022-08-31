package apps.eduraya.genius

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import apps.eduraya.genius.data.network.Resource
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.*

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startAnActivity(activity: Class<A>) {
    Intent(this, activity).also {
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

//fun Activity.snackbar(message: String, action: (() -> Unit)? = null) {
//    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
//    action?.let {
//        snackbar.setAction("Retry") {
//            it()
//        }
//    }
//    snackbar.show()
//}

//fun Fragment.logout() = lifecycleScope.launch {
//    if (activity is HomeActivity) {
//        (activity as HomeActivity).performLogout()
//    }
//}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    if(failure.isNetworkError){
        requireView().snackbar("Mohon cek koneksi internet Anda!")
    }else{
        requireView().snackbar(failure.errorCode.toString())
        if (failure.errorCode ==  404 || failure.errorCode ==  422){
            requireView().snackbar("Akun tidak terdaftar!")
        }else if (failure.errorCode == 401){
            requireView().snackbar("Password salah!")
        }else{
            requireView().snackbar("Terjadi kesalahan")
        }
    }
}

//fun Activity.handleApiErrorActivity(
//    failure: Resource.Failure,
//    retry: (() -> Unit)? = null
//) {
//    if(failure.isNetworkError){
//        val error = failure.errorCode.toString()
//        snackbar(error)
//    }else{
//        requireView().snackbar("Terjadi kesalahan")
//    }
//}



