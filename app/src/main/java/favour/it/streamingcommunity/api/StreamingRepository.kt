package favour.it.streamingcommunity.api

class StreamingRepository {
    private val api = RetrofitInstance.api

    suspend fun search(query: String): List<SearchResult> {
        val response = api.getSearch(query)
        return response.data //questo data sarebbe il nome del data list che incapsula la risposta
    }

    suspend fun getTitlesByGenre(genre: String): GenreResponse {
        return api.getTitlesByGenre(genre)
    }

}