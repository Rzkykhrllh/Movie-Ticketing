package com.example.movticket.Home.Dashboard

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DefaultItemDecorator(private val xspace : Int, private val yspace : Int, private val mode : Int = 0) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (mode==1){
            if (parent.getChildLayoutPosition(view) == 0){
                outRect.right = xspace
            }
            outRect.left = (xspace/2)
            outRect.right = (xspace/2)
        }

        if (mode == 0){

            if (parent.getChildLayoutPosition(view) == 0){
                outRect.bottom = yspace
            }

            outRect.bottom = yspace/2
            outRect.top = yspace/2
        }
    }
}