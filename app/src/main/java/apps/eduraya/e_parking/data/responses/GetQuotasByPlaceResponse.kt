package apps.eduraya.e_parking.data.responses.getplace


import com.google.gson.annotations.SerializedName

data class GetQuotasByPlace(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)