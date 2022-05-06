package life.league.challenge.kotlin.ui.base

import androidx.lifecycle.ViewModel
import com.google.gson.Gson

open class BaseViewModel : ViewModel() {

    val mGson = Gson()

    inline fun <reified T> jsonToObject(json: String): T {
        return mGson.fromJson(json, T::class.java)
    }
}

