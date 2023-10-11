package com.github.splashgallery.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class SquareLayout @JvmOverloads constructor(
    context : Context,
    attrs : AttributeSet,
    defStyleAttribute : Int = 0
) : ConstraintLayout(context, attrs, defStyleAttribute)  {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}