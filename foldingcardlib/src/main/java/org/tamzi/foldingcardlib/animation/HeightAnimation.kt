package org.tamzi.foldingcardlib.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.Transformation

/*
* add description and more inline documentation
*
*/

/**
 * Height animation for cell container to change cell size according to flip animation.
 */
class HeightAnimation(
    private val mView: View,
    private val mHeightFrom: Int,
    private val mHeightTo: Int,
    //duration: Int
) :
    Animation() {
    init {
        duration = duration.toLong()
    }

    fun withInterpolator(interpolator: Interpolator?): HeightAnimation {
        if (interpolator != null) {
            //interpolator = interpolator
            getInterpolator()
        }
        return this
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newHeight = mHeightFrom + (mHeightTo - mHeightFrom) * interpolatedTime
        if (interpolatedTime == 1f) {
            mView.layoutParams.height = mHeightTo
        } else {
            mView.layoutParams.height = newHeight.toInt()
        }
        mView.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }

    override fun isFillEnabled(): Boolean {
        return false
    }

    override fun toString(): String {
        return "HeightAnimation{" +
                "mHeightFrom=" + mHeightFrom +
                ", mHeightTo=" + mHeightTo +
                ", offset =" + startOffset +
                ", duration =" + duration +
                '}'
    }
}
