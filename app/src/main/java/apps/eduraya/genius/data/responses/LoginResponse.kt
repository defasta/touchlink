package apps.eduraya.genius.data.responses

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    val data: Data? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val code: Int? = null
):Parcelable

@Parcelize
data class Data (
    val token: String? = null,
    val user: UserInfo? = null
): Parcelable

@Parcelize
data class UserInfo(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    @SerializedName("photo_url")
    val photoUrl: String? = null
):Parcelable