package favour.it.streamingcommunity.api

class StreamingRepository {
    private val api = RetrofitInstance.api

    suspend fun search(query: String): List<SearchResult> {
        val response = api.getSearch(query)
        return response.data //questo data sarebbe il nome del datalist che incapsula la risposta
    }

//    suspend fun loadDetails(url: String): String {
//        return api.loadDetails(url)
//    }

//    suspend fun loadPreview(url: String): ItemPreviewResponse {
//        val previewUrl = "${RetrofitInstance.baseUrl}/api/titles/preview/$item_id"
//        return api.loadPreview(url)
//    }


}