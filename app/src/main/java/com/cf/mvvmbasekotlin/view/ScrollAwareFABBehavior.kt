package com.cf.wanzhuanandroidapp.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.jar.Attributes

/**
 *@作者:陈飞
 *@说明:FloatingActionButton自定义Behavior
 *@时间:2019/11/27 13:52
 */
class ScrollAwareFABBehavior(context: Context, attrs: Attributes) :
    FloatingActionButton.Behavior() {
    private var mIsAnimatingOut = false

    companion object {
        private val INTERPOLATOR = FastOutSlowInInterpolator()
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int
    ): Boolean {
        return axes == ViewGroup.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes
        )
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed
        )

        if (dxConsumed > 0 && !this.mIsAnimatingOut && child.visibility == View.VISIBLE) {
            animateOut(child)
        } else if (dxConsumed < 0 && child.visibility != View.VISIBLE) {
            animationIn(child)
        }

    }


    private fun animateOut(button: FloatingActionButton) {
        ViewCompat.animate(button).translationY((button.height + getMarginBotton(button)).toFloat())
            .setInterpolator(INTERPOLATOR)
            .withLayer()
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationCancel(view: View?) {
                    this@ScrollAwareFABBehavior.mIsAnimatingOut = true
                }

                override fun onAnimationStart(view: View?) {
                    this@ScrollAwareFABBehavior.mIsAnimatingOut = false
                }

                override fun onAnimationEnd(view: View?) {
                    this@ScrollAwareFABBehavior.mIsAnimatingOut = false
                    view!!.visibility = View.INVISIBLE
                }
            })
    }

    private fun animationIn(button: FloatingActionButton) {
        button.visibility = View.VISIBLE
        ViewCompat.animate(button)
            .translationY(0f)
            .setInterpolator(INTERPOLATOR)
            .withLayer()
            .setListener(null)
            .start()
    }

    private fun getMarginBotton(v: View): Int {
        var marginBotton = 0
        val layoutParams = v.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            marginBotton = (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
        }
        return marginBotton
    }
}