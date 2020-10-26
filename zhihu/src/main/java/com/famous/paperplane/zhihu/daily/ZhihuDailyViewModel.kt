package com.famous.paperplane.zhihu.daily

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.famous.paperplane.business_base.BaseViewModel
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.zhihu.base.ZhihuDailyNewsRepository
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.*

class ZhihuDailyViewModel: BaseViewModel() {

    private sealed class UiEvent {
        object InitLoad: UiEvent()
        object LoadMore : UiEvent()
        object LoadCache: UiEvent()
    }

    val showLoadingIndicator = MutableLiveData<Boolean>()
    val newsList = MutableLiveData<List<ZhihuDailyNewsQuestion>>()

    private val uiEventChannel = Channel<UiEvent>()

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0

    private val currentDate: Long get() {
        val c = Calendar.getInstance()
        c.set(mYear, mMonth, mDay)
        return c.timeInMillis
    }

    fun refresh() {
        uiEventChannel.offer(UiEvent.InitLoad)
    }

    fun loadMore() {
        uiEventChannel.offer(UiEvent.LoadMore)
    }

    fun setCalendar(c: Calendar) {
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        refresh()
    }

    fun getCurrentCalendar(): Calendar {
        val c = Calendar.getInstance()
        c.set(mYear, mMonth, mDay)
        return c
    }

    init {
        listenEvent()

        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+08")
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        refresh()
    }

    private fun listenEvent() = viewModelScope.launch {
        for (event in uiEventChannel) {
            when(event) {
                is UiEvent.InitLoad -> refreshInner()
                is UiEvent.LoadMore -> loadMoreInner()
                is UiEvent.LoadCache -> loadNewsFromCacheInner()
            }
        }
    }

    private suspend fun refreshInner() {
        showLoadingIndicator.postValue(true)
        val result = ZhihuDailyNewsRepository.getZhihuDailyNews(currentDate, true)
        showLoadingIndicator.postValue(false)
        if (result is Result.Success) {
            newsList.postValue(result.data.toMutableList())
        }
    }

    private suspend fun loadMoreInner() {
        showLoadingIndicator.postValue(true)
        mDay--
        val result = ZhihuDailyNewsRepository.getZhihuDailyNews(currentDate, false)
        showLoadingIndicator.postValue(false)
        if (result is Result.Success) {
            val list = mutableListOf<ZhihuDailyNewsQuestion>()
            newsList.value?.let { list.addAll(it) }
            list.addAll(result.data)
            newsList.postValue(list)
        }
    }

    private suspend fun loadNewsFromCacheInner() {
        val result = ZhihuDailyNewsRepository.getZhihuDailyNewsFromCache()
        if (result is Result.Success) {
            newsList.postValue(result.data.toMutableList())
        }
    }
}