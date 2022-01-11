package apps.eduraya.e_parking.data.responses

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateDepositResponse(
    val code: Int? = null,
    val `data`: DataDeposit? = null,
    val message: String? = null,
    val success: Boolean? = null
):Parcelable

@Parcelize
data class DataDeposit(
    val amount: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    val id: Int? = null,
    val status: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("user_id")
    val userId: Int? = null
):Parcelable
