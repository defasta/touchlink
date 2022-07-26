package apps.eduraya.edurayaapp.data.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestResetPasswordResponse(
    val code: Int? = null,
    val message: String? = null,
    val success: Boolean? = null
): Parcelable