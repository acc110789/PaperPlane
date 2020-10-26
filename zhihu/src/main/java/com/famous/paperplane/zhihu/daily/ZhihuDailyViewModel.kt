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
        class Load(val clearCache: Boolean, val date: Long) : UiEvent()
        object LoadCache: UiEvent()
    }

    val showLoadingIndicator = MutableLiveData<Boolean>()
    val newsList = MutableLiveData<List<ZhihuDailyNewsQuestion>>()

    private val uiEventChannel = Channel<UiEvent>()

    fun loadNews(clearCache: Boolean, date: Long) {
        uiEventChannel.offer(UiEvent.Load(clearCache, date))
    }

    fun loadNewsFromCache() {
        uiEventChannel.offer(UiEvent.LoadCache)
    }

    init {
        listenEvent()
    }

    private fun listenEvent() = viewModelScope.launch {
        for (event in uiEventChannel) {
            when(event) {
                is UiEvent.Load -> loadNewsInner(event.clearCache, event.date)
                is UiEvent.LoadCache -> loadNewsFromCacheInner()
            }
        }
    }

    private suspend fun loadNewsInner(clearCache: Boolean, date: Long) {
        showLoadingIndicator.postValue(true)
        val result = ZhihuDailyNewsRepository.getZhihuDailyNews(date, clearCache)

        showLoadingIndicator.postValue(false)
        if (result is Result.Success) {
            newsList.postValue(result.data.toMutableList())
        }
    }

    private suspend fun loadNewsFromCacheInner() {
        val result = ZhihuDailyNewsRepository.getZhihuDailyNewsFromCache()
        if (result is Result.Success) {
            newsList.postValue(result.data.toMutableList())
        }
    }
}