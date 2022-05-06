package life.league.challenge.kotlin.utils.pref

import android.content.Context
import at.favre.lib.armadillo.Armadillo
import life.league.challenge.kotlin.utils.Consts

class SharedPreferenceHelperImpl(
    context: Context,
    prefFileName: String = Consts.LEAGUE_SHARED_PREF
) : SharedPreferenceHelper {

    private var mSharesPreferences = Armadillo.create(context, prefFileName)
        .encryptionFingerprint(context)
        .build()

    override fun setBoolean(key: String, value: Boolean) {
        mSharesPreferences.edit().putBoolean(key, value).apply()
    }

    override fun setString(key: String, value: String) {
        mSharesPreferences.edit().putString(key, value).apply()
    }

    override fun setInt(key: String, value: Int) {
        mSharesPreferences.edit().putInt(key, value).apply()
    }

    override fun setLong(key: String, value: Long) {
        mSharesPreferences.edit().putLong(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean =
        mSharesPreferences.getBoolean(key, false)

    override fun getString(key: String): String =
        mSharesPreferences.getString(key, Consts.EMPTY_STRING) ?: Consts.EMPTY_STRING

    override fun getInt(key: String): Int =
        mSharesPreferences.getInt(key, 0)

    override fun getLong(key: String): Long =
        mSharesPreferences.getLong(key, 0L)

    override fun delete(key: String) =
        mSharesPreferences.edit().remove(key).apply()

    companion object {
        const val PREF_API_KEY = "PREF_API_KEY"
    }
}