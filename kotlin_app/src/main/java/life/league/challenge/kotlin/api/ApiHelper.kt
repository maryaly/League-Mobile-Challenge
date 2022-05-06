package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import retrofit2.Response

interface ApiHelper {

    suspend fun loginIn(
        credentials: String?
    ): Response<Account>


    /**
     * Overloaded Login API extension function to handle authorization header encoding
     */
    suspend fun login(
        username: String, password: String
    ) = loginIn(
        "Basic " + android.util.Base64.encodeToString
            (
            "$username:$password".toByteArray(), android.util.Base64.NO_WRAP
        )
    )

    suspend fun getAllUser(
        apiKey: String?
    ): Response<List<User>>

    suspend fun getUserPosts(
        apiKey: String?,
        userId: Int
    ): Response<List<Post>>
}