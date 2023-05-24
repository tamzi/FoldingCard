package org.tamzi.foldingcardlib.animation

import android.graphics.Camera
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.Transformation


/**
 * Main piece of fold animation. Rotates view in 3d space around one of view borders.
 */
class FoldAnimation(private val mFoldMode: FoldAnimationMode, cameraHeight: Int, duration: Long) :
    Animation() {
    enum class FoldAnimationMode {
        FOLD_UP, UNFOLD_DOWN, FOLD_DOWN, UNFOLD_UP
    }

    private val mCameraHeight: Int
    private var mFromDegrees = 0f
    private var mToDegrees = 0f
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var mCamera: Camera? = null

    init {
        this.fillAfter = true
        //duration = duration.toLong()
        getDuration()
        mCameraHeight = cameraHeight
    }

    fun withAnimationListener(animationListener: AnimationListener?): FoldAnimation {
        setAnimationListener(animationListener)
        return this
    }

    fun withStartOffset(offset: Int): FoldAnimation {
        this.startOffset = offset.toLong()
        return this
    }

    fun withInterpolator(interpolator: Interpolator?): FoldAnimation {
        if (interpolator != null) {
            //interpolator = interpolator
            getInterpolator()
        }
        return this
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        mCamera = Camera()
        mCamera!!.setLocation(0f, 0f, -mCameraHeight.toFloat())
        mCenterX = (width / 2).toFloat()
        when (mFoldMode) {
            FoldAnimationMode.FOLD_UP -> {
                mCenterY = 0f
                mFromDegrees = 0f
                mToDegrees = 90f
            }

            FoldAnimationMode.FOLD_DOWN -> {
                mCenterY = height.toFloat()
                mFromDegrees = 0f
                mToDegrees = -90f
            }

            FoldAnimationMode.UNFOLD_UP -> {
                mCenterY = height.toFloat()
                mFromDegrees = -90f
                mToDegrees = 0f
            }

            FoldAnimationMode.UNFOLD_DOWN -> {
                mCenterY = 0f
                mFromDegrees = 90f
                mToDegrees = 0f
            }

            else -> throw IllegalStateException("Unknown animation mode.")
        }
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val camera = mCamera
        val matrix = t.matrix
        val fromDegrees = mFromDegrees
        val degrees = fromDegrees + (mToDegrees - fromDegrees) * interpolatedTime
        camera!!.save()
        camera.rotateX(degrees)
        camera.getMatrix(matrix)
        camera.restore()
        matrix.preTranslate(-mCenterX, -mCenterY)
        matrix.postTranslate(mCenterX, mCenterY)
    }

    override fun toString(): String {
        return "FoldAnimation{" +
                "mFoldMode=" + mFoldMode +
                ", mFromDegrees=" + mFromDegrees +
                ", mToDegrees=" + mToDegrees +
                '}'
    }
}
