package com.cf.wanzhuanandroidapp.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(space: Int) : RecyclerView.ItemDecoration() {
    private val mSpace = space

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 0
        outRect.bottom = mSpace
    }
}