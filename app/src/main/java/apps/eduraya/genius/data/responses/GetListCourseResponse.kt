package apps.eduraya.genius.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetListCourseResponse(
    val data: DataXCourse? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val code: Int? = null
): Parcelable

@Parcelize
data class DataXCourse(
    val data: ArrayList<DataCourse>? = null,
): Parcelable

@Parcelize
data class DataCourse(
    val id: Int? = null,
    val name: String? = null,
):Parcelable