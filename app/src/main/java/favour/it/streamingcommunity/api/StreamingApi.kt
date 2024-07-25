package favour.it.streamingcommunity.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StreamingApi {

    @GET("/api/browse/{t}")
    suspend fun getBrowse(@Path("t") t: String): GenreResponse

    @GET("/api/search") //con api ricevo il json della query
    suspend fun getSearch(@Query("q") query: String): SearchResponse

    @GET("/api/browse/genre") //questo è per prendere il genere
    suspend fun getTitlesByGenre(@Query("g") genre: String): GenreResponse

}