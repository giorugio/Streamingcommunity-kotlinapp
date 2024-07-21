package favour.it.streamingcommunity.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StreamingViewModel : ViewModel() {

    private val repository = StreamingRepository()

    //ui
    private val _searchResponse = MutableLiveData<List<SearchResult>>()
    val searchResponse: LiveData<List<SearchResult>> get() = _searchResponse

    //caricamento
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    //errori
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun search(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.search(query)
                _searchResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadDetails(url: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.loadDetails(url)
                // Process response to extract necessary data
                // Update LiveData objects accordingly
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}