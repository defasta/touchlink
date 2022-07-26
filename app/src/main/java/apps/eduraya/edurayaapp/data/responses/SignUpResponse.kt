package apps.eduraya.edurayaapp.data.responses
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
    val token: String?,
    val user: UserRegister?
): Parcelable

@Parcelize
data class UserRegister(
    @SerializedName("activation_token")
    val activationToken: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    val email: String?,
    val id: Int?,
    val name: String?,
    val roles: List<Role>?,
    val status: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
): Parcelable

@Parcelize
data class Pivot(
    @SerializedName("model_id")
    val modelId: Int?,
    @SerializedName("model_type")
    val modelType: String?,
    @SerializedName("role_id")
    val roleId: Int?
):Parcelable

@Parcelize
data class Role(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("guard_name")
    val guardName: String,
    val id: Int,
    val name: String,
    val pivot: Pivot,
    @SerializedName("updated_at")
    val updatedAt: String
):Parcelable