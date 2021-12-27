package apps.eduraya.e_parking.data.responses.getplace


import com.google.gson.annotations.SerializedName

data class GetQuotasByPlaceResponse(
    val code: Int? = null,
    val `data`: ArrayList<ListDataQuotasByPlace>? = null,
    val message: String? = null,
    val success: Boolean? = null
)

data class ListDataQuotasByPlace(
    @SerializedName("created_at")
    val createdAt: String? = null,
    val id: Int? = null,
    @SerializedName("place_id")
    val placeId: Int? = null,
    @SerializedName("quota_regular")
    val quotaRegular: Int? = null,
    @SerializedName("quota_valet")
    val quotaValet: Int? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("vehicle_id")
    val vehicleId: Int? = null
)