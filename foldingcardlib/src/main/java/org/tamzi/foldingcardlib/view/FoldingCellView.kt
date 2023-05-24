package org.tamzi.foldingcardlib.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.widget.RelativeLayout


/**
 * Basic element for folding animation that represents one physic part of folding sheet with different views on front and back.
 *
 */
class FoldingCellView : RelativeLayout {
    var backView: View? = null
        private set
    var frontView: View? = null
        private set

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        var layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        layoutParams = layoutParams
        this.clipToPadding = false
        this.clipChildren = false
    }

    constructor(frontView: View?, backView: View?, context: Context?) : super(context) {
        this.frontView = frontView
        this.backView = backView
        var layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        this.clipToPadding = false
        this.clipChildren = false
        if (this.backView != null) {
            this.addView(this.backView)
            val mBackViewParams = backView!!.layoutParams as LayoutParams
            mBackViewParams.addRule(ALIGN_PARENT_BOTTOM)
            backView.layoutParams = mBackViewParams
            layoutParams.height = mBackViewParams.height
        }
        if (this.frontView != null) {
            this.addView(this.frontView)
            val frontViewLayoutParams = frontView!!.layoutParams as LayoutParams
            frontViewLayoutParams.addRule(ALIGN_PARENT_BOTTOM)
            frontView.layoutParams = frontViewLayoutParams
        }
        layoutParams = layoutParams
    }

    fun withFrontView(frontView: View?): FoldingCellView {
        this.frontView = frontView
        if (this.frontView != null) {
            this.addView(this.frontView)
            val frontViewLayoutParams = frontView!!.layoutParams as LayoutParams
            frontViewLayoutParams.addRule(ALIGN_PARENT_BOTTOM)
            frontView.layoutParams = frontViewLayoutParams
        }
        return this
    }

    fun withBackView(backView: View?): FoldingCellView {
        this.backView = backView
        if (this.backView != null) {
            this.addView(this.backView)
            val mBackViewParams = backView!!.layoutParams as LayoutParams
            mBackViewParams.addRule(ALIGN_PARENT_BOTTOM)
            backView.layoutParams = mBackViewParams
            var layoutParams = this.layoutParams as LayoutParams
            layoutParams.height = mBackViewParams.height
            layoutParams = layoutParams
        }
        return this
    }

    fun animateFrontView(animation: Animation?) {
        if (frontView != null) frontView!!.startAnimation(animation)
    }
}
