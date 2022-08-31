package apps.eduraya.genius.data.responses


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditProfileResponse(
    val code: Int? = null,
    val `data`: DataProfile? = null,
    val message: String? = null,
    val success: Boolean? = null
): Parcelable

@Parcelize
data class DataProfile(
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("photo_url")
    val photoUrl: String? = null,
    val email: String? = null,
    @SerializedName("birth_date")
    val birthDate: String? = null,
    @SerializedName("birth_place")
    val birthPlace: String? = null,
    val address: String? = null,
    @SerializedName("educational_level")
    val educationalLevel: String? = null
):Parcelable