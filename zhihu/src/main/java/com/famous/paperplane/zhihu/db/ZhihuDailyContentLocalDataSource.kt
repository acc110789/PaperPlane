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

package com.famous.paperplane.zhihu.db

import com.famous.paperplane.business_base.LocalDataNotFoundException
import com.famous.paperplane.business_base.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Created by lizhaotailang on 2017/5/26.
 *
 * Concrete implementation of a [ZhihuDailyContent] data source as database.
 */

class ZhihuDailyContentLocalDataSource internal constructor(
        val mZhihuDailyContentDao: ZhihuDailyContentDao
)  {

    suspend fun getZhihuDailyContent(id: Int): Result<ZhihuDailyContent> = withContext(IO) {
        val content = mZhihuDailyContentDao.queryContentById(id)
        if (content != null) Result.Success(content) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun saveContent(content: ZhihuDailyContent) {
        withContext(IO) {
            mZhihuDailyContentDao.insert(content)
        }
    }

}
