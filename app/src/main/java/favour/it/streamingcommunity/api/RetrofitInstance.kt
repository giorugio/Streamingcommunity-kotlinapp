package favour.it.streamingcommunity.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    private const val baseUrl = "https://streamingcommunity.boston/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: StreamingApi by lazy {
        retrofit.create(StreamingApi::class.java)
    }


}