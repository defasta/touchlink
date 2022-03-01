package apps.eduraya.e_parking.data.responses


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditProfileResponse(
    val code: Int,
    val `data`: DataProfile,
    val message: String,
    val success: Boolean
): Parcelable

@Parcelize
data class DataProfile(
    val token: String,
    val user: User
): Parcelable

@Parcelize
data class User(
    @SerializedName("activation_token")
    val activationToken: String,
    val avatar: String,
    val balance: Int,
    @SerializedName("created_at")
    val createdAt: String,
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String,
    val id: Int,
    val name: String,
    val phone: String,
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
):Parcelable