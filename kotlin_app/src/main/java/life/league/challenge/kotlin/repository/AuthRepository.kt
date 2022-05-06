package life.league.challenge.kotlin.repository

import life.league.challenge.kotlin.api.ApiHelper
import life.league.challenge.kotlin.model.Account
import retrofit2.Response
import javax.inject.Inject

class AuthRepository  @Inject constructor(
    private val mApiHelper: ApiHelper
) {

    suspend fun loginIn(credentials: String?): Response<Account> =
        mApiHelper.loginIn(credentials = credentials)


    /**
     * Overloaded Login API extension function to handle authorization header encoding
     */
    suspend fun login(username: String, password: String) = loginIn(
        "Basic " + android.util.Base64.encodeToString
            (
            "$username:$password".toByteArray(), android.util.Base64.NO_WRAP
        )
    )
}