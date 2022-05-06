package life.league.challenge.kotlin.utils.pref

interface SharedPreferenceHelper {
    fun setBoolean(key: String, value: Boolean)
    fun setString(key: String, value: String)
    fun setInt(key: String, value: Int)
    fun setLong(key: String, value: Long)
    fun getBoolean(key: String): Boolean
    fun getString(key: String): String
    fun getInt(key: String): Int
    fun getLong(key: String): Long
    fun delete(key: String)
}
