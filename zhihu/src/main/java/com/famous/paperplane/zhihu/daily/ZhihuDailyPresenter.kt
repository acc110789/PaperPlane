/*
 * Copyright 2016 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.famous.paperplane.zhihu.daily

import androidx.lifecycle.ViewModel
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.zhihu.base.ZhihuDailyNewsRepository
import com.famous.paperplane.business_base.launchSilent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Listens to user actions from UI ([ZhihuDailyFragment]),
 * retrieves the data and update the ui as required.
 */

class ZhihuDailyPresenter(
    private val mView: ZhihuDailyContract.View,
    private val mRepository: ZhihuDailyNewsRepository,
    private val uiContext: CoroutineContext = Dispatchers.Main
): ViewModel(), ZhihuDailyContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {
    }

    override fun loadNews(forceUpdate: Boolean, clearCache: Boolean, date: Long) = launchSilent(uiContext) {
        val result = mRepository.getZhihuDailyNews(forceUpdate, clearCache, date)
        if (mView.isActive) {
            if (result is Result.Success) {
                mView.showResult(result.data.toMutableList())
            }

            mView.setLoadingIndicator(false)
        }
    }

}