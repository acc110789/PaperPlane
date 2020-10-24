package com.famous.paperplane.app

import com.famous.paperplane.business_base.app.AppModule
import com.kwai.kim.module_suite.base.ModuleImpl
import com.marktony.zhihudaily.details.DetailsActivity

@ModuleImpl(module = [AppModule::class])
class AppModuleImpl: AppModule {
    override fun detailActivityClass(): Class<*> = DetailsActivity::class.java
}