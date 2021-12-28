package apps.eduraya.e_parking.data.responses.user


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetDataUserResponse(
    val code: Int? = null,
    val `data`: Data? = null,
    val message: String? = null,
    val success: Boolean? = null
): Parcelable

@Parcelize
data class Data(
    val user: UserData? = null
):Parcelable

@Parcelize
@Entity(tableName = "user_data")
data class UserData(

    @SerializedName("activation_token")
    @ColumnInfo(name = "activation_token")
    val activationToken: String? = null,

    @ColumnInfo(name = "avatar")
    val avatar: String? = null,

    @ColumnInfo(name = "balance")
    val balance: Int? = null,

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "email")
    val email: String? = null,

    @SerializedName("email_verified_at")
    @ColumnInfo(name = "email_verified_at")
    val emailVerifiedAt: String? = null,

    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "phone")
    val phone: String? = null,

    @ColumnInfo(name = "roles")
    val roles: List<Role>? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null
):Parcelable

@Parcelize
data class Role(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("guard_name")
    val guardName: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val pivot: Pivot? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
):Parcelable

@Parcelize
data class Pivot(
    @SerializedName("model_id")
    val modelId: Int? = null,
    @SerializedName("model_type")
    val modelType: String? = null,
    @SerializedName("role_id")
    val roleId: Int? = null
):Parcelable
