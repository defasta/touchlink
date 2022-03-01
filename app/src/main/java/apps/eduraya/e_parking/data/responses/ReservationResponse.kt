package apps.eduraya.e_parking.data.responses.reservation


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationResponse(
    val code: Int,
    val `data`: DataReservation,
    val message: String,
    val success: Boolean
): Parcelable

@Parcelize
data class DataReservation(
    @SerializedName("basic_price")
    val basicPrice: Int,
    @SerializedName("check_in")
    val checkIn: String,
    @SerializedName("check_in_photo")
    val checkInPhoto: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("insurance_price")
    val insurancePrice:Int,
    @SerializedName("is_insurance")
    val isInsurance: Boolean,
    @SerializedName("progressive_price")
    val progressivePrice: Int,
    val status: String,
    @SerializedName("total_bill")
    val totalBill: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("valet_area_id")
    val valetAreaId: Int,
    @SerializedName("vehicle_id")
    val vehicleId: Int
):Parcelable