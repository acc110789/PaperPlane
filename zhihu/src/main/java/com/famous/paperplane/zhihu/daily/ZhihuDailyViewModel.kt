package com.famous.paperplane.zhihu.daily

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.famous.paperplane.business_base.BaseViewModel
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.zhihu.base.ZhihuDailyNewsRepository
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class ZhihuDailyViewModel: BaseViewModel() {

    private sealed class UiEvent {
        class Load(val forceUpdate: Boolean, val clearCache: Boolean, val date: Long) : UiEvent()
    }

    val showLoadingIndicator = MutableLiveData<Boolean>()
    val newsList = MutableLiveData<List<ZhihuDailyNewsQuestion>>()

    private val uiEventChannel = Channel<UiEvent>()


    fun loadNews(forceUpdate: Boolean, clearCache: Boolean, date: Long) {
        uiEventChannel.offer(UiEvent.Load(forceUpdate, clearCache, date))
    }

    init {
        listenEvent()
    }

    private fun listenEvent() = viewModelScope.launch {
        for (event in uiEventChannel) {
            when(event) {
                is UiEvent.Load -> loadNewsInner(event.forceUpdate, event.clearCache, event.date)
            }
        }
    }


    private suspend fun loadNewsInner(forceUpdate: Boolean, clearCache: Boolean, date: Long) {
        val result = ZhihuDailyNewsRepository.getZhihuDailyNews(forceUpdate, clearCache, date)
        if (result is Result.Success) {
            newsList.postValue(result.data.toMutableList())
        }
        showLoadingIndicator.postValue(false)
    }
}