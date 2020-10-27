package com.famous.paperplane.douban

import com.famous.paperplane.business_base.douban.DoubanModule
import com.kwai.kim.module_suite.base.ModuleImpl

@ModuleImpl(module = [DoubanModule::class])
class DoubanModuleImpl: DoubanModule