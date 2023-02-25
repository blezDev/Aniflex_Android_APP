package com.blez.aniplex_clone.Presentation.exoplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExoViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {
    sealed class SetupEventForVideo(){
        object VideoLinkLoading : SetupEventForVideo()

        data class VideoLink(val data : VideoData) : SetupEventForVideo()
    }
    private val _videoFlow = MutableStateFlow<SetupEventForVideo>(SetupEventForVideo.VideoLinkLoading)
    val videoFlow : StateFlow<SetupEventForVideo>
    get() = _videoFlow

    fun getVideoData(episodeLink : String) = viewModelScope.launch(Dispatchers.Main){
        _videoFlow.value = SetupEventForVideo.VideoLinkLoading
        val data = animeRepository.getVideoData(episodeID = episodeLink)
        _videoFlow.value = SetupEventForVideo.VideoLink(data = data?: return@launch)

    }


}