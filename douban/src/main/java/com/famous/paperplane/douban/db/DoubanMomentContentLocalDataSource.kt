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

package com.famous.paperplane.douban.db

import com.famous.paperplane.business_base.LocalDataNotFoundException
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.douban.db.DoubanMomentContentDao
import com.famous.paperplane.douban.entity.DoubanMomentContent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Created by lizhaotailang on 2017/5/25.
 *
 * Concrete implementation of a [DoubanMomentContent] data source as database.
 */

class DoubanMomentContentLocalDataSource internal constructor(
    private val mDoubanMomentContentDao: DoubanMomentContentDao
) {

    suspend fun getDoubanMomentContent(id: Int): Result<DoubanMomentContent> = withContext(IO) {
        val content = mDoubanMomentContentDao.queryContentById(id)
        if (content != null) Result.Success(content) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun saveContent(content: DoubanMomentContent) {
        withContext(IO) {
            mDoubanMomentContentDao.insert(content)
        }
    }

}
