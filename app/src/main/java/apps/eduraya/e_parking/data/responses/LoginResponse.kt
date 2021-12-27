package apps.eduraya.e_parking.data.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    val data: Data? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val code: Int? = null
):Parcelable

@Parcelize
data class Data (
    val token: String? = null,
    val user: User? = null): Parcelable

@Parcelize
data class User(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val email_verified_at: String? = null,
    val phone: Int? = null,
    val avatar: String? = null,
    val balance: Int? = null,
    val status: String? = null,
    val activation_token: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null
):Parcelable