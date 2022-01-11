package apps.eduraya.e_parking.data.responses.deposit


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetDepositResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
):Parcelable

@Parcelize
data class Data(
    @SerializedName("current_page")
    val currentPage: Int,
    val `data`: ArrayList<DataDeposit>,
    @SerializedName("first_page_url")
    val firstPageUrl: String,
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("last_page_url")
    val lastPageUrl: String,
    val links: List<Link>,
    @SerializedName("next_page_url")
    val nextPageUrl: String,
    val path: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("prev_page_url")
    val prevPageUrl: String,
    val to: Int,
    val total: Int
):Parcelable

@Parcelize
data class DataDeposit(
    val amount: Int,
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
):Parcelable

@Parcelize
data class Link(
    val active: Boolean,
    val label: String,
    val url: String
):Parcelable