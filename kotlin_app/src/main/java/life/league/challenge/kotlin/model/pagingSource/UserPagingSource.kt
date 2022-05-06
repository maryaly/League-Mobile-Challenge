package life.league.challenge.kotlin.model.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.api.ApiHelper
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.error.ErrorBody
import life.league.challenge.kotlin.utils.Consts
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelper
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelperImpl
import life.league.challenge.kotlin.utils.resource.ResourceUtilHelper
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val mApiHelper: ApiHelper,
    private val mSharedPreferenceHelper: SharedPreferenceHelper,
    private val mResourceUtilHelper: ResourceUtilHelper,
    private val mGson: Gson
) : PagingSource<Int, User>() {

    fun jsonToObject(json: String): ErrorBody {
        return mGson.fromJson(json, ErrorBody::class.java)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: Consts.STARTING_PAGE_INDEX
        return try {

            val response = mApiHelper.getAllUser(
                mSharedPreferenceHelper.getString(
                    SharedPreferenceHelperImpl.PREF_API_KEY
                )
            )

            if (response.isSuccessful) {
                response.body()?.let {
                    val deals = it
                    val nextKey = if (deals.isEmpty())
                        null
                    else
                        position + (params.loadSize / Consts.NETWORK_PAGE_SIZE)

                    LoadResult.Page(
                        data = deals,
                        prevKey = if (position == Consts.STARTING_PAGE_INDEX) null else position - 1,
                        nextKey = nextKey
                    )
                }
                    ?: LoadResult.Error((Exception(mResourceUtilHelper.getResourceString(R.string.error_general))))

            } else {
                response.errorBody()?.let {
                    val error = jsonToObject(it.string()).mMessage
                        ?: mResourceUtilHelper.getResourceString(R.string.error_general)
                    return@let LoadResult.Error<Int, User>((Exception(error)))
                }
                    ?: LoadResult.Error((Exception(mResourceUtilHelper.getResourceString(R.string.error_general))))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}