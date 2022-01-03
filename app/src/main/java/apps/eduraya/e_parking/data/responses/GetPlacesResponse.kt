package apps.eduraya.e_parking.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetPlacesResponse(
    val code: Int? = null,
    val `data`: DataPlace? = null,
    val message: String? = null,
    val success: Boolean? = null
):Parcelable

@Parcelize
data class DataPlace(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    val `data`: ArrayList<ListDataPlace?>? = null,
    @SerializedName("first_page_url")
    val firstPageUrl: String? = null,
    val from: Int? = null,
    @SerializedName("last_page")
    val lastPage: Int? = null,
    @SerializedName("last_page_url")
    val lastPageUrl: String? = null,
    val links: List<Link>? = null,
    @SerializedName("next_page_url")
    val nextPageUrl:String? = null,
    val path: String? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("prev_page_url")
    val prevPageUrl: String? = null,
    val to: Int? = null,
    val total: Int? = null
):Parcelable

@Parcelize
data class ListDataPlace(
    val address: String? = null,
    val code: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    val id: Int? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val name: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
//    val quotas: ArrayList<Quotas?>? = null
):Parcelable

//@Parcelize
//data class Quotas(
//    @SerializedName("created_at")
//    val createdAt: String? = null,
//    val id: Int? = null,
//    @SerializedName("place_id")
//    val placeId: Int? = null,
//    @SerializedName("quota_regular")
//    val quotaRegular: Int? = null,
//    @SerializedName("quota_valet")
//    val quotaValet: Int? = null,
//    @SerializedName("updated_at")
//    val updatedAt: String? = null,
//    @SerializedName("vehicle_id")
//    val vehicleId: Int? = null
//):Parcelable

@Parcelize
data class Link(
    val active: Boolean? = null,
    val label: String? = null,
    val url: String? = null
):Parcelable