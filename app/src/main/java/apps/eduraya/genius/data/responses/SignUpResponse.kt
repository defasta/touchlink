package apps.eduraya.genius.data.responses
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpResponse(
    val code: Int?,
    val `data`: DataRegister?,
    val message: String?,
    val success: Boolean?
): Parcelable

@Parcelize
data class DataRegister(
    val id: Int?,
): Parcelable
