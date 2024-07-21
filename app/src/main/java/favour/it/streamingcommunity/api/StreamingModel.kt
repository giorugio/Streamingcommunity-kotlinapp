package favour.it.streamingcommunity.api

data class SearchResponse (
    //val results: Map<String, SearchResult>,
    val data: List<SearchResult>
)

data class SearchResult (
    val id: String,
    val slug: String,
    val name: String,
    val type: String,
    val score: String,
    val sub_ita: Boolean,
    val last_air_date: String?,
    val seasons_count: Int,
    val images: List<Image>,
    val url: String
)

data class Image(
    val imageable_id: String,
    val imageable_type: String,
    val filename: String,
    val type: String,
    val original_url_field: String?
)

data class ItemPreviewResponse (
    val type: String,
    val release_date: String,
    val genres: List<Genre>,
    val title: Title
)

data class Genre(
    val name: String
)

data class Title(
    val id: String,
    val name: String,
    val plot: String,
    val score: String,
    val tmdb_id: String,
    val imdb_id: String,
    val netflix_id: String?,
    val prime_id: String?,
    val disney_id: String?,
    val release_date: String,
    val sub_ita: Boolean,
    val seasons: List<Season>,
    val seasons_count: Int,
    val trailers: List<Trailer>?
)

data class Season(
    val number: Int,
    val title_id: String,
    val episodes: List<Episode>
)

data class Episode(
    val id: String,
    val name: String,
    val number: Int,
    val plot: String,
    val duration: Int,
    val images: List<Image>
)

data class Trailer(
    val youtube_id: String
)