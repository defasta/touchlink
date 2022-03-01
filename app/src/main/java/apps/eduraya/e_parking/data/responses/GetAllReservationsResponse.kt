package apps.eduraya.e_parking.data.responses.reservations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetAllReservationsResponse(
    val code: Int? = null,
    val `data`: DataReservationsX? = null,
    val message: String? = null,
    val success: Boolean? = null
): Parcelable

@Parcelize
data class DataReservationsX(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    val `data`: ArrayList<DataReservations>? = null,
    @SerializedName("first_page_url")
    val firstPageUrl: String? = null,
    val from: Int? = null,
    @SerializedName("last_page")
    val lastPage: Int? = null,
    @SerializedName("last_page_url")
    val lastPageUrl: String? = null,
    val links: List<Link>? = null,
    @SerializedName("next_page_url")
    val nextPageUrl: String? = null,
    val path: String? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("prev_page_url")
    val prevPageUrl: String? = null,
    val to: Int? = null,
    val total: Int? = null
): Parcelable

@Parcelize
data class Link(
    val active: Boolean? = null,
    val label: String? = null,
    val url: String? = null
): Parcelable

@Parcelize
data class DataReservations(
    @SerializedName("basic_price")
    val basicPrice: Int? = null,
    @SerializedName("check_in")
    val checkIn: String? = null,
    @SerializedName("check_in_photo")
    val checkInPhoto: String? = null,
    @SerializedName("check_out")
    val checkOut: String? = null,
    @SerializedName("check_out_photo")
    val checkOutPhoto: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    val gate: GateReservation? = null,
    @SerializedName("gate_id")
    val gateId: Int? = null,
    val id: Int? = null,
    @SerializedName("insurance_price")
    val insurancePrice: Int? = null,
    @SerializedName("is_insurance")
    val isInsurance: Int? = null,
    @SerializedName("officer_id")
    val officerId: String? = null,
    val place: PlaceReservation? = null,
    @SerializedName("place_id")
    val placeId: Int? = null,
    @SerializedName("plate_number")
    val plateNumber: String? = null,
    @SerializedName("progressive_price")
    val progressivePrice: Int? = null,
    val status: String? = null,
    @SerializedName("total_bill")
    val totalBill: Int? = null,
    @SerializedName("total_pay")
    val totalPay: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    val user: UserReservation? = null,
    @SerializedName("user_id")
    val userId: Int? = null,
    @SerializedName("valet_area_id")
    val valetAreaId: String? = null,
    val valetarea: ValetArea? = null,
    val vehicle: VehicleReservation? = null,
    @SerializedName("vehicle_id")
    val vehicleId: Int? = null
): Parcelable

@Parcelize
data class GateReservation(
    val code: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    val id: Int? = null,
    @SerializedName("place_id")
    val placeId: Int? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("vehicle_id")
    val vehicleId: Int? = null
): Parcelable

@Parcelize
data class PlaceReservation(
    val id: Int? = null,
    val name: String? = null
): Parcelable

@Parcelize
data class UserReservation(
    val email: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val phone: String? = null
): Parcelable

@Parcelize
data class ValetArea(
    @SerializedName("code_area")
    val codeArea: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    val floor: String? = null,
    val id: Int? = null,
    @SerializedName("place_id")
    val placeId: Int? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("vehicle_id")
    val vehicleId: Int? = null
):Parcelable

@Parcelize
data class VehicleReservation(
    val id: Int? = null,
    val name: String? = null
): Parcelable