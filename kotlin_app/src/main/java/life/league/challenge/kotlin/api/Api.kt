package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API interface definition using coroutines. Feel free to change this implementation to
 * suit your chosen architecture pattern and concurrency tools
 */
interface Api {

    @GET("login")
    suspend fun loginIn(
        @Header("Authorization") credentials: String?
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

    @GET("users")
    suspend fun getAllUser(
        @Header("x-access-token") apiKey: String?
    ): Response<List<User>>


    @GET("posts")
    suspend fun getUserPosts(
        @Header("x-access-token") apiKey: String?,
        @Query("userId") userId: Int
    ): Response<List<Post>>

}
