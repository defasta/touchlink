package apps.eduraya.genius.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetListContentResponse(
    val data: DataXContent? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val code: Int? = null
): Parcelable

@Parcelize
data class DataXContent(
    val data: ArrayList<DataContent>? = null,
): Parcelable

@Parcelize
data class DataContent(
    val id: Int? = null,
    val title: String? = null,
):Parcelable