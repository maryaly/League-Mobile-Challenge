package life.league.challenge.kotlin.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import life.league.challenge.kotlin.api.ApiHelper
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.pagingSource.UserPagingSource
import life.league.challenge.kotlin.utils.Consts
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelper
import life.league.challenge.kotlin.utils.resource.ResourceUtilHelper
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val mApiHelper: ApiHelper,
    private val mGson: Gson
) {

    fun getAllUserStream(
        mSharedPreferenceHelper: SharedPreferenceHelper,
        mResourceUtilHelper: ResourceUtilHelper,
    ): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = Consts.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserPagingSource(
                    mApiHelper,
                    mSharedPreferenceHelper,
                    mResourceUtilHelper,
                    mGson
                )
            }
        ).flow
    }
}