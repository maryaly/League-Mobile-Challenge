package life.league.challenge.kotlin.ui.posts

import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.repository.AuthRepository
import life.league.challenge.kotlin.repository.PostRepository
import life.league.challenge.kotlin.ui.base.BaseViewModel
import life.league.challenge.kotlin.utils.network.NetworkHelper
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelper
import life.league.challenge.kotlin.utils.resource.ResourceUtilHelper
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val mNetworkHelper: NetworkHelper,
    private val mResourceUtilHelper: ResourceUtilHelper,
    private val mAuthRepository: AuthRepository,
    private val mSharedPreferenceHelper: SharedPreferenceHelper,
    private val mPostRepository: PostRepository
) : BaseViewModel() {

    private var mCurrentPosts: Flow<PagingData<Post>>? = null


    fun getUserPosts(mUserId: Int): Flow<PagingData<Post>> {
        val lastPosts = mCurrentPosts
        if (lastPosts != null)
            return lastPosts

        val newPosts: Flow<PagingData<Post>> =
            mPostRepository.getAllPostStream(
                mSharedPreferenceHelper,
                mResourceUtilHelper,
                mUserId
            )

        mCurrentPosts = newPosts
        return newPosts
    }

    fun checkNetworkConnection(): Boolean {
        return mNetworkHelper.isNetworkConnected()
    }
}