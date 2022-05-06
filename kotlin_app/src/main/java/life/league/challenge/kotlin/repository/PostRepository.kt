package life.league.challenge.kotlin.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import life.league.challenge.kotlin.api.ApiHelper
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.pagingSource.PostPagingSource
import life.league.challenge.kotlin.model.pagingSource.UserPagingSource
import life.league.challenge.kotlin.utils.Consts
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelper
import life.league.challenge.kotlin.utils.resource.ResourceUtilHelper
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val mApiHelper: ApiHelper,
    private val mGson: Gson
) {

    fun getAllPostStream(
        mSharedPreferenceHelper: SharedPreferenceHelper,
        mResourceUtilHelper: ResourceUtilHelper,
        mUserId: Int
    ): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = Consts.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PostPagingSource(
                    mApiHelper,
                    mSharedPreferenceHelper,
                    mResourceUtilHelper,
                    mUserId,
                    mGson
                )
            }
        ).flow
    }
}