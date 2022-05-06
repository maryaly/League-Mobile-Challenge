package life.league.challenge.kotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Result
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.error.ErrorBody
import life.league.challenge.kotlin.repository.AuthRepository
import life.league.challenge.kotlin.repository.UserRepository
import life.league.challenge.kotlin.ui.base.BaseViewModel
import life.league.challenge.kotlin.utils.Consts
import life.league.challenge.kotlin.utils.SingleLiveEvent
import life.league.challenge.kotlin.utils.network.NetworkHelper
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelper
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelperImpl
import life.league.challenge.kotlin.utils.resource.ResourceUtilHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mNetworkHelper: NetworkHelper,
    private val mResourceUtilHelper: ResourceUtilHelper,
    private val mAuthRepository: AuthRepository,
    private val mSharedPreferenceHelper: SharedPreferenceHelper,
    private val mUserRepository: UserRepository
) : BaseViewModel() {

    private var _mLogin = SingleLiveEvent<Result<Account>>()
    val mLogin: LiveData<Result<Account>> = _mLogin

    var mError = MutableLiveData<String>()

    private var mCurrentUsers: Flow<PagingData<User>>? = null

    init {
        login()
    }

    fun login() {
        mError.value = Consts.EMPTY_STRING
        if (mNetworkHelper.isNetworkConnected()) {
            viewModelScope.launch(Dispatchers.IO) {
                _mLogin.postValue(Result.loading())
                try {
                    val account = mAuthRepository.login(
                        "hello", "world"
                    ).let { response ->
                        if (response.isSuccessful) {
                            response.body()?.let { account ->
                                account.apiKey?.let {
                                    saveApiKey(it)
                                    viewModelScope.launch {
                                        getAllUser()
                                    }
                                }
                            }
                            _mLogin.postValue(Result.success(response.body()))
                        } else {
                            response.errorBody()?.let {
                                val errorBody = jsonToObject<ErrorBody>(it.string())
                                withContext(Dispatchers.Main) {
                                    mError.value = errorBody.mMessage
                                        ?: mResourceUtilHelper.getResourceString(R.string.error_general)
                                }
                            }
                            postError()
                        }
                    }
                } catch (exp: Exception) {
                    Timber.e(exp)
                    withContext(Dispatchers.Main) {
                        mError.value = mResourceUtilHelper.getResourceString(R.string.error_general)
                    }
                    postError()
                }
            }
        } else
            mError.value = mResourceUtilHelper.getResourceString(R.string.no_internet_connection)
    }

    private fun saveApiKey(mApikey: String) {
        mSharedPreferenceHelper.setString(
            SharedPreferenceHelperImpl.PREF_API_KEY, mApikey
        )
    }

    private fun postError() {
        _mLogin.postValue(
            Result.error(
                Consts.EMPTY_STRING,
                null
            )
        )
    }

    fun getAllUser(): Flow<PagingData<User>> {
        val lastUsers = mCurrentUsers
        if (lastUsers != null)
            return lastUsers

        val newUsers: Flow<PagingData<User>> =
            mUserRepository.getAllUserStream(
                mSharedPreferenceHelper,
                mResourceUtilHelper
            )

        mCurrentUsers = newUsers
        return newUsers
    }

    fun checkNetworkConnection(): Boolean {
        return mNetworkHelper.isNetworkConnected()
    }
}