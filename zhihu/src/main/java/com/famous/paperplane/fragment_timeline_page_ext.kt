package com.famous.paperplane

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.famous.paperplane.zhihu.R

val View.refresh_layout: SwipeRefreshLayout get() = this.findViewById(R.id.refresh_layout)

val View.recycler_view: RecyclerView get() = this.findViewById(R.id.recycler_view)

val View.empty_view: View get() = this.findViewById(R.id.empty_view)