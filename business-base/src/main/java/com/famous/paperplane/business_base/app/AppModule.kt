package com.famous.paperplane.business_base.app

import com.kwai.kim.module_suite.base.ModuleManager

val appModule = ModuleManager.requireModule(AppModule::class.java)

interface AppModule {

    fun detailActivityClass(): Class<*>
}