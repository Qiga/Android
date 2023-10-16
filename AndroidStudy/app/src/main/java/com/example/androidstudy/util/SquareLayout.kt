package com.example.androidstudy.util

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 정사각형 모양의 레이아웃
 */
class SquareLayout @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttribute: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttribute) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}