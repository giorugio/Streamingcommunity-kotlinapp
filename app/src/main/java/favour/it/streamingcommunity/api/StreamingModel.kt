package favour.it.streamingcommunity.api

data class SearchResponse (
    val data: List<SearchResult>
)

data class GenreResponse (
    val name: String,
    val label: String,
    val titles: List<Title>
)

data class SearchResult (
    val id: String, //potrebbe essere int
    val slug: String,
    val name: String,
    val type: String,
    val score: String,
    val sub_ita: String, //potrebbe essere int
    val last_air_date: String?,
    val seasons_count: String, //potrebbe essere int
    val images: List<Image>,
)

data class Title (
    val id: String, //potrebbe essere int
    val slug: String,
    val name: String,
    val type: String,
    val score: String,
    val sub_ita: String, //potrebbe essere int
    val last_air_date: String?,
    val seasons_count: String, //potrebbe essere int
    val images: List<Image>,
)

data class Image(
    val imageable_id: String, //potrebbe essere int
    val imageable_type: String,
    val filename: String,
    val type: String,
    val original_url_field: String?
)
