package com.d121211030.artlovers.ui.feeds

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d121211030.artlovers.data.model.Artwork
import com.d121211030.artlovers.data.repository.ArtworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * View model for home and loved state
 */
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: ArtworkRepository,
    private val ioDispatcher: CoroutineDispatcher,
    ): ViewModel() {

    private val _homeArtworks = MutableLiveData<List<Artwork>?>()
    val homeArtworks: LiveData<List<Artwork>?>
        get() = _homeArtworks

    val lovedArtworks: LiveData<List<Artwork>?>
        get() = repository.lovedArtwork

    private var isFirstLoad = true

    init {
        _homeArtworks.postValue(emptyList())
        if (isFirstLoad) {
            loadHomeArtwork()
            isFirstLoad = false
        }
    }

    fun loadHomeArtwork(search: String? = null) {
        viewModelScope.launch(ioDispatcher) {
            repository.getHomeArtwork(search).collect {
                Log.i("FeedViewModel", "Updating home feed model ${it.size}")
                _homeArtworks.postValue(it)
            }
        }
    }

    fun updateIsLoved(artwork: Artwork) {
        viewModelScope.launch {
            repository.updateIsLoved(artwork)
        }
    }

}