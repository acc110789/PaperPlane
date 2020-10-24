package com.marktony.zhihudaily.glide

import android.widget.ImageView
import com.famous.paperplane.business_base.ImageService
import com.kwai.kim.module_suite.base.ModuleImpl

@ModuleImpl(module = [ImageService::class])
class ImageServiceImpl: ImageService {
    override fun loadImage(imageView: ImageView, url: String?) {
        imageView.loadImage(url)
    }
}