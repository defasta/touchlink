package apps.eduraya.e_parking.data.responses.valet


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetValetAreaResponse(
    val code: Int,
    val `data`: ArrayList<DataValetArea>,
    val message: String,
    val success: Boolean
):Parcelable

@Parcelize
data class DataValetArea(
    @SerializedName("code_area")
    val codeArea: String,
    @SerializedName("created_at")
    val createdAt: String,
    val floor: String,
    val id: Int,
    @SerializedName("place_id")
    val placeId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("vehicle_id")
    val vehicleId: Int
):Parcelable