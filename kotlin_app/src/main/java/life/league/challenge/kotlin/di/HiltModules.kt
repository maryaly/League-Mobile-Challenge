package life.league.challenge.kotlin.di


import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import life.league.challenge.kotlin.BuildConfig
import life.league.challenge.kotlin.api.Api
import life.league.challenge.kotlin.api.ApiHelper
import life.league.challenge.kotlin.api.ApiHelperImpl
import life.league.challenge.kotlin.repository.AuthRepository
import life.league.challenge.kotlin.utils.Consts
import life.league.challenge.kotlin.utils.network.NetworkHelper
import life.league.challenge.kotlin.utils.network.NetworkHelperImpl
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelper
import life.league.challenge.kotlin.utils.pref.SharedPreferenceHelperImpl
import life.league.challenge.kotlin.utils.resource.ResourceUtilHelper
import life.league.challenge.kotlin.utils.resource.ResourceUtilHelperImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext mContext: Context): OkHttpClient =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().connectTimeout(Consts.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Consts.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Consts.TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .build()
        } else OkHttpClient
            .Builder()
            .retryOnConnectionFailure(true)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(mOkHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideNetworkHelperImpl(@ApplicationContext mContext: Context): NetworkHelper =
        NetworkHelperImpl(mContext)

    @Provides
    @Singleton
    fun provideApiService(mRetrofit: Retrofit): Api =
        mRetrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(mApiHelper: ApiHelperImpl): ApiHelper = mApiHelper

    @Provides
    @Singleton
    fun provideResourceUtilHelperImpl(@ApplicationContext mContext: Context): ResourceUtilHelper =
        ResourceUtilHelperImpl(mContext)

    @Provides
    @Singleton
    fun provideAuthRepository(mApiHelper: ApiHelper): AuthRepository = AuthRepository(mApiHelper)

    @Provides
    @Singleton
    fun provideSharedPreferencesImpl(@ApplicationContext mContext: Context): SharedPreferenceHelper =
        SharedPreferenceHelperImpl(mContext)
}