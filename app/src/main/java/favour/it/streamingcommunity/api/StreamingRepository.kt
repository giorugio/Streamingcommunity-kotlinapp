package favour.it.streamingcommunity.api

class StreamingRepository {
    private val api = RetrofitInstance.api

    suspend fun getBrowse(t: String): GenreResponse {
        return api.getBrowse(t)
    }

    suspend fun search(query: String): List<SearchResult> {
        val response = api.getSearch(query)
        return response.data
    }

    suspend fun getTitlesByGenre(genre: String): GenreResponse {
        return api.getTitlesByGenre(genre)
    }

}