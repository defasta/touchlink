package apps.eduraya.e_parking.data.responses.insurance


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetInsuranceResponse(
    val code: Int,
    val `data`: DataInsurance,
    val message: String,
    val success: Boolean
):Parcelable

@Parcelize
data class DataInsurance(
    @SerializedName("created_at")
    val createdAt: String,
    val description: String,
    val id: Int,
    val name: String,
    val nominal: Int,
    val price: Int,
    @SerializedName("updated_at")
    val updatedAt: String
):Parcelable