package apps.eduraya.e_parking.data.responses.vehicle


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllVehicleResponse(
    val code: Int,
    val `data`: ArrayList<DataVehicle>,
    val message: String,
    val success: Boolean
): Parcelable

@Parcelize
data class DataVehicle(
    @SerializedName("basic_duration")
    val basicDuration: Int,
    @SerializedName("basic_price")
    val basicPrice: Int,
    @SerializedName("created_at")
    val createdAt: String,
    val icon: String,
    val id: Int,
    val name: String,
    @SerializedName("progressive_duration")
    val progressiveDuration: Int,
    @SerializedName("progressive_price")
    val progressivePrice: Int,
    @SerializedName("updated_at")
    val updatedAt: String
): Parcelable