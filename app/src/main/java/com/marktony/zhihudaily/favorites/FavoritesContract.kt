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

package com.marktony.zhihudaily.favorites

import com.famous.paperplane.business_base.BasePresenter
import com.famous.paperplane.business_base.BaseView
import com.famous.paperplane.douban.entity.DoubanMomentNewsPosts
import com.famous.paperplane.guokr.entity.GuokrHandpickNewsResult
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion

/**
 * Created by lizhaotailang on 2017/6/6.
 *
 * This specifies the contract between the view and the presenter.
 */

interface FavoritesContract {

    interface View : BaseView<Presenter> {

        val isActive: Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showFavorites(zhihuList: MutableList<ZhihuDailyNewsQuestion>,
                          doubanList: MutableList<DoubanMomentNewsPosts>,
                          guokrList: MutableList<GuokrHandpickNewsResult>)

    }

    interface Presenter : BasePresenter {

        fun loadFavorites()

    }

}
