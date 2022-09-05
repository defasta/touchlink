package apps.eduraya.genius.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetListClassroomResponse(
    val data: DataX? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val code: Int? = null
): Parcelable

@Parcelize
data class DataX(
    val data: ArrayList<DataClass>? = null,
): Parcelable

@Parcelize
data class DataClass(
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("educational_level")
    val educational_level: String? = null
):Parcelable