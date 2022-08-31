package apps.eduraya.genius.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetListAllContentResponse(
    val data: DataXAllContent? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val code: Int? = null
): Parcelable

@Parcelize
data class DataXAllContent(
    val data: ArrayList<DataAllContent>? = null,
    @SerializedName("current_page")
    val currentPage: String? = null,
    @SerializedName("last_page")
    val lastPage: String? = null
): Parcelable

@Parcelize
data class DataAllContent(
    val id: Int? = null,
    val title: String? = null,
    val course: String? = null,
    val classroom: String? = null
):Parcelable