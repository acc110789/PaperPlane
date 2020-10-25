package com.famous.paperplane.business_base

import android.view.View
import android.widget.ImageView
import android.widget.TextView

val View.text_view_title: TextView get() = this.findViewById(R.id.text_view_title)

val View.image_view_cover: ImageView get() = this.findViewById(R.id.image_view_cover)