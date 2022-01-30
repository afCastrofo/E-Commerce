package br.com.afCastrofo.testeframework.utils

import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible

fun View.alphaAnimation(duration: Long = 500, withEndAction: (() -> Unit)? = null) {
    animate()
        .alpha(1f)
        .setDuration(duration)
        .withEndAction {
            withEndAction?.invoke()
        }
        .start()
}

fun View.enterTranslateYAnimation(duration: Long = 400, delayMultiplier: Int = 1, withEndAction: (() -> Unit)? = null) {
    translationY = 1000f
    isVisible = true
    
    animate()
        .translationY(0f)
        .setDuration(duration)
        .withEndAction {
            withEndAction?.invoke()
        }
        .setStartDelay(100L * delayMultiplier)
        .start()
}