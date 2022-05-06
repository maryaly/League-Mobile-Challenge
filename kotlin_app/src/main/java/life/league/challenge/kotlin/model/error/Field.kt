package life.league.challenge.kotlin.model.error

import com.google.gson.annotations.SerializedName

data class Field(
    @SerializedName("message")
    val mMessage: String?,
    @SerializedName("name")
    val mName: String?,
    @SerializedName("value")
    val mValue: String?
)