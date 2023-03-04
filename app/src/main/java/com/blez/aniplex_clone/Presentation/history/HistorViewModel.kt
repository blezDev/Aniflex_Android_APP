package com.blez.aniplex_clone.Presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.aniplex_clone.db.WatHistory
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistorViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {

    sealed class SealedEvent{
        object Loading : SealedEvent()
        data class ListData(val data : List<WatHistory>) : SealedEvent()
    }
    private val _data = MutableStateFlow<SealedEvent>(SealedEvent.Loading)
    val data : StateFlow<SealedEvent>
    get() = _data

    fun getListData(){
        viewModelScope.launch(Dispatchers.Main) {
       val data =  animeRepository.dao.getHistory()
       _data.value = SealedEvent.ListData(data)

        }
    }

}