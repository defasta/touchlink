package apps.eduraya.genius.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetDetailContentResponse(
    val data: DataDetailContent? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val code: Int? = null
): Parcelable

@Parcelize
data class DataDetailContent(
    val id: Int? = null,
    val title: String? = null,
    @SerializedName("video_url")
    val videoUrl: String? = null,
    @SerializedName("content_url")
    val contentUrl: String? = null,
    val type: String? = null
):Parcelable