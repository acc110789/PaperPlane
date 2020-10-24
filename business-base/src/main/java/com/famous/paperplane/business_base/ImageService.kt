package com.famous.paperplane.business_base

import android.widget.ImageView
import com.kwai.kim.module_suite.base.ModuleManager

val imageService = ModuleManager.requireModule(ImageService::class.java)

interface ImageService {

    fun loadImage(imageView: ImageView, url: String?)
}