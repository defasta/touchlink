package apps.eduraya.e_parking.data.responses.lastparking


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetLastParkingResponse(

    val code: Int,
    val `data`: DataLastParkings,
    val message: String,
    val success: Boolean
):Parcelable

@Parcelize
data class DataLastParkings(
    @SerializedName("basic_price")
    val basicPrice: Int,
    @SerializedName("check_in")
    val checkIn: String,
    @SerializedName("check_in_photo")
    val checkInPhoto: String,
    @SerializedName("check_out")
    val checkOut: String,
    @SerializedName("check_out_photo")
    val checkOutPhoto: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("gate_id")
    val gateId: Int,
    val id: Int,
    @SerializedName("insurance_price")
    val insurancePrice: Int,
    @SerializedName("is_insurance")
    val isInsurance: Int,
    @SerializedName("officer_id")
    val officerId: String,
    @SerializedName("place_id")
    val placeId: Int,
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("plate_number")
    val plateNumber: String,
    @SerializedName("progressive_price")
    val progressivePrice: Int,
    val status: String,
    @SerializedName("total_bill")
    val totalBill: Int,
    @SerializedName("total_pay")
    val totalPay: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("valet_area_id")
    val valetAreaId: Int,
    @SerializedName("vehicle_basic_duration")
    val vehicleBasicDuration: Int,
    @SerializedName("vehicle_basic_price")
    val vehicleBasicPrice: Int,
    @SerializedName("vehicle_icon")
    val vehicleIcon: String,
    @SerializedName("vehicle_id")
    val vehicleId: Int,
    @SerializedName("vehicle_name")
    val vehicleName: String,
    @SerializedName("vehicle_progressive_duration")
    val vehicleProgressiveDuration: Int,
    @SerializedName("vehicle_progressive_price")
    val vehicleProgressivePrice: Int
): Parcelable