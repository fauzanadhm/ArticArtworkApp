package com.d121211030.artlovers.ui.detail

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
 * View model for detail screen state
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ArtworkRepository,
    private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _artwork = MutableLiveData<Artwork?>()
    val artwork: LiveData<Artwork?>
        get() = _artwork

    fun updateModel(artwork: Artwork?) {
        Log.i("DetailViewModel", "Updating model ${artwork}")
        _artwork.postValue(artwork)
    }

    fun refreshArtworkDetail() {
        viewModelScope.launch(ioDispatcher) {
            artwork.value?.let {
                repository.refreshArtworkDetail(id = it.id, isLoved = it.isLoved).collect { result ->
                    updateModel(result)
                }
            }
        }
    }

    fun updateIsLoved() {
        viewModelScope.launch(ioDispatcher) {
            artwork.value?.let {
                repository.updateIsLoved(it)
                updateModel(it.copy(isLoved = it.isLoved?.not()))
            }
        }
    }
}