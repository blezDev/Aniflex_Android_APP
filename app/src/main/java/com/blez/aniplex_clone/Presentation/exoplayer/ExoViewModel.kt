package com.blez.aniplex_clone.Presentation.exoplayer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.data.VideoFormat
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExoViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {
    sealed class SetupEventForVideo() {
        object VideoLinkLoading : SetupEventForVideo()

        data class VideoLink(val data: VideoFormat) : SetupEventForVideo()

        data class Failure(val message: String) : SetupEventForVideo()
    }

    private val _videoFlow =
        MutableStateFlow<SetupEventForVideo>(SetupEventForVideo.VideoLinkLoading)
    val videoFlow: StateFlow<SetupEventForVideo>
        get() = _videoFlow

    fun getVideoData(episodeLink: String) = viewModelScope.launch {
        _videoFlow.value = SetupEventForVideo.VideoLinkLoading
        withContext(Dispatchers.IO) {
            val data = animeRepository.getVideoData(episodeID = episodeLink)
            withContext(Dispatchers.Main) {
                if (data == null) {
                    SetupEventForVideo.Failure("Empty Data")
                }

                _videoFlow.value = SetupEventForVideo.VideoLink(
                    data = data ?: throw NullPointerException("Null Value")
                )
            }
        }
    }

    init {
        getData()
    }
    fun getData(){
        viewModelScope.launch {
            val data = animeRepository.getHistoryData()
            Log.e("TAG",data.toString())
        }
    }

}