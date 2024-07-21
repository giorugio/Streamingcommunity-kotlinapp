package favour.it.streamingcommunity.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface StreamingApi {

    @GET("/search")
    suspend fun getSearch(@Query("q") query: String): SearchResponse

    @GET
    suspend fun loadDetails(@Url url: String): String

    @POST
    suspend fun loadPreview(@Url url: String): ItemPreviewResponse
}