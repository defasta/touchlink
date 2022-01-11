package apps.eduraya.e_parking.data.responses


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayDepositResponse(
    val code: Int? = null,
    val `data`: DataPayDeposit? = null,
    val message: String? = null,
    val success: Boolean? = null
):Parcelable

@Parcelize
data class DataPayDeposit(
    val token: String? = null
):Parcelable