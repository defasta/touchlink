package apps.eduraya.genius.data.responses

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogoutResponse(
    val data: String? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val code: Int? = null
):Parcelable