package life.league.challenge.kotlin.model.error

import com.google.gson.annotations.SerializedName

data class ExtraParams(
    @SerializedName("key")
    val mKey: String?,
    @SerializedName("value")
    val mValue: String?
)