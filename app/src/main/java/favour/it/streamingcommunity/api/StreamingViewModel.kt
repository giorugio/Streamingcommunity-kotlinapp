package favour.it.streamingcommunity.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StreamingViewModel : ViewModel() {
    //repository
    private val repository = StreamingRepository()

    //ui
    private val _homeData = MutableLiveData<List<Title>>()
    val homeData: LiveData<List<Title>> get() = _homeData

    private val _searchResponse = MutableLiveData<List<SearchResult>>()
    val searchResponse: LiveData<List<SearchResult>> get() = _searchResponse

    private val _genreResponse = MutableLiveData<List<Title>>()
    val genreResponse: LiveData<List<Title>> get() = _genreResponse

    //caricamento
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    //error
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        getBrowse("trending")
    }

    private fun getBrowse(t: String) {
        _isLoading.value = true
        _error.value = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val homePageResponse = repository.getBrowse(t)
                withContext(Dispatchers.Main) {
                    _homeData.value = homePageResponse.titles
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                    _isLoading.value = false
                }
            }
        }
    }

    fun search(query: String) {
        _isLoading.value = true
        _error.value = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val searchResults = repository.search(query)
                withContext(Dispatchers.Main) {
                    _searchResponse.value = searchResults
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                    _isLoading.value = false
                }
            }
        }
    }

    fun getTitlesByGenre(genre: String) {
        _isLoading.value = true
        _error.value = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val genreResponse = repository.getTitlesByGenre(genre)
                withContext(Dispatchers.Main) {
                    _genreResponse.value = genreResponse.titles
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                    _isLoading.value = false
                }
            }
        }
    }
}