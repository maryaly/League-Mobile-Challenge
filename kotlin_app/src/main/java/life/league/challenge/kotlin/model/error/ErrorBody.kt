package life.league.challenge.kotlin.model.error

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("message")
    val mMessage: String?
)