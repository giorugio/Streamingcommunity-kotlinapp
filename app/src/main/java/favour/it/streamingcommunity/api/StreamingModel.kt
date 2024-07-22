package favour.it.streamingcommunity.api

data class SearchResponse (
    val data: List<SearchResult>
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

data class Image(
    val imageable_id: String, //potrebbe essere int
    val imageable_type: String,
    val filename: String,
    val type: String,
    val original_url_field: String?
)


/* questo serve per caricare ora è tolta

le funzioni per caricare sono in
- streamingApi.kt
- streamingRepository.kt
- streamingViewModel.kt

 */
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
    val sub_ita: String,
    val seasons: List<Season>,
    val seasons_count: String,
    val trailers: List<Trailer>?
)

data class Season(
    val number: String,
    val title_id: String,
    val episodes: List<Episode>
)

data class Episode(
    val id: String,
    val name: String,
    val number: String,
    val plot: String,
    val duration: String,
    val images: List<Image>
)

data class Trailer(
    val youtube_id: String
)