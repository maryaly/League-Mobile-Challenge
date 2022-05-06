package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val mApi: Api) : ApiHelper {

    override suspend fun loginIn(credentials: String?): Response<Account> =
        mApi.loginIn(credentials = credentials)

    /**
     * Overloaded Login API extension function to handle authorization header encoding
     */
    override suspend fun login(
        username: String, password: String
    ) = mApi.loginIn(
        "Basic " + android.util.Base64.encodeToString
            (
            "$username:$password".toByteArray(), android.util.Base64.NO_WRAP
        )
    )

    override suspend fun getAllUser(
        apiKey: String?
    ): Response<List<User>> = mApi.getAllUser(apiKey = apiKey)

    override suspend fun getUserPosts(
        apiKey: String?, userId: Int
    ): Response<List<Post>> = mApi.getUserPosts(apiKey = apiKey, userId = userId)

}