package life.league.challenge.kotlin.utils.resource

import android.content.Context
import life.league.challenge.kotlin.utils.Consts
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ResourceUtilHelperImpl @Inject constructor(private val mContext: Context) :
    ResourceUtilHelper {

    override fun getResourceString(resourceId: Int): String =
        try {
            mContext.getString(resourceId)
        } catch (exp: Exception) {
            Timber.e(exp)
            Consts.EMPTY_STRING
        }
}