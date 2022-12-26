package abdullah.mola.malwettbewerb.model

import abdullah.mola.malwettbewerb.app_api.SumsiBackend
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    var authToken: String? = null
    var email: String = ""


    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            if (authToken != null) {
                val newRequest = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(chain.request())
            }

        }
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val sumsiBackend: SumsiBackend by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://sumsi.dev.webundsoehne.com/api/v1/")
            .build()
            .create(SumsiBackend::class.java)
    }
}