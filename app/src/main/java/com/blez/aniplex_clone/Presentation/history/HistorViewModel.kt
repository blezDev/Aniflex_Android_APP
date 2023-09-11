package com.blez.aniplex_clone.Presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.aniplex_clone.db.WatHistory
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistorViewModel @Inject constructor(private val animeRepository: AnimeRepository) :
    ViewModel() {


    init {
        getData()
    }


    private val _data = MutableSharedFlow<List<WatHistory>>()
    val data: SharedFlow<List<WatHistory>>
        get() = _data


    fun getData() {
        viewModelScope.launch {
            val data = animeRepository.getHistoryData()

            _data.emit(data)

        }
    }

}